package com.boshz.udas.material.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.boshz.udas.material.dto.RecordQueryDTO;
import com.boshz.udas.material.dto.StockInDTO;
import com.boshz.udas.material.dto.StockOutDTO;
import com.boshz.udas.material.entity.Material;
import com.boshz.udas.material.entity.StockRecord;
import com.boshz.udas.material.excel.StockRecordExcel;
import com.boshz.udas.material.mapper.StockRecordMapper;
import com.boshz.udas.material.service.MaterialService;
import com.boshz.udas.material.service.StockRecordService;
import com.boshz.udas.vo.PageVo;
import com.boshz.udas.vo.QueryEntity;
import com.boshz.udas.vo.ResultVO;
import com.boshz.udas.vo.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class StockRecordServiceImpl implements StockRecordService {

    @Resource
    private StockRecordMapper stockRecordMapper;
    @Resource
    private MaterialService materialService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insert(StockRecord record) {
        return stockRecordMapper.insert(record) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(Long id) {
        if (stockRecordMapper.selectById(id) == null) {
            throw new RuntimeException("数据不存在");
        }
        return stockRecordMapper.deleteById(id) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(StockRecord record) {
        if (stockRecordMapper.selectById(record.getId()) == null) {
            throw new RuntimeException("数据不存在");
        }
        return stockRecordMapper.update(record) > 0;
    }

    @Override
    public StockRecord getById(Long id) {
        return stockRecordMapper.selectById(id);
    }

    @Override
    public PageVo<List<StockRecord>> getListByPage(QueryEntity<StockRecord> queryEntity) {
        StockRecord query = queryEntity.getQuery();
        Integer current = queryEntity.getCurrent();
        Integer pageSize = queryEntity.getPageSize();
        int offset = (current - 1) * pageSize;

        List<StockRecord> data = stockRecordMapper.queryAllByLimit(query, offset, pageSize);
        Long total = stockRecordMapper.selectCount(query);

        PageVo<List<StockRecord>> pageVo = new PageVo<>();
        pageVo.setData(data);
        pageVo.setPage(current);
        pageVo.setSize(pageSize);
        pageVo.setTotal(total.intValue());
        return pageVo;
    }

    @Override
    public List<StockRecord> getByCondition(StockRecord param) {
        return stockRecordMapper.selectByCondition(param);
    }

    @Override
    public void exportExcel(StockRecord query, HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("出入库台账流水.xlsx", "UTF-8");
        response.setHeader("content-disposition", "attachment;filename=" + new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
        OutputStream out = response.getOutputStream();
        List<StockRecord> list = stockRecordMapper.queryAllByLimit(query, null, null);
        log.info("导出条数:{}", list.size());
        EasyExcel.write(out, StockRecordExcel.class).sheet("出入库台账流水").doWrite(list);
        out.flush();
        out.close();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer importExcel(MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();
            Assert.notNull(fileName, "导入文件不能为空");
            Assert.isTrue(fileName.endsWith(".xlsx"), "仅支持.xlsx格式文件");

            List<StockRecord> saveList = new ArrayList<>();
            EasyExcel.read(file.getInputStream(), StockRecordExcel.class, new ReadListener<StockRecordExcel>() {
                        @Override
                        public void invoke(StockRecordExcel dto, AnalysisContext context) {
                            StockRecord entity = BeanUtil.copyProperties(dto, StockRecord.class);
                            saveList.add(entity);
                        }

                        @Override
                        public void doAfterAllAnalysed(AnalysisContext context) {
                        }
                    })
                    .sheet("出入库台账流水")
                    .headRowNumber(1)
                    .doRead();

            if (saveList.isEmpty()) {
                return 0;
            }
            return stockRecordMapper.insertBatch(saveList);
        } catch (IOException e) {
            log.error("出入库台账流水导入异常", e);
            throw new RuntimeException("Excel导入失败：" + e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO stockInByName(StockInDTO dto) {
        String materialName = dto.getMaterialName();
        BigDecimal num = dto.getNum();
        String remark = dto.getRemark();

        if (num.compareTo(BigDecimal.ZERO) <= 0) {
            return ResultVOUtil.error(9000,"入库数量必须大于0");
        }
        Material material = materialService.getByName(materialName);
        if (material == null) {
            return ResultVOUtil.error(9000,"物资【" + materialName + "】不存在，请先新增物资档案");
        }
        BigDecimal beforeStock = material.getStock();
        BigDecimal afterStock = beforeStock.add(num);
        // 更新库存
        materialService.updateStock(material.getId(), afterStock);
        // 生成入库流水
        StockRecord record = new StockRecord();
        record.setMaterialId(material.getId());
        record.setChangeType("IN");
        record.setChangeNum(num);
        record.setBeforeStock(beforeStock);
        record.setAfterStock(afterStock);
        record.setRemark(remark);
        record.setCreateTime(LocalDateTime.now());
        stockRecordMapper.insert(record);
        return ResultVOUtil.success("入库完成，台账已记录");    }

    @Override
    @Transactional(rollbackFor = Exception.class)

    public ResultVO stockOutByName(StockOutDTO dto) {
        String materialName = dto.getMaterialName();
        BigDecimal num = dto.getNum();
        String usePerson = dto.getUsePerson();
        String remark = dto.getRemark();

        if (num.compareTo(BigDecimal.ZERO) <= 0) {
            return ResultVOUtil.error(9000,"出库数量必须大于0");
        }
        Material material = materialService.getByName(materialName);
        if (material == null) {
            return ResultVOUtil.error(9000,"物资【" + materialName + "】不存在，请先新增物资档案");
        }
        BigDecimal beforeStock = material.getStock();
        if (beforeStock.compareTo(num) < 0) {
            return ResultVOUtil.error(9000,"库存不足，当前库存：" + beforeStock);
        }
        BigDecimal afterStock = beforeStock.subtract(num);
        materialService.updateStock(material.getId(), afterStock);
        // 生成出库流水
        StockRecord record = new StockRecord();
        record.setMaterialId(material.getId());
        record.setChangeType("OUT");
        record.setChangeNum(num);
        record.setBeforeStock(beforeStock);
        record.setAfterStock(afterStock);
        record.setUsePerson(usePerson);
        record.setRemark(remark);
        record.setCreateTime(LocalDateTime.now());
        stockRecordMapper.insert(record);
        return ResultVOUtil.success("出库登记完成，台账已记录");
    }


    /**
     * 查询出入库流水台账
     */
    @Override
    public ResultVO<List<StockRecord>> queryRecord(RecordQueryDTO dto) {
        List<StockRecord> list = stockRecordMapper.selectRecordList(dto);
        return ResultVOUtil.success(list);
    }
}
