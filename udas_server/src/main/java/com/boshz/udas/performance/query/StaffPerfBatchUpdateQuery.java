package com.boshz.udas.performance.query;

import java.math.BigDecimal;
import java.util.List;

import com.boshz.udas.performance.dto.BatchStaffPerfUpdateDTO;
import lombok.Data;

@Data
public class StaffPerfBatchUpdateQuery {
    /** 统计周期（必传） */
    private String period;
    /** 机构编码（必传，数据权限过滤） */
    private String branchCode;
    private BigDecimal totalAmount;
    /** 批量更新明细列表 */
    private List<BatchStaffPerfUpdateDTO> updateList;
}