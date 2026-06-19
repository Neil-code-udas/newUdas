package com.boshz.udas.performance.service;

import com.boshz.udas.performance.entity.StaffPerformance;
import com.boshz.udas.performance.excel.StaffPerformanceExcel;
import com.boshz.udas.performance.query.StaffPerfBatchUpdateQuery;
import com.boshz.udas.vo.PageVo;
import com.boshz.udas.vo.QueryEntity;
import com.boshz.udas.vo.ResultVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 员工绩效汇总表 业务接口
 */
public interface StaffPerformanceService {

    boolean insert(StaffPerformance record);

    boolean deleteById(Long id);

    boolean update(StaffPerformance record);

    StaffPerformance getById(Long id);

    /**
     * 分页查询：QueryEntity封装条件+pageNum+pageSize
     */
    PageVo getListByPage(QueryEntity<StaffPerformance> queryEntity);

    List<StaffPerformance> getByCondition(StaffPerformance param);

    void exportExcel(StaffPerformance query, HttpServletResponse response) throws IOException;

    Integer importExcel(MultipartFile file,String period);

    /**
     * 批量更新员工单项绩效指标
     */
    ResultVO<Boolean> batchUpdatePerf(StaffPerfBatchUpdateQuery query);
}
