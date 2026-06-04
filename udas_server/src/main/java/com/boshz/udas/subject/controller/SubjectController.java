package com.boshz.udas.subject.controller;


import com.boshz.udas.subject.entity.UnionSubject;
import com.boshz.udas.subject.service.SubjectService;
import com.boshz.udas.vo.PageVo;
import com.boshz.udas.vo.QueryEntity;
import com.boshz.udas.vo.ResultVO;
import com.boshz.udas.vo.ResultVOUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/subject")
@Api(tags = "科目接口")
public class SubjectController {

    @Resource
    private SubjectService subjectService;

    //原有下拉模糊
    @GetMapping("/listLike")
    @ApiOperation("科目下拉模糊查询(凭证单据页)")
    @ApiImplicitParam(name = "keyword",value = "搜索关键字(科目编码/名称)",required = false,paramType = "query")
    public ResultVO<List<UnionSubject>> listLike(@RequestParam(required = false) String keyword) {
        return ResultVOUtil.success(subjectService.listLike(keyword));
    }

    //=====配置页面CRUD=====
    @PostMapping("/config/add")
    @ApiOperation("新增科目（科目配置页）")
    public ResultVO<Integer> add(@RequestBody UnionSubject entity){
        return ResultVOUtil.success(subjectService.add(entity));
    }

    @PostMapping("/config/update")
    @ApiOperation("编辑科目（科目配置页）")
    public ResultVO<Integer> update(@RequestBody UnionSubject entity){
        return ResultVOUtil.success(subjectService.update(entity));
    }

    @GetMapping("/config/delete")
    @ApiOperation("删除科目")
    @ApiImplicitParam(name="id",value="主键id",required=true)
    public ResultVO<Integer> delete(@RequestParam Long id){
        return ResultVOUtil.success(subjectService.deleteById(id));
    }

    @GetMapping("/config/getById")
    @ApiOperation("根据ID查询科目详情")
    @ApiImplicitParam(name="id",value="主键id",required=true)
    public ResultVO<UnionSubject> get(@RequestParam Long id){
        return ResultVOUtil.success(subjectService.getById(id));
    }

    @PostMapping("/config/page")
    @ApiOperation("科目配置分页列表")
    public ResultVO<PageVo<List<UnionSubject>>> page(@RequestBody QueryEntity<UnionSubject> queryEntity){
        return ResultVOUtil.success(subjectService.pageList(queryEntity));
    }
}