package com.boshz.udas.club.vo;

import java.math.BigDecimal;
import java.util.List;

/**
 * 分组块：收入会费 / 收入经费 / 支出会费 / 支出经费
 * 包含多条明细 + 当前分组合计金额
 */
public class ClubGroupVO {
    // 当前分组总金额
    private BigDecimal subTotal;
    // 分组下所有流水明细
    private List<ClubItemDetailVO> detailList;

    // getter、setter
    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }

    public List<ClubItemDetailVO> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<ClubItemDetailVO> detailList) {
        this.detailList = detailList;
    }
}