package com.boshz.udas.secretPersonnel.controller;

import com.boshz.udas.secretPersonnel.entity.SecretPersonnel;
import com.boshz.udas.secretPersonnel.service.SecretPersonnelService;
import com.boshz.udas.vo.QueryEntity;
import com.boshz.udas.vo.ResultVO;
import com.boshz.udas.vo.ResultVOUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.io.IOException;

/**
 * 涉密人员管理表(SecretPersonnel)表控制层
 *
 * @author makejava
 * @since 2026-06-08 17:37:50
 */
@RestController
@RequestMapping("/secretPersonnel")
public class SecretPersonnelController {
    @Resource
    private SecretPersonnelService secretPersonnelService;

    /**
     * 分页查询：QueryEntity包装
     */
    @PostMapping("/getListByPage")
    public ResultVO getListByPage(@RequestBody QueryEntity<SecretPersonnel> query) {
        return ResultVOUtil.success(secretPersonnelService.getListByPage(query));
    }

    /**
     * 根据主键查询单条
     */
    @GetMapping("/getById")
    public ResultVO getById(@RequestParam("id") Long id) {
        return ResultVOUtil.success(secretPersonnelService.getById(id));
    }

    /**
     * 新增
     */
    @PostMapping("/add")
    public ResultVO add(@RequestBody SecretPersonnel secretPersonnel) {
        return ResultVOUtil.success(secretPersonnelService.insert(secretPersonnel));
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public ResultVO update(@RequestBody SecretPersonnel secretPersonnel) {
        return ResultVOUtil.success(secretPersonnelService.update(secretPersonnel));
    }

    /**
     * 删除
     */
    @PostMapping("/deleteById")
    public ResultVO deleteById(@RequestParam("id") Long id) {
        return ResultVOUtil.success(secretPersonnelService.deleteById(id));
    }

    /**
     * Excel导入
     */
    @PostMapping("/import")
    public ResultVO importExcel(@RequestParam("file") MultipartFile file) {
        Integer count = secretPersonnelService.importExcel(file);
        return ResultVOUtil.success(count);
    }

    /**
     * Excel导出（QueryEntity封装查询条件）
     */
    @PostMapping("/export")
    public void export(@RequestBody SecretPersonnel query, HttpServletResponse response) throws IOException {
        secretPersonnelService.exportExcel(query, response);
    }
}
