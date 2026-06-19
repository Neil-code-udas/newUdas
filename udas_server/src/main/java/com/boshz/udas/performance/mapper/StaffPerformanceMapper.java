package com.boshz.udas.performance.mapper;

import com.boshz.udas.performance.dto.BatchStaffPerfUpdateDTO;
import com.boshz.udas.performance.entity.StaffPerformance;
import org.apache.ibatis.annotations.Param;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface StaffPerformanceMapper {

    StaffPerformance selectById(Long id);

    int deleteById(Long id);

    int insert(StaffPerformance record);

    int update(StaffPerformance record);

    List<StaffPerformance> selectByCondition(@Param("query") StaffPerformance param);

    /**
     * 分页查询
     */
    List<StaffPerformance> queryAllByLimit(@Param("query") StaffPerformance query,
                                           @Param("offset") Integer offset,
                                           @Param("pageSize") Integer pageSize);

    Long selectCount(@Param("query") StaffPerformance query);

    /**
     * 批量新增
     */
    int insertBatch(@Param("entities") List<StaffPerformance> entities);

    /**
     * 批量动态更新员工指标字段
     * @param period 统计周期
     * @param updateList 更新明细列表
     */
//    int batchUpdatePerfField(
//            @Param("period") String period,
//            @Param("list") List<BatchStaffPerfUpdateDTO> updateList
//    );
//
//    /** 根据周期+工号查询单条，用于更新后重算汇总 */
//    StaffPerformance selectByPeriodStaffNo(
//            @Param("period") String period,
//            @Param("staffNo") String staffNo
//    );

    int updatePerfField(
            @Param("period") String period,
            @Param("branchCode") String branchCode,
            @Param("item") BatchStaffPerfUpdateDTO dto
    );

    StaffPerformance selectByPeriodStaffNo(
            @Param("period") String period,
            @Param("branchCode") String branchCode,
            @Param("staffNo") String staffNo
    );
}
