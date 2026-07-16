package com.boshz.udas.material.mapper;

import com.boshz.udas.material.dto.RecordQueryDTO;
import com.boshz.udas.material.entity.StockRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface StockRecordMapper {

    StockRecord selectById(Long id);

    int deleteById(Long id);

    int insert(StockRecord record);

    int update(StockRecord record);

    List<StockRecord> selectByCondition(@Param("query") StockRecord param);

    /**
     * 分页查询
     */
    List<StockRecord> queryAllByLimit(@Param("query") StockRecord query,
                                      @Param("offset") Integer offset,
                                      @Param("pageSize") Integer pageSize);

    Long selectCount(@Param("query") StockRecord query);

    /**
     * 批量新增
     */
    int insertBatch(@Param("entities") List<StockRecord> entities);

    List<StockRecord> selectRecordList(@Param("dto") RecordQueryDTO dto);
}
