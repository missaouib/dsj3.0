package com.hanqian.kepler.core.service.question.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.hanqian.kepler.common.base.dao.BaseDao;
import com.hanqian.kepler.common.base.service.BaseServiceImpl;
import com.hanqian.kepler.common.bean.NameValueVo;
import com.hanqian.kepler.common.enums.BaseEnumManager;
import com.hanqian.kepler.common.jpa.specification.Rule;
import com.hanqian.kepler.common.jpa.specification.SpecificationFactory;
import com.hanqian.kepler.core.dao.primary.question.QuestionDao;
import com.hanqian.kepler.core.entity.primary.question.Question;
import com.hanqian.kepler.core.service.question.QuestionService;
import com.hanqian.kepler.core.vo.QuestionCountVo;
import com.hanqian.kepler.core.vo.QuestionEchartVo;
import com.hanqian.kepler.core.vo.QuestionExportVo;
import com.hanqian.kepler.core.vo.QuestionSearchVo;
import org.hibernate.SQLQuery;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        String whereSql = getWhereSearchSql(questionSearch);

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

    @Override
    public List<QuestionExportVo> findExportData(QuestionSearchVo questionSearchVo) {
//        List<Map<String, Object>> voMapList = questionDao.findExportData();
//        JSONArray jsonArray = JSONUtil.parseArray(voMapList);
//        return JSONUtil.toList(jsonArray, QuestionExportVo.class);

        String sql = "SELECT \n" +
                "CASE q.HOSPITALNAME\n" +
                "\tWHEN 'JZ' THEN '精神卫生中心'\n" +
                "\tWHEN 'HD' THEN '华东医院'\n" +
                "\tWHEN 'SY' THEN '第十人民医院'\n" +
                "\tWHEN 'DYRM' THEN '第一人民医院'\n" +
                "\tWHEN 'YY' THEN '岳阳医院'\n" +
                "\tWHEN 'LH' THEN '龙华医院'\n" +
                "\tWHEN 'XH' THEN '新华医院'\n" +
                "\tWHEN 'LY' THEN '第六人民医院'\n" +
                "\tWHEN 'XK' THEN '胸科医院'\n" +
                "\tWHEN 'FK' THEN '肺科医院'\n" +
                "\tWHEN 'DYRMYYBY' THEN '第一人民医院北院'\n" +
                "\tWHEN 'DYRMYYNY' THEN '第一人民医院南院'\n" +
                "\tWHEN 'SGYYDY' THEN '曙光医院东院'\n" +
                "\tWHEN 'SGYYXY' THEN '曙光医院西院'\n" +
                "\tWHEN 'ZYYY' THEN '上海市中医医院'\n" +
                "\tWHEN 'HDYY' THEN '华东医院'\n" +
                "\tWHEN 'JSWSZXXH' THEN '精神卫生中心徐汇'\n" +
                "\tWHEN 'JSWSZXMH' THEN '精神卫生中心闵行'\n" +
                "\tWHEN 'GWZX' THEN '公卫中心'\n" +
                "\tWHEN 'RJYYDY' THEN '仁济医院东院'\n" +
                "\tWHEN 'RJYYXY' THEN '仁济医院西院'\n" +
                "\tWHEN 'RJYYBY' THEN '仁济医院北院'\n" +
                "\tWHEN 'TJYY' THEN '同济医院'\n" +
                "\tWHEN 'RJYYZY' THEN '瑞金医院总院'\n" +
                "\tWHEN 'ETEXZX' THEN '儿童医学中心'\n" +
                "\tWHEN 'ETYYBJXL' THEN '儿童医院北京西路'\n" +
                "\tWHEN 'DYFYBJYDY' THEN '第一妇婴保健院东院'\n" +
                "END hospitalName,\n" +
                "\t\n" +
                "\tCASE q.OBJECTTYPE\n" +
                "\t\tWHEN 'Patient' THEN '患者'\n" +
                "\t\tWHEN 'PatientFamily' THEN '患者家属'\n" +
                "\t\tWHEN 'Doctor' THEN '医生'\n" +
                "\t\tWHEN 'Nurse' THEN '护士'\n" +
                "\t\tWHEN 'MedicalTechnician' THEN '医技人员'\n" +
                "\t\tWHEN 'Management' THEN '管理人员' ELSE '其他'\n" +
                "\tEND objectType,\n" +
                "\n" +
                "\tCASE q.SEX\n" +
                "\t\tWHEN 'male' THEN '男'\n" +
                "\t\tWHEN 'female' THEN '女'\n" +
                "\tEND sex,\n" +
                "\n" +
                "\tCASE q.AGEFIELD\n" +
                "\t\tWHEN 1 THEN '18岁以下'\n" +
                "\t\tWHEN 2 THEN '19~40'\n" +
                "\t\tWHEN 3 THEN '19~40'\n" +
                "\t\tWHEN 4 THEN '41~60'\n" +
                "\t\tWHEN 5 THEN '41~60'\n" +
                "\t\tWHEN 6 THEN '61岁以上'\n" +
                "\tEND ageField,\n" +
                "\n" +
                "\n" +
                "\tq.QUALITYINDOOR as qualityIndoor,\n" +
                "\tq.qualityOutdoor as qualityOutdoor,\n" +
                "\tq.toiletHygiene as toiletHygiene,\n" +
                "\tq.cleanService as cleanService,\n" +
                "\tq.dailySecurity as dailySecurity,\n" +
                "\tq.accidentalDisposal as accidentalDisposal,\n" +
                "\tq.securityService as securityService,\n" +
                "\tq.dishPrice as dishPrice,\n" +
                "\tq.diningEnvironment as diningEnvironment,\n" +
                "\tq.foodService as foodService,\n" +
                "\tq.deliveryTimeliness as deliveryTimeliness,\n" +
                "\tq.foodNutrition as foodNutrition,\n" +
                "\tq.diningAttitude as diningAttitude,\n" +
                "\tq.transportTimeliness as transportTimeliness,\n" +
                "\tq.transportAccuracy as transportAccuracy,\n" +
                "\tq.transportService as transportService,\n" +
                "\tq.repairTimeliness as repairTimeliness,\n" +
                "\tq.repairQuality as repairQuality,\n" +
                "\tq.elevatorStatus as elevatorStatus,\n" +
                "\tq.operationService as operationService\n" +
                "FROM HP_QUESTION q where q.state='Enable'" + getWhereSearchSql(questionSearchVo);

        Query query = em.createNativeQuery(sql);
//        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(QuestionExportVo.class));
        query.unwrap(SQLQuery.class).setResultTransformer(Transformers.aliasToBean(QuestionExportVo.class)); //虽然过时但是能用，上面注释那个会报NativeQueryImpl类型转换错误
        List<QuestionExportVo> list = query.getResultList();
        return list;
    }

    @Override
    public List<QuestionCountVo> findQuestionCountList(QuestionSearchVo questionSearchVo) {
        if(StrUtil.isNotBlank(questionSearchVo.getHospitalName())){
            return findQuestionCountListOfhospital(questionSearchVo);
        }
        String whereSql = getWhereSearchSql(questionSearchVo);
        String sql = "SELECT \n" +
                "\n" +
                "CASE q.HOSPITALNAME\n" +
                "\tWHEN 'JZ' THEN '精神卫生中心'\n" +
                "\tWHEN 'HD' THEN '华东医院'\n" +
                "\tWHEN 'SY' THEN '第十人民医院'\n" +
                "\tWHEN 'DYRM' THEN '第一人民医院'\n" +
                "\tWHEN 'YY' THEN '岳阳医院'\n" +
                "\tWHEN 'LH' THEN '龙华医院'\n" +
                "\tWHEN 'XH' THEN '新华医院'\n" +
                "\tWHEN 'LY' THEN '第六人民医院'\n" +
                "\tWHEN 'XK' THEN '胸科医院'\n" +
                "\tWHEN 'FK' THEN '肺科医院'\n" +
                "\tWHEN 'DYRMYYBY' THEN '第一人民医院北院'\n" +
                "\tWHEN 'DYRMYYNY' THEN '第一人民医院南院'\n" +
                "\tWHEN 'SGYYDY' THEN '曙光医院东院'\n" +
                "\tWHEN 'SGYYXY' THEN '曙光医院西院'\n" +
                "\tWHEN 'ZYYY' THEN '上海市中医医院'\n" +
                "\tWHEN 'HDYY' THEN '华东医院'\n" +
                "\tWHEN 'JSWSZXXH' THEN '精神卫生中心徐汇'\n" +
                "\tWHEN 'JSWSZXMH' THEN '精神卫生中心闵行'\n" +
                "\tWHEN 'GWZX' THEN '公卫中心'\n" +
                "\tWHEN 'RJYYDY' THEN '仁济医院东院'\n" +
                "\tWHEN 'RJYYXY' THEN '仁济医院西院'\n" +
                "\tWHEN 'RJYYBY' THEN '仁济医院北院'\n" +
                "\tWHEN 'TJYY' THEN '同济医院'\n" +
                "\tWHEN 'RJYYZY' THEN '瑞金医院总院'\n" +
                "\tWHEN 'ETEXZX' THEN '儿童医学中心'\n" +
                "\tWHEN 'ETYYBJXL' THEN '儿童医院北京西路'\n" +
                "\tWHEN 'DYFYBJYDY' THEN '第一妇婴保健院东院'\n" +
                "END as \"hospitalName\",\n" +
                "\n" +
                "'' as \"objectType\",\n" +
                "\n" +
                "CAST(count(*) AS CHAR) as \"count\",\n" +
                "\n" +
                "DATE_FORMAT(max(q.createTime), '%Y-%m-%d %H:%i') as \"maxCreateTime\"\n" +
                "\t\n" +
                "FROM HP_QUESTION q where q.state='Enable' "+whereSql+" GROUP BY q.hospitalName";
        Query query = em.createNativeQuery(sql);
        query.unwrap(SQLQuery.class).setResultTransformer(Transformers.aliasToBean(QuestionCountVo.class)); //虽然过时但是能用，上面注释那个会报NativeQueryImpl类型转换错误
        List<QuestionCountVo> list = query.getResultList();
        return list;
    }

    @Override
    public List<QuestionCountVo> findQuestionCountListOfhospital(QuestionSearchVo questionSearchVo) {
        String whereSql = getWhereSearchSql(questionSearchVo);
        String sql = "SELECT \n" +
                "\n" +
                "CASE q.HOSPITALNAME\n" +
                "\tWHEN 'JZ' THEN '精神卫生中心'\n" +
                "\tWHEN 'HD' THEN '华东医院'\n" +
                "\tWHEN 'SY' THEN '第十人民医院'\n" +
                "\tWHEN 'DYRM' THEN '第一人民医院'\n" +
                "\tWHEN 'YY' THEN '岳阳医院'\n" +
                "\tWHEN 'LH' THEN '龙华医院'\n" +
                "\tWHEN 'XH' THEN '新华医院'\n" +
                "\tWHEN 'LY' THEN '第六人民医院'\n" +
                "\tWHEN 'XK' THEN '胸科医院'\n" +
                "\tWHEN 'FK' THEN '肺科医院'\n" +
                "\tWHEN 'DYRMYYBY' THEN '第一人民医院北院'\n" +
                "\tWHEN 'DYRMYYNY' THEN '第一人民医院南院'\n" +
                "\tWHEN 'SGYYDY' THEN '曙光医院东院'\n" +
                "\tWHEN 'SGYYXY' THEN '曙光医院西院'\n" +
                "\tWHEN 'ZYYY' THEN '上海市中医医院'\n" +
                "\tWHEN 'HDYY' THEN '华东医院'\n" +
                "\tWHEN 'JSWSZXXH' THEN '精神卫生中心徐汇'\n" +
                "\tWHEN 'JSWSZXMH' THEN '精神卫生中心闵行'\n" +
                "\tWHEN 'GWZX' THEN '公卫中心'\n" +
                "\tWHEN 'RJYYDY' THEN '仁济医院东院'\n" +
                "\tWHEN 'RJYYXY' THEN '仁济医院西院'\n" +
                "\tWHEN 'RJYYBY' THEN '仁济医院北院'\n" +
                "\tWHEN 'TJYY' THEN '同济医院'\n" +
                "\tWHEN 'RJYYZY' THEN '瑞金医院总院'\n" +
                "\tWHEN 'ETEXZX' THEN '儿童医学中心'\n" +
                "\tWHEN 'ETYYBJXL' THEN '儿童医院北京西路'\n" +
                "\tWHEN 'DYFYBJYDY' THEN '第一妇婴保健院东院'\n" +
                "END as \"hospitalName\",\n" +
                "\n" +
                "CASE q.OBJECTTYPE\n" +
                "\t\tWHEN 'Patient' THEN '患者'\n" +
                "\t\tWHEN 'PatientFamily' THEN '患者家属'\n" +
                "\t\tWHEN 'Doctor' THEN '医生'\n" +
                "\t\tWHEN 'Nurse' THEN '护士'\n" +
                "\t\tWHEN 'MedicalTechnician' THEN '医技人员'\n" +
                "\t\tWHEN 'Management' THEN '管理人员' ELSE '其他'\n" +
                "\tEND as 'objectType',\n" +
                "\n" +
                "CAST(count(*) AS CHAR) as \"count\",\n" +
                "\n" +
                "DATE_FORMAT(max(q.createTime), '%Y-%m-%d %H:%i') as \"maxCreateTime\"\n" +
                "\t\n" +
                "FROM HP_QUESTION q where q.state='Enable' "+whereSql+" GROUP BY q.objectType";
        Query query = em.createNativeQuery(sql);
        query.unwrap(SQLQuery.class).setResultTransformer(Transformers.aliasToBean(QuestionCountVo.class)); //虽然过时但是能用，上面注释那个会报NativeQueryImpl类型转换错误
        List<QuestionCountVo> list = query.getResultList();
        return list;
    }

    @Override
    public int getHospCountEnable(QuestionSearchVo questionSearchVo) {
        String whereSql = getWhereSearchSql(questionSearchVo);
        String sql = "select count(DISTINCT q.hospitalName) from HP_QUESTION q where q.state='Enable' "+whereSql;
        Query query = em.createNativeQuery(sql);
        Object o = query.getSingleResult();
        if(o != null){
            return Convert.toInt(o);
        }
        return 0;
    }

    //公共拼接搜索部分sql
    private String getWhereSearchSql(QuestionSearchVo questionSearchVo){
        String whereSearchSql = " ";
        if(questionSearchVo!=null){
            if(StrUtil.isNotBlank(questionSearchVo.getHospitalName())){
                whereSearchSql += " and q.hospitalName='"+questionSearchVo.getHospitalName()+"'";
            }
            if(StrUtil.isNotBlank(questionSearchVo.getStartDate())){
                whereSearchSql += " and q.createTime>='"+questionSearchVo.getStartDate()+"'";
            }
            if(StrUtil.isNotBlank(questionSearchVo.getEndDate())){
                whereSearchSql += " and q.createTime<='"+questionSearchVo.getEndDate()+"'";
            }
        }
        return whereSearchSql;
    }
}
