package com.boshz.udas.mealTicket.service;

import com.boshz.udas.mealTicket.entity.MealTicket;
import com.boshz.udas.mealTicket.excel.MealTicketExcel;
import com.boshz.udas.vo.PageVo;
import com.boshz.udas.vo.QueryEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 餐券预约表 业务接口
 */
public interface MealTicketService {

    boolean insert(MealTicket record);

    boolean deleteById(Long id);

    boolean update(MealTicket record);

    MealTicket getById(Long id);

    /**
     * 分页查询：QueryEntity封装条件+pageNum+pageSize
     */
    PageVo getListByPage(QueryEntity<MealTicket> queryEntity);

    List<MealTicket> getByCondition(MealTicket param);

    void exportExcel(MealTicket query, HttpServletResponse response) throws IOException;

    Integer importExcel(MultipartFile file);
}
