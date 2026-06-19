package com.boshz.udas.performance.constant;

import java.util.HashMap;
import java.util.Map;

public class PerformanceConstant {
    // 指标-表字段映射：key=Excel行索引，value=表精简英文字段名
    public static final Map<Integer, String> TARGET_FIELD_MAP = new HashMap<>();
    // 写死的机构映射：key=Excel列索引，value=机构信息（编码、名称、列索引）
    public static final Map<Integer, BranchColumnInfo> BRANCH_COLUMN_MAP = new HashMap<>();

    static {
        // 1. 指标-精简字段映射（rowIndex从2开始，对应Excel第3行）
        TARGET_FIELD_MAP.put(2, "promotion_1");
        TARGET_FIELD_MAP.put(3, "promotion_2");
        TARGET_FIELD_MAP.put(4, "account_1");
        TARGET_FIELD_MAP.put(5, "account_2");
        TARGET_FIELD_MAP.put(6, "check_1");
        TARGET_FIELD_MAP.put(7, "check_2");
        TARGET_FIELD_MAP.put(8, "bill_1");
        TARGET_FIELD_MAP.put(9, "bill_2");
        TARGET_FIELD_MAP.put(10, "cash_1");
        TARGET_FIELD_MAP.put(11, "cash_2");
        TARGET_FIELD_MAP.put(12, "staff_count");
        TARGET_FIELD_MAP.put(13, "score");

        // 2. 写死的机构映射（完全匹配你的Excel列，D列开始colIndex=3）
        BRANCH_COLUMN_MAP.put(3, new BranchColumnInfo(3, "001", "分行营业部"));
        BRANCH_COLUMN_MAP.put(4, new BranchColumnInfo(4, "002", "萧山支行"));
        BRANCH_COLUMN_MAP.put(5, new BranchColumnInfo(5, "003", "滨江支行"));
        BRANCH_COLUMN_MAP.put(6, new BranchColumnInfo(6, "004", "临平支行"));
        BRANCH_COLUMN_MAP.put(7, new BranchColumnInfo(7, "005", "拱墅支行"));
        BRANCH_COLUMN_MAP.put(8, new BranchColumnInfo(8, "006", "桐庐支行"));
        BRANCH_COLUMN_MAP.put(9, new BranchColumnInfo(9, "007", "武林支行"));
        BRANCH_COLUMN_MAP.put(10, new BranchColumnInfo(10, "008", "临安支行"));
        BRANCH_COLUMN_MAP.put(11, new BranchColumnInfo(11, "009", "钱塘支行"));
        BRANCH_COLUMN_MAP.put(12, new BranchColumnInfo(12, "010", "庆春支行"));
        BRANCH_COLUMN_MAP.put(13, new BranchColumnInfo(13, "011", "余杭支行"));
        BRANCH_COLUMN_MAP.put(14, new BranchColumnInfo(14, "012", "富阳支行"));
        BRANCH_COLUMN_MAP.put(15, new BranchColumnInfo(15, "013", "滨江小微"));
        BRANCH_COLUMN_MAP.put(16, new BranchColumnInfo(16, "014", "瓜沥支行"));
        BRANCH_COLUMN_MAP.put(17, new BranchColumnInfo(17, "015", "乔司小微"));
    }

    // 机构列信息内部类
    @lombok.Data
    public static class BranchColumnInfo {
        private Integer columnIndex;
        private String branchCode;
        private String branchName;

        public BranchColumnInfo(Integer columnIndex, String branchCode, String branchName) {
            this.columnIndex = columnIndex;
            this.branchCode = branchCode;
            this.branchName = branchName;
        }
    }
}