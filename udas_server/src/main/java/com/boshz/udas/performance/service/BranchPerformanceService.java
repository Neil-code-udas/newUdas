package com.boshz.udas.performance.service;

import com.boshz.udas.performance.constant.PerformanceConstant;
import com.boshz.udas.performance.entity.BranchPerformance;
import com.boshz.udas.performance.excel.BranchPerformanceExcel;
import com.boshz.udas.performance.query.BranchPerformanceQuery;
import com.boshz.udas.performance.vo.BranchPerformanceVO;
import com.boshz.udas.vo.PageVo;
import com.boshz.udas.vo.QueryEntity;
import com.boshz.udas.vo.ResultVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * 支行季度绩效表 业务接口
 */
public interface BranchPerformanceService {

    boolean insert(BranchPerformance record);

    boolean deleteById(Long id);

    boolean update(BranchPerformance record);

    BranchPerformance getById(Long id);

    /**
     * 分页查询：QueryEntity封装条件+pageNum+pageSize
     */
    PageVo getListByPage(QueryEntity<BranchPerformance> queryEntity);

    List<BranchPerformance> getByCondition(BranchPerformance param);

    void exportExcel(BranchPerformance query, HttpServletResponse response) throws IOException;

    Integer importExcel(MultipartFile file);

    void importExcel(InputStream inputStream, String period);

    /** 根据周期+单个机构查询绩效 */
    ResultVO<BranchPerformanceVO> getSingleBranchPerformance(BranchPerformance query);



}
