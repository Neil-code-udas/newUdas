package com.boshz.udas.report.controller;

import com.boshz.udas.report.entity.ReportDef;
import com.boshz.udas.report.service.ReportDefService;
import com.boshz.udas.vo.PageVo;
import com.boshz.udas.vo.QueryEntity;
import com.boshz.udas.vo.ResultVO;
import com.boshz.udas.vo.ResultVOUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportDefService reportDefService;

    /**
     * 报表分页列表（完全按你的格式）
     */
    @PostMapping("/getListByPage")
    public ResultVO<Object> getListByPage(@RequestBody QueryEntity<ReportDef> queryEntity,
                                          @RequestParam String account) {
        if (StringUtils.isEmpty(account)) {
            return ResultVOUtil.error(9000, "请登录");
        }

        // admin 不做数据权限
        if (account.contains("admin")) {
            account = null;
        }

        PageVo pageVo = reportDefService.getListByPage(queryEntity, account);
        return ResultVOUtil.success(pageVo);
    }

}