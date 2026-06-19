package com.boshz.udas.department.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.boshz.udas.department.entity.Department;
import com.boshz.udas.department.excel.DepartmentExcel;
import com.boshz.udas.department.mapper.DepartmentMapper;
import com.boshz.udas.department.service.DepartmentService;
import com.boshz.udas.vo.PageVo;
import com.boshz.udas.vo.QueryEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Resource
    private DepartmentMapper departmentMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insert(Department record) {
        return departmentMapper.insert(record) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(Long id) {
        if (departmentMapper.selectById(id) == null) {
            throw new RuntimeException("数据不存在");
        }
        return departmentMapper.deleteById(id) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(Department record) {
        if (departmentMapper.selectById(record.getId()) == null) {
            throw new RuntimeException("数据不存在");
        }
        return departmentMapper.update(record) > 0;
    }

    @Override
    public Department getById(Long id) {
        return departmentMapper.selectById(id);
    }

    @Override
    public PageVo<List<Department>> getListByPage(QueryEntity<Department> queryEntity) {
        Department query = queryEntity.getQuery();
        Integer current = queryEntity.getCurrent();
        Integer pageSize = queryEntity.getPageSize();
        int offset = (current - 1) * pageSize;

        List<Department> data = departmentMapper.queryAllByLimit(query, offset, pageSize);
        Long total = departmentMapper.selectCount(query);

        PageVo<List<Department>> pageVo = new PageVo<>();
        pageVo.setData(data);
        pageVo.setPage(current);
        pageVo.setSize(pageSize);
        pageVo.setTotal(total.intValue());
        return pageVo;
    }

    @Override
    public List<Department> getByCondition(Department param) {
        return departmentMapper.selectByCondition(param);
    }

    @Override
    public void exportExcel(Department query, HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("机构部门表.xlsx", "UTF-8");
        response.setHeader("content-disposition", "attachment;filename=" + new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
        OutputStream out = response.getOutputStream();
        List<Department> list = departmentMapper.queryAllByLimit(query, null, null);
        log.info("导出条数:{}", list.size());
        EasyExcel.write(out, DepartmentExcel.class).sheet("机构部门表").doWrite(list);
        out.flush();
        out.close();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer importExcel(MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();
            Assert.notNull(fileName, "导入文件不能为空");
            Assert.isTrue(fileName.endsWith(".xlsx"), "仅支持.xlsx格式文件");

            List<Department> saveList = new ArrayList<>();
            EasyExcel.read(file.getInputStream(), DepartmentExcel.class, new ReadListener<DepartmentExcel>() {
                        @Override
                        public void invoke(DepartmentExcel dto, AnalysisContext context) {
                            Department entity = BeanUtil.copyProperties(dto, Department.class);
                            saveList.add(entity);
                        }

                        @Override
                        public void doAfterAllAnalysed(AnalysisContext context) {
                        }
                    })
                    .sheet("机构部门表")
                    .headRowNumber(1)
                    .doRead();

            if (saveList.isEmpty()) {
                return 0;
            }
            return departmentMapper.insertBatch(saveList);
        } catch (IOException e) {
            log.error("机构部门表导入异常", e);
            throw new RuntimeException("Excel导入失败：" + e.getMessage());
        }
    }
}
