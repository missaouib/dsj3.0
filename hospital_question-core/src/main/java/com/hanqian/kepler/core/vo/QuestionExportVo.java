package com.hanqian.kepler.core.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

/**
 * excel导出
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionExportVo {

    /**
     * 所属医院
     */
    private String hospitalName;

    /**
     * 调查对象
     */
    private String objectType;

    /**
     * 性别
     */
    private String sex;

    /**
     * 年龄段
     */
    private String ageField;


    // ======== （保洁服务） ========
    /**
     * 室内环境质量
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

}
