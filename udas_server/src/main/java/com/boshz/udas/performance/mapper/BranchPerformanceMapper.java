package com.boshz.udas.performance.mapper;

import com.boshz.udas.performance.entity.BranchPerformance;
import org.apache.ibatis.annotations.Param;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface BranchPerformanceMapper {

    BranchPerformance selectById(Long id);

    int deleteById(Long id);

    int insert(BranchPerformance record);

    int update(BranchPerformance record);

    List<BranchPerformance> selectByCondition(@Param("query") BranchPerformance param);

    /**
     * 分页查询
     */
    List<BranchPerformance> queryAllByLimit(@Param("query") BranchPerformance query,
                                            @Param("offset") Integer offset,
                                            @Param("pageSize") Integer pageSize);

    Long selectCount(@Param("query") BranchPerformance query);

    /**
     * 批量新增
     */
    int insertBatch(@Param("entities") List<BranchPerformance> entities);

    /**
     * 批量插入/更新：同周期同经营单位数据重复则更新
     */
    int insertOrUpdateBatch(@Param("entities") List<BranchPerformance> list);
}
