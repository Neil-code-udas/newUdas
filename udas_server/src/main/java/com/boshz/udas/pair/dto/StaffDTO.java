package com.boshz.udas.pair.dto;

import lombok.Data;

@Data
public class StaffDTO {
    /** 员工工号 */
    private String account;
    /** 员工姓名 */
    private String name;
    /** 职等 */
    private Integer level;
    /** 在岗年限（师父使用） */
    private Integer workYear;
    /** 入职月数（徒弟使用） */
    private Integer entryMonth;
    /** 是否部门负责人 */
    private Boolean isManager;
    /** 岗位类型 NORMAL普通岗位 / CUSTOMER客户经理旧方案 */
    private String jobType;
}