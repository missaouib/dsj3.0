package com.hanqian.kepler.core.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 问卷数总览 表格数据
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionCountVo {

    private String hospitalName;

    private String objectType;

    private String count;

    private String maxCreateTime;

}
