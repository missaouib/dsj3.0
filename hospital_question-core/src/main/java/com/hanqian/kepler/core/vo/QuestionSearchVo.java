package com.hanqian.kepler.core.vo;

import lombok.Data;

@Data
public class QuestionSearchVo {

    /**
     * 医院名称
     */
    private String hospitalName;

    /**
     * 开始时间
     */
    private String startDate;

    /**
     * 结束时间
     */
    private String endDate;

}
