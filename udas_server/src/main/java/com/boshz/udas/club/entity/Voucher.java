package com.boshz.udas.club.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel("付款凭证实体")
public class Voucher {
    @ApiModelProperty(value = "主键ID",hidden = true)
    private Long id;
    @ApiModelProperty(value = "凭证单号（系统自动生成）",hidden = true)
    private String voucherNo;
    @ApiModelProperty(value = "收款单位或个人")
    private String payTarget;
    @ApiModelProperty(value = "收款账号")
    private String payAccount;
    @ApiModelProperty(value = "成本中心")
    private String costCenter;
    @ApiModelProperty(value = "经办")
    private String handlePerson;
    @ApiModelProperty(value = "报销金额")
    private BigDecimal payAmount;
    @ApiModelProperty(value = "事项描述")
    private String normalDesc;
    @ApiModelProperty(value = "费用发生时间")
    private Date occurTime;

    @ApiModelProperty(value = "费用发生时间(年月日)",hidden = true)
    private String fullDate;
    @ApiModelProperty(value = "报销金额大写",hidden = true)
    private String payString;

    @ApiModelProperty(value = "是否俱乐部支出")
    private Integer isClubExpense;
    @ApiModelProperty(value = "是否销账")
    private String isItem;
    @ApiModelProperty(value = "申请机构/部门")
    private String applyDept;

    @ApiModelProperty(value = "会费支出类型")
    private String membershipType;

    @ApiModelProperty(value = "报销项目ID")
    private Long itemId;
    @ApiModelProperty(value = "报销项目名称",hidden = true)
    private String itemName;
    @ApiModelProperty(value = "报销科目编码")
    private String subjectCode;
    @ApiModelProperty(value = "报销科目名称",hidden = true)
    private String subjectName;

    @ApiModelProperty(value = "制单人",hidden = true)
    private String createBy;
    @ApiModelProperty(value = "创建时间",hidden = true)
    private Date createTime;
    @ApiModelProperty(value = "更新时间",hidden = true)
    private Date updateTime;
}