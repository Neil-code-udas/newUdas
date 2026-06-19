package com.boshz.udas.performance.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.boshz.udas.performance.constant.PerformanceConstant;
import com.boshz.udas.performance.entity.BranchPerformance;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PerformanceExcelListener extends AnalysisEventListener<Map<Integer, ?>> {
    private final Map<String, BranchPerformance> performanceMap = new HashMap<>();
    private final String period;
    private final Map<Integer, PerformanceConstant.BranchColumnInfo> branchColumnMap;

    // 双参数构造，匹配Service调用
    public PerformanceExcelListener(String period, Map<Integer, PerformanceConstant.BranchColumnInfo> branchColumnMap) {
        this.period = period;
        this.branchColumnMap = branchColumnMap;
    }

    @Override
    public void invoke(Map<Integer, ?> rowCellMap, AnalysisContext context) {
        Integer rowIndex = context.readRowHolder().getRowIndex();
        // 跳过前两行表头
        if (rowIndex < 2 || !PerformanceConstant.TARGET_FIELD_MAP.containsKey(rowIndex)) {
            return;
        }
        String fieldName = PerformanceConstant.TARGET_FIELD_MAP.get(rowIndex);
        String camelFieldName = underlineToCamel(fieldName);

        // 遍历所有支行列
        for (Map.Entry<Integer, PerformanceConstant.BranchColumnInfo> entry : branchColumnMap.entrySet()) {
            Integer colIdx = entry.getKey();
            PerformanceConstant.BranchColumnInfo branchInfo = entry.getValue();
            String branchCode = branchInfo.getBranchCode();

            Object cellObj = rowCellMap.get(colIdx);
            String dataVal = "";
            // 兼容ReadCellData/String两种类型，彻底解决ClassCastException
            if (cellObj instanceof ReadCellData) {
                ReadCellData<?> cellData = (ReadCellData<?>) cellObj;
                dataVal = cellData.getStringValue();
            } else if (cellObj != null) {
                dataVal = cellObj.toString();
            }
            dataVal = dataVal.trim();

            // 初始化支行绩效实体
            BranchPerformance performance = performanceMap.get(branchCode);
            if (performance == null) {
                performance = new BranchPerformance();
                performance.setPeriod(this.period);
                performance.setBranchCode(branchCode);
                performance.setBranchName(branchInfo.getBranchName());
                performanceMap.put(branchCode, performance);
            }

            // 反射赋值
            try {
                Field field = BranchPerformance.class.getDeclaredField(camelFieldName);
                field.setAccessible(true);
                BigDecimal decimalVal;
                try {
                    decimalVal = new BigDecimal(dataVal);
                } catch (NumberFormatException e) {
                    decimalVal = BigDecimal.ZERO;
                }
                field.set(performance, decimalVal);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        System.out.println("Excel读取完成，周期：" + this.period + "，共解析" + performanceMap.size() + "个经营单位");
    }

    public List<BranchPerformance> getSaveDataList() {
        return new ArrayList<>(performanceMap.values());
    }

    // 下划线转驼峰工具
    private String underlineToCamel(String underlineStr) {
        if (underlineStr == null || underlineStr.isEmpty()) {
            return underlineStr;
        }
        StringBuilder result = new StringBuilder();
        boolean nextUpperCase = false;
        for (char c : underlineStr.toCharArray()) {
            if (c == '_') {
                nextUpperCase = true;
            } else {
                if (nextUpperCase) {
                    result.append(Character.toUpperCase(c));
                    nextUpperCase = false;
                } else {
                    result.append(c);
                }
            }
        }
        return result.toString();
    }
}