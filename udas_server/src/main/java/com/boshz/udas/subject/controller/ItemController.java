package com.boshz.udas.subject.controller;


import com.boshz.udas.subject.entity.ItemConfig;
import com.boshz.udas.subject.service.ItemConfigService;
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
@RequestMapping("/item")
@Api(tags = "项目接口")
public class ItemController {

    @Resource
    private ItemConfigService itemConfigService;

    @PostMapping("/add")
    public ResultVO<Integer> add(@RequestBody ItemConfig entity){
        return ResultVOUtil.success(itemConfigService.add(entity));
    }

    @PostMapping("/update")
    public ResultVO<Integer> update(@RequestBody ItemConfig entity){
        return ResultVOUtil.success(itemConfigService.update(entity));
    }

    @GetMapping("/delete")
    public ResultVO<Integer> delete(@RequestParam Long id){
        return ResultVOUtil.success(itemConfigService.deleteById(id));
    }

    @GetMapping("/getById")
    public ResultVO<ItemConfig> getById(@RequestParam Long id){
        return ResultVOUtil.success(itemConfigService.getById(id));
    }

    /**项目全局下拉+模糊（不限科目）*/
    @GetMapping("/listAll")
    @ApiOperation("科目下拉模糊查询，不传参数查全量")
    public ResultVO<List<ItemConfig>> listAll(@RequestParam(required = false) String keyword){
        return ResultVOUtil.success(itemConfigService.listAll(keyword));
    }

    /**选中科目后，只加载该科目项目*/
    @GetMapping("/listBySubject")
    public ResultVO<List<ItemConfig>> listBySubject(@RequestParam String subjectCode){
        return ResultVOUtil.success(itemConfigService.listBySubjectCode(subjectCode));
    }

    /**项目配置管理页面列表*/
    @PostMapping("/config/page")
    @ApiOperation("项目配置分页｜科目下钻列表")
    public ResultVO<PageVo<List<ItemConfig>>> page(@RequestBody QueryEntity<ItemConfig> queryEntity){
        return ResultVOUtil.success(itemConfigService.pageList(queryEntity));
    }
}