package com.boshz.udas.subject.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel("付款凭证实体")
public class Voucher {

    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("凭证单号（系统自动生成）")
    private String voucherNo;

    @ApiModelProperty("申请机构/部门")
    private String applyDept;

    @ApiModelProperty("费用发生时间")
    private Date occurTime;

    @ApiModelProperty("报销项目ID")
    private Long itemId;

    @ApiModelProperty("报销项目名称")
    private String itemName;

    @ApiModelProperty("报销科目编码")
    private String subjectCode;

    @ApiModelProperty("报销科目名称")
    private String subjectName;

    @ApiModelProperty("收款单位或个人")
    private String payTarget;

    @ApiModelProperty("收款账号")
    private String payAccount;

    @ApiModelProperty("报销金额")
    private BigDecimal payAmount;

    @ApiModelProperty("是否俱乐部支出 0-否 1-是")
    private Integer isClubExpense;

    @ApiModelProperty("被慰问人")
    private String condoledPerson;

    @ApiModelProperty("常规报销事项描述")
    private String normalDesc;

    @ApiModelProperty("俱乐部报销事项描述")
    private String clubDesc;

    @ApiModelProperty("会费支出类型")
    private String membershipType;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("制单人")
    private String createBy;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;
}