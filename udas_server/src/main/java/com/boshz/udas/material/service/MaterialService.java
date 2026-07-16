package com.boshz.udas.material.service;

import com.boshz.udas.material.entity.Material;
import com.boshz.udas.material.excel.MaterialExcel;
import com.boshz.udas.vo.PageVo;
import com.boshz.udas.vo.QueryEntity;
import com.boshz.udas.vo.ResultVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 * 办公用品基础档案 业务接口
 */
public interface MaterialService {

    ResultVO insert(Material record);

    boolean deleteById(Long id);

    ResultVO update(Material record);

    Material getById(Long id);

    /**
     * 分页查询：QueryEntity封装条件+pageNum+pageSize
     */
    PageVo getListByPage(QueryEntity<Material> queryEntity);

    List<Material> getByCondition(Material param);

    void exportExcel(Material query, HttpServletResponse response) throws IOException;

    Integer importExcel(MultipartFile file);


    void updateStock(Long id, BigDecimal afterStock);

    Material getByName(String materialName);

    ResultVO list(String materialName);
}
