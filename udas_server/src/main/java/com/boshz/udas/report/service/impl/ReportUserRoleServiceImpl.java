package com.boshz.udas.report.service.impl;

import com.boshz.udas.report.entity.ReportUserRole;
import com.boshz.udas.report.mapper.ReportUserRoleMapper;
import com.boshz.udas.report.service.ReportUserRoleService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

@Service
public class ReportUserRoleServiceImpl implements ReportUserRoleService {

    @Resource
    private ReportUserRoleMapper reportUserRoleMapper;

    @Override
    public void assignUserRole(String reportCode, String userAccount, String roleType, String createBy) {
        ReportUserRole old = reportUserRoleMapper.selectByReportAndUser(reportCode, userAccount);
        if (old != null) {
            // 已有角色 → 直接更新
            reportUserRoleMapper.updateRoleType(reportCode, userAccount, roleType, createBy);
            return;
        }
        // 无角色 → 新增
        ReportUserRole role = new ReportUserRole();
        role.setReportCode(reportCode);
        role.setUserAccount(userAccount);
        role.setRoleType(roleType);
        role.setCreateBy(createBy);
        role.setUpdateBy(createBy);
        reportUserRoleMapper.insert(role);
    }

    @Override
    public void removeUserRole(String reportCode, String userAccount) {
        // 直接硬删除
        reportUserRoleMapper.deleteRole(reportCode, userAccount);
    }

    @Override
    public List<ReportUserRole> getReportUserRoleList(String reportCode) {
        return reportUserRoleMapper.selectListByReportCode(reportCode);
    }

    @Override
    public String getUserReportRole(String reportCode, String userAccount) {
        ReportUserRole role = reportUserRoleMapper.selectByReportAndUser(reportCode, userAccount);
        return role == null ? null : role.getRoleType();
    }
}