package com.boshz.udas.report.entity;

import lombok.Data;
import java.util.Date;

/**
 * 报表字段配置实体
 * 对应表：tb_report_column
 * 纯原生MyBatis，不使用MyBatis-Plus
 */
@Data
public class ReportColumn {

    /** 主键ID */
    private Long id;

    /** 报表编码 */
    private String reportCode;

    /** 字段名 */
    private String columnName;

    /** 字段中文名 */
    private String columnLabel;

    /** 字段类型 */
    private String columnType;

    /** 排序 */
    private Integer sort;

    /** 是否列表显示 0否 1是 */
    private Integer isList;

    /** 是否查询条件 0否 1是 */
    private Integer isQuery;

    /** 是否可排序 0否 1是 */
    private Integer isSortable;

    /** 宽度 */
    private Integer width;

    /** 对齐方式 left/center/right */
    private String align;

    /** 是否可导入 0否 1是 */
    private Integer isImport;

    /** 是否可导出 0否 1是 */
    private Integer isExport;

    /** 是否必填 0否 1是 */
    private Integer isRequired;

    /** 是否脱敏 0否 1是 */
    private Integer isDesensitize;

    /** 脱敏类型 */
    private String desensitizeType;

    /** 是否下拉框 0否 1是 */
    private Integer isSelect;

    /** 下拉选项 */
    private String selectOptions;

    /** 创建人 */
    private String createBy;

    /** 创建时间 */
    private Date createTime;

    /** 更新人 */
    private String updateBy;

    /** 更新时间 */
    private Date updateTime;

    /** 删除标志 0正常 1删除 */
    private Integer delFlag;
}