package com.boshz.udas.club.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel("收入预算明细")
public class IncomeBudgetDetail {
    @ApiModelProperty(value = "主键",hidden = true)
    private Long id;
    @ApiModelProperty(value = "项目ID")
    private Long itemId;
    @ApiModelProperty(value = "科目编码")
    private String subjectCode;
    @ApiModelProperty(value = "预算金额")
    private BigDecimal budgetAmount;
    @ApiModelProperty(value = "实际金额")
    private BigDecimal actualAmount;
    @ApiModelProperty(value = "预算年度")
    private Integer budgetYear;
    @ApiModelProperty(value = "备注")
    private String remark;
    @ApiModelProperty(value = "创建人",hidden = true)
    private String createBy;
    @ApiModelProperty(value = "创建时间",hidden = true)
    private Date createTime;
    @ApiModelProperty(value = "更新时间",hidden = true)
    private Date updateTime;
}