package com.hanqian.kepler.core.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionEchartVo {

    /**
     * x轴 名称列表
     */
    private String[] xlist;

    /**
     * y轴 数值列表
     */
    private BigDecimal[] ylist;

}
