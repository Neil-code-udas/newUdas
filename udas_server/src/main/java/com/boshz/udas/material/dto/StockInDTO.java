package com.boshz.udas.material.dto;

import java.math.BigDecimal;

public class StockInDTO {
    private String materialName;
    private BigDecimal num;
    private String remark;

    public String getMaterialName() {
        return materialName;
    }
    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }
    public BigDecimal getNum() {
        return num;
    }
    public void setNum(BigDecimal num) {
        this.num = num;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
}