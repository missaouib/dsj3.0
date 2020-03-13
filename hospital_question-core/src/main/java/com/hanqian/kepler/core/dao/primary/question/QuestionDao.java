package com.hanqian.kepler.core.dao.primary.question;

import com.hanqian.kepler.common.base.dao.BaseDao;
import com.hanqian.kepler.common.bean.NameValueVo;
import com.hanqian.kepler.core.entity.primary.question.Question;
import com.hanqian.kepler.core.vo.QuestionExportVo;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface QuestionDao extends BaseDao<Question, String> {

    /**
     * excel导出数据
     */
    @Query(value = "SELECT \n" +
            "CASE q.HOSPITALNAME\n" +
            "\tWHEN 'JZ' THEN '精中医院'\n" +
            "\tWHEN 'HD' THEN '华东医院'\n" +
            "\tWHEN 'SY' THEN '第十人民医院'\n" +
            "\tWHEN 'DYRM' THEN '第一人民医院'\n" +
            "\tWHEN 'YY' THEN '岳阳医院'\n" +
            "\tWHEN 'LH' THEN '龙华医院'\n" +
            "\tWHEN 'XH' THEN '新华医院'\n" +
            "\tWHEN 'LY' THEN '第六人民医院'\n" +
            "\tWHEN 'XK' THEN '胸科医院'\n" +
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
            "FROM HP_QUESTION q where q.state='Enable'", nativeQuery = true)
    List<Map<String, Object>> findExportData();

}
