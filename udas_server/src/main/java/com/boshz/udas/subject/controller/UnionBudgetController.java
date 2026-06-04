package com.boshz.udas.subject.controller;


import com.boshz.udas.subject.dto.TableDataDTO;
import com.boshz.udas.subject.service.UnionBudgetService;
import com.boshz.udas.vo.ResultVO;
import com.boshz.udas.vo.ResultVOUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/gonghui/budget")
@Api(tags = "工会预决算报表接口")
public class UnionBudgetController {

    @Resource
    private UnionBudgetService unionBudgetService;

    @GetMapping("/table")
    @ApiOperation("获取预决算树形表格数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "yearList", value = "查询年度集合", required = true, paramType = "query"),
            @ApiImplicitParam(name = "subjectCode", value = "筛选科目编码(选填，自动带出所有下级)", required = false, paramType = "query"),
            @ApiImplicitParam(name = "subjectName", value = "科目名称模糊检索(选填)", required = false, paramType = "query")
    })
    public ResultVO<TableDataDTO> getBudgetTable(@RequestParam List<Integer> yearList,
                                                 @RequestParam(required = false) String subjectCode,
                                                 @RequestParam(required = false) String subjectName) {
        TableDataDTO tableData = unionBudgetService.getTableData(yearList, subjectCode, subjectName);
        return ResultVOUtil.success(tableData);
    }
}