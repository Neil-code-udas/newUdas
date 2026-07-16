package com.boshz.udas.material.service;

import com.boshz.udas.material.dto.RecordQueryDTO;
import com.boshz.udas.material.dto.StockInDTO;
import com.boshz.udas.material.dto.StockOutDTO;
import com.boshz.udas.material.entity.StockRecord;
import com.boshz.udas.material.excel.StockRecordExcel;
import com.boshz.udas.vo.PageVo;
import com.boshz.udas.vo.QueryEntity;
import com.boshz.udas.vo.ResultVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 出入库台账流水 业务接口
 */
public interface StockRecordService {

    boolean insert(StockRecord record);

    boolean deleteById(Long id);

    boolean update(StockRecord record);

    StockRecord getById(Long id);

    /**
     * 分页查询：QueryEntity封装条件+pageNum+pageSize
     */
    PageVo getListByPage(QueryEntity<StockRecord> queryEntity);

    List<StockRecord> getByCondition(StockRecord param);

    void exportExcel(StockRecord query, HttpServletResponse response) throws IOException;

    Integer importExcel(MultipartFile file);

    ResultVO stockInByName(StockInDTO dto);

    ResultVO stockOutByName(StockOutDTO dto);

    ResultVO queryRecord(RecordQueryDTO dto);
}
