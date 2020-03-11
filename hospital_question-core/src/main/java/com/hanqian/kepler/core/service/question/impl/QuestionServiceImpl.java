package com.hanqian.kepler.core.service.question.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.hanqian.kepler.common.base.dao.BaseDao;
import com.hanqian.kepler.common.base.service.BaseServiceImpl;
import com.hanqian.kepler.common.bean.NameValueVo;
import com.hanqian.kepler.common.enums.BaseEnumManager;
import com.hanqian.kepler.common.jpa.specification.Rule;
import com.hanqian.kepler.common.jpa.specification.SpecificationFactory;
import com.hanqian.kepler.core.dao.primary.question.QuestionDao;
import com.hanqian.kepler.core.entity.primary.question.Question;
import com.hanqian.kepler.core.service.question.QuestionService;
import com.hanqian.kepler.core.vo.QuestionEchartVo;
import com.hanqian.kepler.core.vo.QuestionSearchVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionServiceImpl extends BaseServiceImpl<Question, String> implements QuestionService {

    @Autowired
    private QuestionDao questionDao;
    @PersistenceContext
    private EntityManager em;

    @Override
    public BaseDao<Question, String> getBaseDao() {
        return questionDao;
    }

    @Override
    public List<Object[]> findGroupData(String itemName, QuestionSearchVo questionSearch) {
        String orderby = StrUtil.equalsAny(itemName, new String[]{"objectType","sex"}) ? "objectTypeSort" : itemName;
        String whereSql = "";
        if(questionSearch!=null){
            if(StrUtil.isNotBlank(questionSearch.getHospitalName())){
                whereSql += " and q.hospitalName='"+questionSearch.getHospitalName()+"'";
            }
            if(StrUtil.isNotBlank(questionSearch.getStartDate())){
                whereSql += " and q.createTime>='"+questionSearch.getStartDate()+"'";
            }
            if(StrUtil.isNotBlank(questionSearch.getEndDate())){
                whereSql += " and q.createTime<='"+questionSearch.getEndDate()+"'";
            }
        }

        String sql = "SELECT q."+itemName+" as \"name\",count(*) as \"value\" FROM hp_question q where q.state='Enable' "+whereSql+" GROUP BY q."+itemName+" order by q."+orderby+" asc";
        Query query = em.createNativeQuery(sql);
        return query.getResultList();

    }

    @Override
    public long getCountByObjectAndHospitalName(String hospitalName, BaseEnumManager.ObjectTypeEnum objectTypeEnum) {
        Assert.notBlank(hospitalName);
        Assert.notNull(objectTypeEnum);

        BaseEnumManager.ObjectTypeEnum[] objectTypeEnumArr_YIHURENYUAN = new BaseEnumManager.ObjectTypeEnum[]{
                BaseEnumManager.ObjectTypeEnum.Doctor,
                BaseEnumManager.ObjectTypeEnum.Nurse,
                BaseEnumManager.ObjectTypeEnum.Other
        };

        BaseEnumManager.ObjectTypeEnum[] objectTypeEnumArr_BINGHUANJIASHU = new BaseEnumManager.ObjectTypeEnum[]{
                BaseEnumManager.ObjectTypeEnum.Patient,
                BaseEnumManager.ObjectTypeEnum.PatientFamily
        };

        List<Rule> rules = new ArrayList<>();
        rules.add(Rule.eq("state", BaseEnumManager.StateEnum.Enable));
        rules.add(Rule.eq("hospitalName", hospitalName));
        if(ObjectUtil.contains(objectTypeEnumArr_YIHURENYUAN, objectTypeEnum)){
            rules.add(Rule.in("objectType", objectTypeEnumArr_YIHURENYUAN));
        }
        if(ObjectUtil.contains(objectTypeEnumArr_BINGHUANJIASHU, objectTypeEnum)){
            rules.add(Rule.in("objectType", objectTypeEnumArr_BINGHUANJIASHU));
        }

        return count(SpecificationFactory.where(rules));
    }
}
