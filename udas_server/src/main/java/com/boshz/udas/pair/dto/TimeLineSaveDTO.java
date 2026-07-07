package com.boshz.udas.pair.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class TimeLineSaveDTO {
    /** 关联结对TP编号 */
    private String pairId;
    /** 操作人工号 */
    private String operateAccount;
    /** 操作人姓名 */
    private String operateUserName;
    /** 记录类型 SIGN签约 / STUDENT_SUBMIT徒弟提交 / TEACHER_REVIEW师父审阅 / EXAM_RECORD考核 */
    private String operateType;
    /** 文本内容描述 */
    private String content;
    /** 得分（审阅、考核场景有效） */
    private BigDecimal score;
    /** 附件文件列表 */
    private List<FileDTO> fileUrls;
}