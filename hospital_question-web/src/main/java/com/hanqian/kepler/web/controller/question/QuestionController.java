package com.hanqian.kepler.web.controller.question;

import cn.hutool.core.util.EnumUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.hanqian.kepler.common.bean.NameValueVo;
import com.hanqian.kepler.common.bean.result.AjaxResult;
import com.hanqian.kepler.common.enums.BaseEnumManager;
import com.hanqian.kepler.core.entity.primary.question.Question;
import com.hanqian.kepler.core.service.question.QuestionService;
import com.hanqian.kepler.core.vo.QuestionEchartVo;
import com.hanqian.kepler.core.vo.QuestionSearchVo;
import com.hanqian.kepler.web.controller.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/question")
public class QuestionController extends BaseController {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${kepler.questionLogMaxCount}")
    private Integer questionLogMaxCount;

    @Autowired
    private QuestionService questionService;

    /**
     * 手机端接口 - 保存插入数据
     */
    @CrossOrigin
    @GetMapping("save")
    @ResponseBody
    public AjaxResult save(Question question){
        logger.info(StrUtil.format("READY SAVE ->【{}】", JSONUtil.toJsonStr(question)));

        if(StrUtil.isBlank(question.getHospitalName())) return AjaxResult.error("未获取到所属医院【hospitalName】");
        if(ObjectUtil.isNull(question.getObjectType())) return AjaxResult.error("未获取到调查对象【ObjectType】");
        if(ObjectUtil.isNull(question.getSex())) return AjaxResult.error("未获取到性别【sex】");
        if(ObjectUtil.isNull(question.getAgeField())) return AjaxResult.error("未获取到年龄段【ageField】");

        if(questionService.getCountByObjectAndHospitalName(question.getHospitalName(), question.getObjectType())>questionLogMaxCount) return AjaxResult.error("样本数量已充足");

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
	        if(ObjectUtil.isNull(question.getDeliveryTimeliness())) return AjaxResult.error("送餐的及时性 为空【deliveryTimeliness】");
	        if(ObjectUtil.isNull(question.getFoodNutrition())) return AjaxResult.error("餐品口味营养 为空【foodNutrition】");
	        if(ObjectUtil.isNull(question.getDiningAttitude())) return AjaxResult.error("送餐服务态度 为空【diningAttitude】");
        }

        // ======== （餐品服务：病患及病患家属版） ========
        if(StrUtil.equalsAny(question.getObjectType().name(), new String[]{BaseEnumManager.ObjectTypeEnum.Doctor.name(), BaseEnumManager.ObjectTypeEnum.Nurse.name(), BaseEnumManager.ObjectTypeEnum.Other.name()})){
	        if(ObjectUtil.isNull(question.getDishPrice())) return AjaxResult.error("菜品价格 为空【dishPrice】");
	        if(ObjectUtil.isNull(question.getDiningEnvironment())) return AjaxResult.error("就餐环境 为空【diningEnvironment】");
	        if(ObjectUtil.isNull(question.getFoodService())) return AjaxResult.error("餐饮服务态度 为空【foodService】");
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

        question.setObjectTypeSort(question.getObjectType().key());
        question = questionService.save(question);
        logger.info(StrUtil.format("SAVE->【{}】", JSONUtil.toJsonStr(question)));
        return AjaxResult.success("保存成功");
    }

    /**
     * 手机端接口 - 检查当前医院某个调查对象大类样本数量是否已经充足
     */
    @CrossOrigin
    @GetMapping("checkCount")
    @ResponseBody
    public AjaxResult checkCount(String hospitalName, String objectType){
        if(StrUtil.isBlank(hospitalName)) return AjaxResult.error("未获取到所属医院 为空 【hospitalName】");
        if(StrUtil.isBlank(objectType)) return AjaxResult.error("未获取到调查对象 为空 【objectType】");
        BaseEnumManager.ObjectTypeEnum objectTypeEnum = null;
        try{
            objectTypeEnum = BaseEnumManager.ObjectTypeEnum.valueOf(objectType);
        }catch (Exception e){
            return AjaxResult.error("请检查【调查对象字段值】【objectType】");
        }
        return questionService.getCountByObjectAndHospitalName(hospitalName, objectTypeEnum)<=questionLogMaxCount ? AjaxResult.success("检查通过") : AjaxResult.warn("样本数量已充足");
    }



    /**
     * 获取到所有医院名列表
     */
    @GetMapping("findHospitalNameList")
    @ResponseBody
    public AjaxResult findHospitalNameList(){
        List<String> names = EnumUtil.getNames(BaseEnumManager.HospitalName.class);
        List<NameValueVo> nameValueVoList = new ArrayList<>();
        names.forEach(name->{
            BaseEnumManager.HospitalName hospitalName = BaseEnumManager.HospitalName.valueOf(name);
            NameValueVo nameValueVo = new NameValueVo(hospitalName.name(), hospitalName.value());
            nameValueVoList.add(nameValueVo);
        });
        return AjaxResult.success("获取成功", nameValueVoList);
    }

    /**
     * 调查对象统计表数据_调查对象
     */
    @GetMapping("bar_data_objectType")
    @ResponseBody
    public QuestionEchartVo bar_data_objectType(QuestionSearchVo questionSearch){
        List<Object[]> objects = questionService.findGroupData("objectType", questionSearch);
        String[] xList = new String[objects.size()];
        BigDecimal[] yList = new BigDecimal[objects.size()];
        for(int i=0;i<objects.size();i++){
            Object[] oArr = objects.get(i);
            String name = String.valueOf(oArr[0]);
            try {
                xList[i] = BaseEnumManager.ObjectTypeEnum.valueOf(name).value();
            }catch (Exception e){
                xList[i] = name;
            }

            yList[i] = NumberUtil.toBigDecimal(String.valueOf(oArr[1]));
        }
        return new QuestionEchartVo(xList, yList);
    }

    /**
     * 调查对象统计表数据_性别
     */
    @GetMapping("bar_data_sex")
    @ResponseBody
    public QuestionEchartVo bar_data_sex(QuestionSearchVo questionSearch){
        List<Object[]> objects = questionService.findGroupData("sex", questionSearch);
        String[] xList = new String[objects.size()];
        BigDecimal[] yList = new BigDecimal[objects.size()];
        for(int i=0;i<objects.size();i++){
            Object[] oArr = objects.get(i);
            String name = String.valueOf(oArr[0]);
            try {
                xList[i] = BaseEnumManager.SexEnum.valueOf(name).value();
            }catch (Exception e){
                xList[i] = name;
            }

            yList[i] = NumberUtil.toBigDecimal(String.valueOf(oArr[1]));
        }
        return new QuestionEchartVo(xList, yList);
    }

    /**
     * 调查对象统计表数据_年龄段
     */
    @GetMapping("bar_data_ageField")
    @ResponseBody
    public QuestionEchartVo bar_data_ageField(QuestionSearchVo questionSearch){
        List<Object[]> objects = questionService.findGroupData("ageField", questionSearch);
        String[] xList = new String[objects.size()];
        BigDecimal[] yList = new BigDecimal[objects.size()];
        for(int i=0;i<objects.size();i++){
            Object[] oArr = objects.get(i);
            String name = String.valueOf(oArr[0]);
            switch (name){
                case "1": xList[i] = "18岁以下"; break;
                case "2": xList[i] = "19~40"; break;
                case "4": xList[i] = "41~60"; break;
                case "6": xList[i] = "61岁以上"; break;
                default: xList[i] = "其他";
            }
            yList[i] = NumberUtil.toBigDecimal(String.valueOf(oArr[1]));
        }
        return new QuestionEchartVo(xList, yList);
    }

    /**
     * 获取统计表数据（具体满意度值）
     */
    @GetMapping("bar_data")
    @ResponseBody
    public QuestionEchartVo bar_data(String itemName, QuestionSearchVo questionSearch){
        List<Object[]> objects = questionService.findGroupData(itemName, questionSearch);
        String[] xList = new String[objects.size()];
        BigDecimal[] yList = new BigDecimal[objects.size()];
        for(int i=0;i<objects.size();i++){
            Object[] oArr = objects.get(i);
            String name = String.valueOf(oArr[0]);
            switch (name){
                case "1": xList[i] = "未接触"; break;
                case "2": xList[i] = "不满意"; break;
                case "3": xList[i] = "不太满意"; break;
                case "4": xList[i] = "一般"; break;
                case "5": xList[i] = "较满意"; break;
                case "6": xList[i] = "非常满意"; break;
                default: xList[i] = "未知";
            }
            yList[i] = NumberUtil.toBigDecimal(String.valueOf(oArr[1]));
        }
        return new QuestionEchartVo(xList, yList);
    }

}
