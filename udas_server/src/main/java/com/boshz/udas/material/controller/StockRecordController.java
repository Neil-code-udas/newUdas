package com.boshz.udas.material.controller;

import com.boshz.udas.material.dto.RecordQueryDTO;
import com.boshz.udas.material.dto.StockInDTO;
import com.boshz.udas.material.dto.StockOutDTO;
import com.boshz.udas.material.entity.StockRecord;
import com.boshz.udas.material.service.StockRecordService;
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
 * 出入库台账流水(StockRecord)表控制层
 *
 * @author makejava
 * @since 2026-07-07 15:27:46
 */
@RestController
@RequestMapping("/material/stockRecord")
public class StockRecordController {
    @Resource
    private StockRecordService stockRecordService;

    /**
     * 分页查询：QueryEntity包装
     */
    @PostMapping("/getListByPage")
    public ResultVO getListByPage(@RequestBody QueryEntity<StockRecord> query) {
        return ResultVOUtil.success(stockRecordService.getListByPage(query));
    }

    /**
     * 根据主键查询单条
     */
    @GetMapping("/getById")
    public ResultVO getById(@RequestParam("id") Long id) {
        return ResultVOUtil.success(stockRecordService.getById(id));
    }

    /**
     * 新增
     */
    @PostMapping("/add")
    public ResultVO add(@RequestBody StockRecord stockRecord) {
        return ResultVOUtil.success(stockRecordService.insert(stockRecord));
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public ResultVO update(@RequestBody StockRecord stockRecord) {
        return ResultVOUtil.success(stockRecordService.update(stockRecord));
    }

    /**
     * 删除
     */
    @PostMapping("/deleteById")
    public ResultVO deleteById(@RequestParam("id") Long id) {
        return ResultVOUtil.success(stockRecordService.deleteById(id));
    }

    /**
     * Excel导入
     */
    @PostMapping("/import")
    public ResultVO importExcel(@RequestParam("file") MultipartFile file) {
        Integer count = stockRecordService.importExcel(file);
        return ResultVOUtil.success(count);
    }

    /**
     * Excel导出（QueryEntity封装查询条件）
     */
    @PostMapping("/export")
    public void export(@RequestBody StockRecord query, HttpServletResponse response) throws IOException {
        stockRecordService.exportExcel(query, response);
    }


    /**
     * 物资入库（前端输入名称）
     */
    @PostMapping("/inByName")
    public ResultVO stockIn(@RequestBody StockInDTO dto) {
        return stockRecordService.stockInByName(dto);
    }

    /**
     * 物资出库（前端输入名称）
     */
    @PostMapping("/outByName")
    public ResultVO stockOut(@RequestBody StockOutDTO dto) {
        return stockRecordService.stockOutByName(dto);
    }

    /**
     * 查询全部出入库流水
     */
    @PostMapping("/record")
    public ResultVO recordList(RecordQueryDTO dto) {
        return stockRecordService.queryRecord(dto);
    }
}
