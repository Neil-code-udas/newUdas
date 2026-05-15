package com.boshz.udas.outerSubmit.mapper;

import com.boshz.udas.report.entity.ReportApply;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface ReportApplyMapper {

    // 分页查询
    @Select("<script>" +
            "SELECT * FROM tb_report_apply " +
            "WHERE 1=1 " +
            "<if test='searchCondition.applyDept != null and searchCondition.applyDept != \"\"'>" +
            "AND apply_dept LIKE CONCAT('%', #{searchCondition.applyDept}, '%') " +
            "</if>" +
            "<if test='searchCondition.operator != null and searchCondition.operator != \"\"'>" +
            "AND operator LIKE CONCAT('%', #{searchCondition.operator}, '%') " +
            "</if>" +
            "LIMIT #{offset}, #{size}" +
            "</script>")
    List<ReportApply> selectPage(@Param("offset") long offset,
                                @Param("size") int size,
                                @Param("searchCondition") Map<String, Object> searchCondition);

    // 统计总数
    @Select("<script>" +
            "SELECT COUNT(*) FROM tb_report_apply " +
            "WHERE 1=1 " +
            "<if test='searchCondition.applyDept != null and searchCondition.applyDept != \"\"'>" +
            "AND apply_dept LIKE CONCAT('%', #{searchCondition.applyDept}, '%') " +
            "</if>" +
            "<if test='searchCondition.operator != null and searchCondition.operator != \"\"'>" +
            "AND operator LIKE CONCAT('%', #{searchCondition.operator}, '%') " +
            "</if>" +
            "</script>")
    long selectCount(@Param("searchCondition") Map<String, Object> searchCondition);

    // 单条查询
    @Select("SELECT * FROM tb_report_apply WHERE id = #{id}")
    ReportApply selectById(@Param("id") Long id);

    // 新增
    @Insert("<script>" +
            "INSERT INTO tb_report_apply (" +
            "apply_dept, apply_dept_code, operator, operator_code, contact_phone, " +
            "recipient_info, email_subject, time_requirement, material_name, dept_manager, " +
            "dept_manager_code, vice_president, vice_president_code, related_file_ids, " +
            "create_time, update_time" +
            ") VALUES (" +
            "#{applyDept}, #{applyDeptCode}, #{operator}, #{operatorCode}, #{contactPhone}, " +
            "#{recipientInfo}, #{emailSubject}, #{timeRequirement}, #{materialName}, #{deptManager}, " +
            "#{deptManagerCode}, #{vicePresident}, #{vicePresidentCode}, #{relatedFileIds}, " +
            "NOW(), NOW()" +
            ")" +
            "</script>")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(ReportApply reportApply);

    // 修改
    @Update("<script>" +
            "UPDATE tb_report_apply SET " +
            "apply_dept = #{applyDept}, " +
            "apply_dept_code = #{applyDeptCode}, " +
            "operator = #{operator}, " +
            "operator_code = #{operatorCode}, " +
            "contact_phone = #{contactPhone}, " +
            "recipient_info = #{recipientInfo}, " +
            "email_subject = #{emailSubject}, " +
            "time_requirement = #{timeRequirement}, " +
            "material_name = #{materialName}, " +
            "dept_manager = #{deptManager}, " +
            "dept_manager_code = #{deptManagerCode}, " +
            "vice_president = #{vicePresident}, " +
            "vice_president_code = #{vicePresidentCode}, " +
            "related_file_ids = #{relatedFileIds}, " +
            "update_time = NOW() " +
            "WHERE id = #{id}" +
            "</script>")
    int update(ReportApply reportApply);

    // 删除
    @Delete("DELETE FROM tb_report_apply WHERE id = #{id}")
    int deleteById(@Param("id") Long id);

    // 建表语句（初始化用）
    @Update("CREATE TABLE IF NOT EXISTS tb_report_apply (" +
            "id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键'," +
            "apply_dept VARCHAR(255) COMMENT '申请部门'," +
            "apply_dept_code VARCHAR(50) COMMENT '申请部门编码'," +
            "operator VARCHAR(100) COMMENT '经办人员'," +
            "operator_code VARCHAR(50) COMMENT '经办人员编码'," +
            "contact_phone VARCHAR(20) COMMENT '联系电话'," +
            "recipient_info VARCHAR(500) COMMENT '收件人信息'," +
            "email_subject VARCHAR(500) COMMENT '报送邮件主题'," +
            "time_requirement VARCHAR(200) COMMENT '报送时效要求'," +
            "material_name VARCHAR(500) COMMENT '报送材料名称'," +
            "dept_manager VARCHAR(100) COMMENT '部门负责人'," +
            "dept_manager_code VARCHAR(50) COMMENT '部门负责人编码'," +
            "vice_president VARCHAR(100) COMMENT '分管行长'," +
            "vice_president_code VARCHAR(50) COMMENT '分管行长编码'," +
            "related_file_ids VARCHAR(1000) COMMENT '关联文件ids'," +
            "create_time DATETIME COMMENT '创建时间'," +
            "update_time DATETIME COMMENT '更新时间'" +
            ") ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT='申请报表信息表'")
    void createTable();
}