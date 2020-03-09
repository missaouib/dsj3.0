package com.hanqian.kepler.web.controller.question;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.hanqian.kepler.common.bean.result.AjaxResult;
import com.hanqian.kepler.common.enums.BaseEnumManager;
import com.hanqian.kepler.core.entity.primary.question.Question;
import com.hanqian.kepler.core.service.question.QuestionService;
import com.hanqian.kepler.web.controller.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/question")
public class QuestionController extends BaseController {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private QuestionService questionService;

    @CrossOrigin
    @GetMapping("save")
    @ResponseBody
    public Object save(Question question){
        logger.info(StrUtil.format("READY SAVE ->【{}】", JSONUtil.toJsonStr(question)));

        if(StrUtil.isBlank(question.getHospitalName())) return AjaxResult.error("未获取到所属医院【hospitalName】");
        if(ObjectUtil.isNull(question.getObjectType())) return AjaxResult.error("未获取到调查对象【ObjectType】");
        if(ObjectUtil.isNull(question.getSex())) return AjaxResult.error("未获取到性别【sex】");
        if(ObjectUtil.isNull(question.getAgeField())) return AjaxResult.error("未获取到年龄段【ageField】");

        // ======== （保洁服务） ========
        if(ObjectUtil.isNull(question.getQualityIndoor())) return AjaxResult.error("室内环境质量 为空【qualityIndoor】");
        if(ObjectUtil.isNull(question.getQualityOutdoor())) return AjaxResult.error("室外环境质量 为空【qualityOutdoor】");
        if(ObjectUtil.isNull(question.getToiletHygiene())) return AjaxResult.error("厕所卫生状况 为空【toiletHygiene】");
        if(ObjectUtil.isNull(question.getCleanService())) return AjaxResult.error("保洁服务态度 为空【cleanService】");

        // ======== （安保服务） ========
        if(ObjectUtil.isNull(question.getDailySecurity())) return AjaxResult.error("日常安保工作 为空【dailySecurity】");
        if(ObjectUtil.isNull(question.getAccidentalDisposal())) return AjaxResult.error("意外处置及时性 为空【accidentalDisposal】");
        if(ObjectUtil.isNull(question.getSecurityService())) return AjaxResult.error("安保服务态度 为空【securityService】");

        // ======== （餐品服务：医护人员版） ========
        if(StrUtil.equalsAny(question.getObjectType().name(), new String[]{BaseEnumManager.ObjectTypeEnum.Patient.name(), BaseEnumManager.ObjectTypeEnum.PatientFamily.name()})){
            if(ObjectUtil.isNull(question.getDishPrice())) return AjaxResult.error("菜品价格 为空【dishPrice】");
            if(ObjectUtil.isNull(question.getDiningEnvironment())) return AjaxResult.error("就餐环境 为空【diningEnvironment】");
            if(ObjectUtil.isNull(question.getFoodService())) return AjaxResult.error("餐饮服务态度 为空【foodService】");
        }

        // ======== （餐品服务：病患及病患家属版） ========
        if(StrUtil.equalsAny(question.getObjectType().name(), new String[]{BaseEnumManager.ObjectTypeEnum.Doctor.name(), BaseEnumManager.ObjectTypeEnum.Nurse.name(), BaseEnumManager.ObjectTypeEnum.Other.name()})){
            if(ObjectUtil.isNull(question.getDeliveryTimeliness())) return AjaxResult.error("送餐的及时性 为空【deliveryTimeliness】");
            if(ObjectUtil.isNull(question.getFoodNutrition())) return AjaxResult.error("餐品口味营养 为空【foodNutrition】");
            if(ObjectUtil.isNull(question.getDiningAttitude())) return AjaxResult.error("送餐服务态度 为空【diningAttitude】");
        }

        // ======== （运送服务） ========
        if(ObjectUtil.isNull(question.getTransportTimeliness())) return AjaxResult.error("运送及时性 为空【transportTimeliness】");
        if(ObjectUtil.isNull(question.getTransportAccuracy())) return AjaxResult.error("运送准确性 为空【transportAccuracy】");
        if(ObjectUtil.isNull(question.getTransportService())) return AjaxResult.error("运送服务态度 为空【transportService】");

        // ======== （运维服务） ========
        if(ObjectUtil.isNull(question.getRepairTimeliness())) return AjaxResult.error("维修及时性 为空【repairTimeliness】");
        if(ObjectUtil.isNull(question.getRepairQuality())) return AjaxResult.error("维修质量 为空【repairQuality】");
        if(ObjectUtil.isNull(question.getElevatorStatus())) return AjaxResult.error("电梯运状态 为空【elevatorStatus】");
        if(ObjectUtil.isNull(question.getOperationService())) return AjaxResult.error("运维服务态度 为空【operationService】");

        question = questionService.save(question);
        logger.info(StrUtil.format("SAVE->【{}】", JSONUtil.toJsonStr(question)));
        return AjaxResult.success("保存成功");
    }

}
