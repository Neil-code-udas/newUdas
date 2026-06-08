package com.boshz.udas.club.controller;


import com.boshz.udas.club.entity.OutBudgetDetail;
import com.boshz.udas.club.service.OutBudgetDetailService;
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
@RequestMapping("/budget/out")
@Api(tags = "支出预算明细")
public class OutBudgetDetailController {
    @Resource
    private OutBudgetDetailService service;

    @PostMapping("/add")
    @ApiOperation("手动新增")
    public ResultVO<Integer> add(@RequestBody OutBudgetDetail dto){
        return ResultVOUtil.success(service.add(dto));
    }
    @PostMapping("/update")
    @ApiOperation("编辑")
    public ResultVO<Integer> update(@RequestBody OutBudgetDetail dto){
        return ResultVOUtil.success(service.update(dto));
    }
    @GetMapping("/delete")
    @ApiOperation("删除")
    public ResultVO<Integer> delete(@RequestParam Long id){
        return ResultVOUtil.success(service.delete(id));
    }
    @GetMapping("/getById")
    @ApiOperation("详情")
    public ResultVO<OutBudgetDetail> getById(@RequestParam Long id){
        return ResultVOUtil.success(service.getById(id));
    }
    @PostMapping("/page")
    @ApiOperation("分页列表")
    public ResultVO<PageVo<List<OutBudgetDetail>>> page(@RequestBody QueryEntity<OutBudgetDetail> q){
        return ResultVOUtil.success(service.pageList(q));
    }
}