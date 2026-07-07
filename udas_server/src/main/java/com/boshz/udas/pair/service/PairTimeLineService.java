package com.boshz.udas.pair.service;

import com.boshz.udas.pair.dto.TimeLineSaveDTO;
import com.boshz.udas.pair.entity.PairTimeLine;
import com.boshz.udas.pair.excel.PairTimeLineExcel;
import com.boshz.udas.vo.PageVo;
import com.boshz.udas.vo.QueryEntity;
import com.boshz.udas.vo.ResultVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 师徒时间轴记录表 业务接口
 */
public interface PairTimeLineService {

    boolean insert(PairTimeLine record);

    boolean deleteById(Long id);

    boolean update(PairTimeLine record);

    PairTimeLine getById(Long id);

    /**
     * 分页查询：QueryEntity封装条件+pageNum+pageSize
     */
    PageVo getListByPage(QueryEntity<PairTimeLine> queryEntity);

    List<PairTimeLine> getByCondition(PairTimeLine param);

    void exportExcel(PairTimeLine query, HttpServletResponse response) throws IOException;

    Integer importExcel(MultipartFile file);

    ResultVO saveTimeLine(TimeLineSaveDTO dto);
    ResultVO getTimeLineList(String pairId);
}
