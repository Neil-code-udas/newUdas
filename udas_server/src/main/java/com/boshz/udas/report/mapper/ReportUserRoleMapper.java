package com.boshz.udas.report.mapper;

import com.boshz.udas.report.entity.ReportUserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface ReportUserRoleMapper {

    // 查询用户在指定报表的角色
    ReportUserRole selectByReportAndUser(
            @Param("reportCode") String reportCode,
            @Param("userAccount") String userAccount
    );

    // 新增用户角色
    int insert(ReportUserRole role);

    // 更新角色类型
    int updateRoleType(
            @Param("reportCode") String reportCode,
            @Param("userAccount") String userAccount,
            @Param("roleType") String roleType,
            @Param("updateBy") String updateBy
    );

    // 直接删除用户角色（硬删除）
    int deleteRole(
            @Param("reportCode") String reportCode,
            @Param("userAccount") String userAccount
    );

    // 查询报表下所有用户角色
    List<ReportUserRole> selectListByReportCode(
            @Param("reportCode") String reportCode
    );
}