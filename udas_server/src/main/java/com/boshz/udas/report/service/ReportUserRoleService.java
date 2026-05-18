package com.boshz.udas.report.service;

import com.boshz.udas.report.entity.ReportUserRole;

import java.util.List;

public interface ReportUserRoleService {
    void assignUserRole(String reportCode, String userAccount, String roleType, String createBy);
    void removeUserRole(String reportCode, String userAccount);
    List<ReportUserRole> getReportUserRoleList(String reportCode);
    String getUserReportRole(String reportCode, String userAccount);
}