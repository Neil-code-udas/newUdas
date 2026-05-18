package com.boshz.udas.report.controller;

import com.boshz.udas.report.entity.ReportUserRole;
import com.boshz.udas.report.service.ReportUserRoleService;
import com.boshz.udas.vo.ResultVO;
import com.boshz.udas.vo.ResultVOUtil;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/report/role")
public class ReportUserRoleController {

    @Resource
    private ReportUserRoleService reportUserRoleService;

    /**
     * 分配/修改用户报表角色
     */
    @PostMapping("/assign")
    public ResultVO assignRole(@RequestParam String reportCode,
                                     @RequestParam String userAccount,
                                     @RequestParam String roleType,
                                     @RequestParam String operateUser) {
        reportUserRoleService.assignUserRole(reportCode, userAccount, roleType, operateUser);
        return ResultVOUtil.success();
    }

    /**
     * 移除用户报表权限
     */
    @PostMapping("/remove")
    public ResultVO removeRole(@RequestParam String reportCode,
                                     @RequestParam String userAccount) {
        reportUserRoleService.removeUserRole(reportCode, userAccount);
        return ResultVOUtil.success();
    }

    /**
     * 查询该报表所有授权用户
     */
    @GetMapping("/list/{reportCode}")
    public ResultVO<List<ReportUserRole>> roleList(@PathVariable String reportCode) {
        return ResultVOUtil.success(reportUserRoleService.getReportUserRoleList(reportCode));
    }

    /**
     * 查询当前用户对应报表角色
     */
    @GetMapping("/get")
    public ResultVO<String> getRole(@RequestParam String reportCode,
                                    @RequestParam String userAccount) {
        return ResultVOUtil.success(reportUserRoleService.getUserReportRole(reportCode, userAccount));
    }
}