package com.hanqian.kepler.core.service.question;

import com.hanqian.kepler.common.base.service.BaseService;
import com.hanqian.kepler.common.bean.NameValueVo;
import com.hanqian.kepler.common.enums.BaseEnumManager;
import com.hanqian.kepler.core.entity.primary.question.Question;
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

}
