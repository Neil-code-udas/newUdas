package com.boshz.udas.club.controller;


import com.boshz.udas.club.entity.BudgetTotal;
import com.boshz.udas.club.service.BudgetTotalService;
import com.boshz.udas.vo.PageVo;
import com.boshz.udas.vo.QueryEntity;
import com.boshz.udas.vo.ResultVO;
import com.boshz.udas.vo.ResultVOUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/budget/total")
@Api(tags = "收支预决算汇总(仅查询)")
public class BudgetTotalController {
    @Resource
    private BudgetTotalService service;

    @PostMapping("/page")
    @ApiOperation("汇总分页查询")
    public ResultVO<PageVo<List<BudgetTotal>>> page(@RequestBody QueryEntity<BudgetTotal> q){
        return ResultVOUtil.success(service.pageList(q));
    }
}