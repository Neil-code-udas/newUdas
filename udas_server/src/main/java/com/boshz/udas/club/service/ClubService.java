package com.boshz.udas.club.service;

import com.boshz.udas.club.entity.Club;
import com.boshz.udas.club.excel.ClubExcel;
import com.boshz.udas.vo.PageVo;
import com.boshz.udas.vo.QueryEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 俱乐部收支台账 业务接口
 */
public interface ClubService {

    boolean insert(Club record);

    boolean deleteById(Long id);

    boolean update(Club record);

    Club getById(Long id);

    /**
     * 分页查询：QueryEntity封装条件+pageNum+pageSize
     */
    PageVo getListByPage(QueryEntity<Club> queryEntity);

    List<Club> getByCondition(Club param);

    void exportExcel(Club query, HttpServletResponse response) throws IOException;

    Integer importExcel(MultipartFile file);
}
