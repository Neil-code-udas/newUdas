package com.boshz.udas.pair.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.boshz.udas.pair.entity.TeacherStudentPair;
import com.boshz.udas.pair.excel.TeacherStudentPairExcel;
import com.boshz.udas.pair.mapper.TeacherStudentPairMapper;
import com.boshz.udas.pair.service.TeacherStudentPairService;
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
public class TeacherStudentPairServiceImpl implements TeacherStudentPairService {

    @Resource
    private TeacherStudentPairMapper teacherStudentPairMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insert(TeacherStudentPair record) {
        return teacherStudentPairMapper.insert(record) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(String id) {
        if (teacherStudentPairMapper.selectById(id) == null) {
            throw new RuntimeException("数据不存在");
        }
        return teacherStudentPairMapper.deleteById(id) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(TeacherStudentPair record) {
        if (teacherStudentPairMapper.selectById(record.getId()) == null) {
            throw new RuntimeException("数据不存在");
        }
        return teacherStudentPairMapper.update(record) > 0;
    }

    @Override
    public TeacherStudentPair getById(String id) {
        return teacherStudentPairMapper.selectById(id);
    }

    @Override
    public PageVo<List<TeacherStudentPair>> getListByPage(QueryEntity<TeacherStudentPair> queryEntity) {
        TeacherStudentPair query = queryEntity.getQuery();
        Integer current = queryEntity.getCurrent();
        Integer pageSize = queryEntity.getPageSize();
        int offset = (current - 1) * pageSize;

        List<TeacherStudentPair> data = teacherStudentPairMapper.queryAllByLimit(query, offset, pageSize);
        Long total = teacherStudentPairMapper.selectCount(query);

        PageVo<List<TeacherStudentPair>> pageVo = new PageVo<>();
        pageVo.setData(data);
        pageVo.setPage(current);
        pageVo.setSize(pageSize);
        pageVo.setTotal(total.intValue());
        return pageVo;
    }

    @Override
    public List<TeacherStudentPair> getByCondition(TeacherStudentPair param) {
        return teacherStudentPairMapper.selectByCondition(param);
    }

    @Override
    public void exportExcel(TeacherStudentPair query, HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("师徒结对主表.xlsx", "UTF-8");
        response.setHeader("content-disposition", "attachment;filename=" + new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
        OutputStream out = response.getOutputStream();
        List<TeacherStudentPair> list = teacherStudentPairMapper.queryAllByLimit(query, null, null);
        log.info("导出条数:{}", list.size());
        EasyExcel.write(out, TeacherStudentPairExcel.class).sheet("师徒结对主表").doWrite(list);
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

            List<TeacherStudentPair> saveList = new ArrayList<>();
            EasyExcel.read(file.getInputStream(), TeacherStudentPairExcel.class, new ReadListener<TeacherStudentPairExcel>() {
                        @Override
                        public void invoke(TeacherStudentPairExcel dto, AnalysisContext context) {
                            TeacherStudentPair entity = BeanUtil.copyProperties(dto, TeacherStudentPair.class);
                            saveList.add(entity);
                        }

                        @Override
                        public void doAfterAllAnalysed(AnalysisContext context) {
                        }
                    })
                    .sheet("师徒结对主表")
                    .headRowNumber(1)
                    .doRead();

            if (saveList.isEmpty()) {
                return 0;
            }
            return teacherStudentPairMapper.insertBatch(saveList);
        } catch (IOException e) {
            log.error("师徒结对主表导入异常", e);
            throw new RuntimeException("Excel导入失败：" + e.getMessage());
        }
    }
}
