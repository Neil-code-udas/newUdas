package com.boshz.udas.pair.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 师徒时间轴记录表 Excel导入导出实体
 *
 * @author makejava
 * @since 2026-07-03 16:17:33
 */
@Data
public class PairTimeLineExcel {
    /**
     * 自增主键
     */
    @ExcelProperty(value = "自增主键", index = 0)
    private Long id;
    /**
     * 关联结对TP单据编号
     */
    @ExcelProperty(value = "关联结对TP单据编号", index = 1)
    private String pairId;
    /**
     * 操作人工号
     */
    @ExcelProperty(value = "操作人工号", index = 2)
    private String operateAccount;
    /**
     * 操作人姓名
     */
    @ExcelProperty(value = "操作人姓名", index = 3)
    private String operateUserName;
    /**
     * 记录类型 SIGN签约 STUDENT_SUBMIT徒弟提交 TEACHER_REVIEW师父审阅 EXAM_RECORD考核
     */
    @ExcelProperty(value = "记录类型 SIGN签约 STUDENT_SUBMIT徒弟提交 TEACHER_REVIEW师父审阅 EXAM_RECORD考核", index = 4)
    private String operateType;
    /**
     * 文本内容
     */
    @ExcelProperty(value = "文本内容", index = 5)
    private String content;
    /**
     * 分数，审阅/考核使用
     */
    @ExcelProperty(value = "分数，审阅/考核使用", index = 6)
    private BigDecimal score;
    /**
     * 附件json数组 [{fileName,fileUrl}]
     */
    @ExcelProperty(value = "附件json数组 [{fileName,fileUrl}]", index = 7)
    private String fileUrls;
    /**
     *
     */
    @ExcelProperty(value = "", index = 8)
    private LocalDateTime operateTime;
}
