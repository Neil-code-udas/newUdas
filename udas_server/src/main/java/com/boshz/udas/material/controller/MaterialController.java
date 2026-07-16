package com.boshz.udas.material.controller;

import com.boshz.udas.material.entity.Material;
import com.boshz.udas.material.service.MaterialService;
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
 * 办公用品基础档案(Material)表控制层
 *
 * @author makejava
 * @since 2026-07-07 15:27:46
 */
@RestController
@RequestMapping("/material")
public class MaterialController {
    @Resource
    private MaterialService materialService;

    /**
     * 分页查询：QueryEntity包装
     */
    @PostMapping("/getListByPage")
    public ResultVO getListByPage(@RequestBody QueryEntity<Material> query) {
        return ResultVOUtil.success(materialService.getListByPage(query));
    }

    /**
     * 根据主键查询单条
     */
    @GetMapping("/getById")
    public ResultVO getById(@RequestParam("id") Long id) {
        return ResultVOUtil.success(materialService.getById(id));
    }

    /**
     * 新增
     */
    @PostMapping("/add")
    public ResultVO add(@RequestBody Material material) {
        return materialService.insert(material);
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public ResultVO update(@RequestBody Material material) {
        return materialService.update(material);
    }

    /**
     * 删除
     */
    @PostMapping("/deleteById")
    public ResultVO deleteById(@RequestParam("id") Long id) {
        return ResultVOUtil.success(materialService.deleteById(id));
    }

    /**
     * Excel导入
     */
    @PostMapping("/import")
    public ResultVO importExcel(@RequestParam("file") MultipartFile file) {
        Integer count = materialService.importExcel(file);
        return ResultVOUtil.success(count);
    }

    /**
     * Excel导出（QueryEntity封装查询条件）
     */
    @PostMapping("/export")
    public void export(@RequestBody Material query, HttpServletResponse response) throws IOException {
        materialService.exportExcel(query, response);
    }

    /**
     * 物资列表查询
     * @param materialName 物资名称（模糊查询，非必传）
     * @return 物资集合
     */
    @GetMapping("/list")
    public ResultVO list(@RequestParam(required = false) String materialName) {
        return materialService.list(materialName);
    }
}
