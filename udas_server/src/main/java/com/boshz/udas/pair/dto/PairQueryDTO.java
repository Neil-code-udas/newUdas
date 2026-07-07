package com.boshz.udas.pair.dto;

import lombok.Data;

@Data
public class PairQueryDTO {
    /** 机构ID筛选 */
    private String orgId;
    /** 岗位类型筛选 */
    private String jobType;
    /** 姓名模糊搜索关键词 */
    private String keyword;
}