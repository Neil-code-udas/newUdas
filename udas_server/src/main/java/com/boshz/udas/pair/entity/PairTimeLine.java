package com.boshz.udas.pair.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

/**
 * 师徒时间轴记录表(PairTimeLine)实体类
 *
 * @author makejava
 * @since 2026-07-03 16:17:32
 */
@Data
public class PairTimeLine implements Serializable {
    private static final long serialVersionUID = 662245541267879075L;
    /**
     * 自增主键
     */
    private Long id;
    /**
     * 关联结对TP单据编号
     */
    private String pairId;
    /**
     * 操作人工号
     */
    private String operateAccount;
    /**
     * 操作人姓名
     */
    private String operateUserName;
    /**
     * 记录类型 SIGN签约 STUDENT_SUBMIT徒弟提交 TEACHER_REVIEW师父审阅 EXAM_RECORD考核
     */
    private String operateType;
    /**
     * 文本内容
     */
    private String content;
    /**
     * 分数，审阅/考核使用
     */
    private BigDecimal score;
    /**
     * 附件json数组 [{fileName,fileUrl}]
     */
    private String fileUrls;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime operateTime;

}
