package com.hanqian.kepler.core.vo;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
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

    //获取结束时间，真实的应该为选择的时间+1天
    public String getEndDate() {
        if(StrUtil.isBlank(endDate)){
            return "";
        }

        return DateUtil.formatDate(DateUtil.offsetDay(DateUtil.parseDate(endDate), 1));
    }
}
