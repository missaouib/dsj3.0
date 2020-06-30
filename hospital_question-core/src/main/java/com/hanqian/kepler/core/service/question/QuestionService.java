package com.hanqian.kepler.core.service.question;

import com.hanqian.kepler.common.base.service.BaseService;
import com.hanqian.kepler.common.bean.NameValueVo;
import com.hanqian.kepler.common.enums.BaseEnumManager;
import com.hanqian.kepler.core.entity.primary.question.Question;
import com.hanqian.kepler.core.vo.QuestionCountVo;
import com.hanqian.kepler.core.vo.QuestionEchartVo;
import com.hanqian.kepler.core.vo.QuestionExportVo;
import com.hanqian.kepler.core.vo.QuestionSearchVo;

import java.util.List;

public interface QuestionService extends BaseService<Question, String> {

    /**
     * 查询统计数据
     */
    List<Object[]> findGroupData(String itemName, QuestionSearchVo questionSearch);

    /**
     * 获取某个调查对象某个医院的总数量
     */
    long getCountByObjectAndHospitalName(String hospitalName, BaseEnumManager.ObjectTypeEnum objectTypeEnum);

    /**
     * 查询excel导出数据
     */
    List<QuestionExportVo> findExportData(QuestionSearchVo questionSearchVo);

    /**
     * 问卷数总览 表格数据（所有医院数据，questionSearchVo.hospitalName应该为空）
     */
    List<QuestionCountVo> findQuestionCountList(QuestionSearchVo questionSearchVo);

    /**
     * 问卷数总览 表格数据（单个医院根据调查对象分类，questionSearchVo.hospitalName一定不为空）
     */
    List<QuestionCountVo> findQuestionCountListOfhospital(QuestionSearchVo questionSearchVo);

    /**
     * 查询已经存在有数据的医院数量
     */
    int getHospCountEnable(QuestionSearchVo questionSearchVo);

}
