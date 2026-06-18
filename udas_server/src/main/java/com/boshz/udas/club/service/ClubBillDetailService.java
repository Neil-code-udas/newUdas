package com.boshz.udas.club.service;

import com.boshz.udas.club.entity.ClubBillDetail;
import com.boshz.udas.club.excel.ClubBillDetailExcel;
import com.boshz.udas.vo.PageVo;
import com.boshz.udas.vo.QueryEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 俱乐部收支明细表 业务接口
 */
public interface ClubBillDetailService {

    boolean insert(ClubBillDetail record);

    boolean deleteById(Long id);

    boolean update(ClubBillDetail record);

    ClubBillDetail getById(Long id);

    /**
     * 分页查询：QueryEntity封装条件+pageNum+pageSize
     */
    PageVo getListByPage(QueryEntity<ClubBillDetail> queryEntity);

    List<ClubBillDetail> getByCondition(ClubBillDetail param);

    void exportExcel(ClubBillDetail query, HttpServletResponse response) throws IOException;

    Integer importExcel(MultipartFile file);
}
