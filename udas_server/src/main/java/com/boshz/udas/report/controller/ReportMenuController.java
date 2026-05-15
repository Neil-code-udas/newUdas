package com.boshz.udas.report.controller;


import com.boshz.udas.report.entity.ReportDef;
import com.boshz.udas.report.mapper.ReportDefMapper;
import com.boshz.udas.vo.ResultVO;
import com.boshz.udas.vo.ResultVOUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportMenuController {
    private final ReportDefMapper reportDefMapper;

    @GetMapping("/menu")
    public ResultVO<Object> menu() {
        List<ReportDef> list = reportDefMapper.selectAll();
        List<Map<String, Object>> menus = list.stream().map(def -> {
            Map<String, Object> map = new HashMap<>();
            map.put("name", def.getReportName());
            map.put("path", "/report/" + def.getReportCode());
            map.put("component", "ReportIndex");
            return map;
        }).collect(Collectors.toList());
        return ResultVOUtil.success(menus);
    }
}