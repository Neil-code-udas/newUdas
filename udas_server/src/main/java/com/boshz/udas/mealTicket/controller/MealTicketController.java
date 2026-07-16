package com.boshz.udas.mealTicket.controller;

import com.boshz.udas.mealTicket.entity.MealTicket;
import com.boshz.udas.mealTicket.service.MealTicketService;
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
 * 餐券预约表(MealTicket)表控制层
 *
 * @author makejava
 * @since 2026-07-07 17:31:38
 */
@RestController
@RequestMapping("/mealTicket")
public class MealTicketController {
    @Resource
    private MealTicketService mealTicketService;

    /**
     * 分页查询：QueryEntity包装
     */
    @PostMapping("/getListByPage")
    public ResultVO getListByPage(@RequestBody QueryEntity<MealTicket> query) {
        return ResultVOUtil.success(mealTicketService.getListByPage(query));
    }

    /**
     * 根据主键查询单条
     */
    @GetMapping("/getById")
    public ResultVO getById(@RequestParam("id") Long id) {
        return ResultVOUtil.success(mealTicketService.getById(id));
    }

    /**
     * 新增
     */
    @PostMapping("/add")
    public ResultVO add(@RequestBody MealTicket mealTicket) {
        return ResultVOUtil.success(mealTicketService.insert(mealTicket));
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public ResultVO update(@RequestBody MealTicket mealTicket) {
        return ResultVOUtil.success(mealTicketService.update(mealTicket));
    }

    /**
     * 删除
     */
    @PostMapping("/deleteById")
    public ResultVO deleteById(@RequestParam("id") Long id) {
        return ResultVOUtil.success(mealTicketService.deleteById(id));
    }

    /**
     * Excel导入
     */
    @PostMapping("/import")
    public ResultVO importExcel(@RequestParam("file") MultipartFile file) {
        Integer count = mealTicketService.importExcel(file);
        return ResultVOUtil.success(count);
    }

    /**
     * Excel导出（QueryEntity封装查询条件）
     */
    @PostMapping("/export")
    public void export(@RequestBody MealTicket query, HttpServletResponse response) throws IOException {
        mealTicketService.exportExcel(query, response);
    }
}
