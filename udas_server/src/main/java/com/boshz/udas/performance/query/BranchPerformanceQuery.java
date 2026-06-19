package com.boshz.udas.performance.query;

public class BranchPerformanceQuery {
    /** 统计周期，必填 */
    private String period;
    /** 网点机构编码，必填，仅查单机构 */
    private String branchCode;
    /** 网点名称模糊检索（可选） */
    private String branchName;

    // getter & setter
    public String getPeriod() {
        return period;
    }
    public void setPeriod(String period) {
        this.period = period;
    }
    public String getBranchCode() {
        return branchCode;
    }
    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }
    public String getBranchName() {
        return branchName;
    }
    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }
}