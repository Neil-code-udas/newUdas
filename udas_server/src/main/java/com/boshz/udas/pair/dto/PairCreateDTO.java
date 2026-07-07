package com.boshz.udas.pair.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class PairCreateDTO {
    /** 师父工号 */
    private String teacherAccount;
    /** 徒弟工号 */
    private String studentAccount;
    /** 所属机构ID */
    private String orgId;
    /** 结对生效日期 */
    private LocalDate startDate;
    /** 承诺书文件地址 */
    private String signFileUrl;
    /** 创建人工号 */
    private String createAccount;
}