package com.boshz.udas.subject.controller;


import com.boshz.udas.subject.entity.Voucher;
import com.boshz.udas.subject.service.VoucherService;
import com.boshz.udas.vo.ResultVO;
import com.boshz.udas.vo.ResultVOUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/voucher")
@Api(tags = "付款凭证管理接口")
public class VoucherController {

    @Resource
    private VoucherService voucherService;

    @PostMapping("/add")
    @ApiOperation("新增付款凭证（自动生成凭证单号）")
    public ResultVO<Integer> add(@RequestBody Voucher voucher) {
        return ResultVOUtil.success(voucherService.add(voucher));
    }

    @PostMapping("/update")
    @ApiOperation("编辑付款凭证")
    public ResultVO<Integer> update(@RequestBody Voucher voucher) {
        return ResultVOUtil.success(voucherService.update(voucher));
    }

    @GetMapping("/delete")
    @ApiOperation("根据ID删除凭证")
    @ApiImplicitParam(name = "id", value = "凭证主键ID", required = true, paramType = "query")
    public ResultVO<Integer> delete(@RequestParam Long id) {
        return ResultVOUtil.success(voucherService.deleteById(id));
    }

    @GetMapping("/getById")
    @ApiOperation("根据ID查询凭证详情（编辑回显）")
    @ApiImplicitParam(name = "id", value = "凭证主键ID", required = true, paramType = "query")
    public ResultVO<Voucher> getById(@RequestParam Long id) {
        return ResultVOUtil.success(voucherService.getById(id));
    }

    @GetMapping("/page")
    @ApiOperation("凭证列表条件查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "voucherNo", value = "凭证单号", required = false, paramType = "query"),
            @ApiImplicitParam(name = "applyDept", value = "申请部门", required = false, paramType = "query"),
            @ApiImplicitParam(name = "subjectCode", value = "科目编码", required = false, paramType = "query"),
            @ApiImplicitParam(name = "startDate", value = "开始日期", required = false, paramType = "query"),
            @ApiImplicitParam(name = "endDate", value = "结束日期", required = false, paramType = "query")
    })
    public ResultVO<List<Voucher>> page(
            @RequestParam(required = false) String voucherNo,
            @RequestParam(required = false) String applyDept,
            @RequestParam(required = false) String subjectCode,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate
    ) {
        return ResultVOUtil.success(voucherService.pageList(voucherNo, applyDept, subjectCode, startDate, endDate));
    }
}