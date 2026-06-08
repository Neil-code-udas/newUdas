package com.boshz.udas.club.controller;


import com.boshz.udas.club.entity.IncomeBudgetDetail;
import com.boshz.udas.club.service.IncomeBudgetDetailService;
import com.boshz.udas.vo.PageVo;
import com.boshz.udas.vo.QueryEntity;
import com.boshz.udas.vo.ResultVO;
import com.boshz.udas.vo.ResultVOUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/budget/income")
@Api(tags = "收入预算明细")
public class IncomeBudgetDetailController {
    @Resource
    private IncomeBudgetDetailService service;

    @PostMapping("/add")
    @ApiOperation("新增")
    public ResultVO<Integer> add(@RequestBody IncomeBudgetDetail dto){
        return ResultVOUtil.success(service.add(dto));
    }
    @PostMapping("/update")
    @ApiOperation("编辑")
    public ResultVO<Integer> update(@RequestBody IncomeBudgetDetail dto){
        return ResultVOUtil.success(service.update(dto));
    }
    @GetMapping("/delete")
    @ApiOperation("删除")
    public ResultVO<Integer> delete(@RequestParam Long id){
        return ResultVOUtil.success(service.delete(id));
    }
    @GetMapping("/getById")
    @ApiOperation("详情")
    public ResultVO<IncomeBudgetDetail> getById(@RequestParam Long id){
        return ResultVOUtil.success(service.getById(id));
    }
    @PostMapping("/page")
    @ApiOperation("分页列表")
    public ResultVO<PageVo<List<IncomeBudgetDetail>>> page(@RequestBody QueryEntity<IncomeBudgetDetail> q){
        return ResultVOUtil.success(service.pageList(q));
    }
}