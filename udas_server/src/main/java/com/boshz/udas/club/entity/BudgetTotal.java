package com.boshz.udas.club.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel("收支预决算汇总")
public class BudgetTotal {
    @ApiModelProperty(value = "主键",hidden = true)
    private Long id;
    @ApiModelProperty(value = "科目编码")
    private String subjectCode;
    @ApiModelProperty(value = "合计预算")
    private BigDecimal budgetAmount;
    @ApiModelProperty(value = "合计实际")
    private BigDecimal actualAmount;
    @ApiModelProperty(value = "年度")
    private Integer budgetYear;
    @ApiModelProperty(value = "收支类型：1收入 2支出")
    private Integer inOutType;
    @ApiModelProperty(value = "更新时间",hidden = true)
    private Date updateTime;
}