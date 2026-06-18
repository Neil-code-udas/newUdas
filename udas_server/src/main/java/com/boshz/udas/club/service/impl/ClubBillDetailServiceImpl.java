package com.boshz.udas.club.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.boshz.udas.club.entity.ClubBillDetail;
import com.boshz.udas.club.excel.ClubBillDetailExcel;
import com.boshz.udas.club.mapper.ClubBillDetailMapper;
import com.boshz.udas.club.service.ClubBillDetailService;
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
public class ClubBillDetailServiceImpl implements ClubBillDetailService {

    @Resource
    private ClubBillDetailMapper clubBillDetailMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insert(ClubBillDetail record) {
        //401是会费收入
        if(record.getSubjectCode().startsWith("401")){
            //如果是会费收入，系统会自动录入一条经费收入 是会费的2倍
            ClubBillDetail clubBillDetail = BeanUtil.copyProperties(record, ClubBillDetail.class);
            clubBillDetail.setProjectId(111l);
            clubBillDetail.setProjectName("经费项目");
            clubBillDetail.setSubjectCode("");
            clubBillDetail.setSubjectName("");
            clubBillDetailMapper.insert(clubBillDetail);
        }
        return clubBillDetailMapper.insert(record) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(Long id) {
        if (clubBillDetailMapper.selectById(id) == null) {
            throw new RuntimeException("数据不存在");
        }
        return clubBillDetailMapper.deleteById(id) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(ClubBillDetail record) {
        if (clubBillDetailMapper.selectById(record.getId()) == null) {
            throw new RuntimeException("数据不存在");
        }
        return clubBillDetailMapper.update(record) > 0;
    }

    @Override
    public ClubBillDetail getById(Long id) {
        return clubBillDetailMapper.selectById(id);
    }

    @Override
    public PageVo<List<ClubBillDetail>> getListByPage(QueryEntity<ClubBillDetail> queryEntity) {
        ClubBillDetail query = queryEntity.getQuery();
        Integer current = queryEntity.getCurrent();
        Integer pageSize = queryEntity.getPageSize();
        int offset = (current - 1) * pageSize;

        List<ClubBillDetail> data = clubBillDetailMapper.queryAllByLimit(query, offset, pageSize);
        Long total = clubBillDetailMapper.selectCount(query);

        PageVo<List<ClubBillDetail>> pageVo = new PageVo<>();
        pageVo.setData(data);
        pageVo.setPage(current);
        pageVo.setSize(pageSize);
        pageVo.setTotal(total.intValue());
        return pageVo;
    }

    @Override
    public List<ClubBillDetail> getByCondition(ClubBillDetail param) {
        return clubBillDetailMapper.selectByCondition(param);
    }

    @Override
    public void exportExcel(ClubBillDetail query, HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("俱乐部收支明细表.xlsx", "UTF-8");
        response.setHeader("content-disposition", "attachment;filename=" + new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
        OutputStream out = response.getOutputStream();
        List<ClubBillDetail> list = clubBillDetailMapper.queryAllByLimit(query, null, null);
        log.info("导出条数:{}", list.size());
        EasyExcel.write(out, ClubBillDetailExcel.class).sheet("俱乐部收支明细表").doWrite(list);
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

            List<ClubBillDetail> saveList = new ArrayList<>();
            EasyExcel.read(file.getInputStream(), ClubBillDetailExcel.class, new ReadListener<ClubBillDetailExcel>() {
                        @Override
                        public void invoke(ClubBillDetailExcel dto, AnalysisContext context) {
                            ClubBillDetail entity = BeanUtil.copyProperties(dto, ClubBillDetail.class);
                            saveList.add(entity);
                        }

                        @Override
                        public void doAfterAllAnalysed(AnalysisContext context) {
                        }
                    })
                    .sheet("俱乐部收支明细表")
                    .headRowNumber(1)
                    .doRead();

            if (saveList.isEmpty()) {
                return 0;
            }
            return clubBillDetailMapper.insertBatch(saveList);
        } catch (IOException e) {
            log.error("俱乐部收支明细表导入异常", e);
            throw new RuntimeException("Excel导入失败：" + e.getMessage());
        }
    }
}
