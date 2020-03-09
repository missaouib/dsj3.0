package com.hanqian.kepler.core.entity.primary.question;

import com.hanqian.kepler.common.base.entity.BaseEntity;
import com.hanqian.kepler.common.enums.BaseEnumManager;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "hp_question")
public class Question extends BaseEntity {

    /**
     * 所属医院
     */
    private String hospitalName;
    /**
     * 调查对象
     * Patient ： 患者（病患及病患家属）
     * PatientFamily ： 患者家属（病患及病患家属）
     * Doctor ： 医生（医护人员）
     * Nurse ： 护士（医护人员）
     * Other ： 其他（医护人员）
     */
    @Enumerated(EnumType.STRING)
    private BaseEnumManager.ObjectTypeEnum ObjectType;
    /**
     * 性别
     * male ： 男
     * female ： 女
     */
    @Enumerated(EnumType.STRING)
    private BaseEnumManager.SexEnum sex;
    /**
     * 年龄段（new）
     * 1: 18岁以下
     * 2 : 19~40
     * 4 : 41~60
     * 6 : 61岁以上
     */
    private Integer ageField;

    // ======== （保洁服务） ========
    /**
     * 室内环境质量
     *
     * 1 ：未接触
     * 2 ：不满意
     * 3 ：不太满意
     * 4 ： 一般
     * 5 ： 较满意
     * 6 ： 非常满意
     */
    private Integer qualityIndoor;
    /**
     * 室外环境质量
     */
    private Integer qualityOutdoor;
    /**
     * 厕所卫生状况
     */
    private Integer toiletHygiene;
    /**
     * 保洁服务态度
     */
    private Integer cleanService;

    // ======== （安保服务） ========
    /**
     * 日常安保工作
     */
    private Integer dailySecurity;
    /**
     * 意外处置及时性
     */
    private Integer accidentalDisposal;
    /**
     * 安保服务态度
     */
    private Integer securityService;

    // ======== （餐品服务：医护人员版） ========
    /**
     * 菜品价格
     */
    private Integer dishPrice;
    /**
     * 就餐环境
     */
    private Integer diningEnvironment;
    /**
     * 餐饮服务态度
     */
    private Integer foodService;

    // ======== （餐品服务：病患及病患家属版） ========
    /**
     * 送餐的及时性
     */
    private Integer deliveryTimeliness;
    /**
     * 餐品口味营养
     */
    private Integer foodNutrition;
    /**
     * 送餐服务态度
     */
    private Integer diningAttitude;

    // ======== （运送服务） ========
    /**
     * 运送及时性
     */
    private Integer transportTimeliness;
    /**
     * 运送准确性
     */
    private Integer transportAccuracy;
    /**
     * 运送服务态度
     */
    private Integer transportService;

    // ======== （运维服务） ========
    /**
     * 维修及时性
     */
    private Integer repairTimeliness;
    /**
     * 维修质量
     */
    private Integer repairQuality;
    /**
     * 电梯运状态
     */
    private Integer elevatorStatus;
    /**
     * 运维服务态度
     */
    private Integer operationService;


    /**
     * 针对医院后勤服务，您还有哪些建议（选填）（新版去掉了）
     */
    private String suggest;

}
