package com.boshz.udas.mealTicket.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.boshz.udas.mealTicket.entity.MealTicket;
import com.boshz.udas.mealTicket.excel.MealTicketExcel;
import com.boshz.udas.mealTicket.mapper.MealTicketMapper;
import com.boshz.udas.mealTicket.service.MealTicketService;
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
public class MealTicketServiceImpl implements MealTicketService {

    @Resource
    private MealTicketMapper mealTicketMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insert(MealTicket record) {
        return mealTicketMapper.insert(record) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(Long id) {
        if (mealTicketMapper.selectById(id) == null) {
            throw new RuntimeException("数据不存在");
        }
        return mealTicketMapper.deleteById(id) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(MealTicket record) {
        if (mealTicketMapper.selectById(record.getId()) == null) {
            throw new RuntimeException("数据不存在");
        }
        return mealTicketMapper.update(record) > 0;
    }

    @Override
    public MealTicket getById(Long id) {
        return mealTicketMapper.selectById(id);
    }

    @Override
    public PageVo<List<MealTicket>> getListByPage(QueryEntity<MealTicket> queryEntity) {
        MealTicket query = queryEntity.getQuery();
        Integer current = queryEntity.getCurrent();
        Integer pageSize = queryEntity.getPageSize();
        int offset = (current - 1) * pageSize;

        List<MealTicket> data = mealTicketMapper.queryAllByLimit(query, offset, pageSize);
        Long total = mealTicketMapper.selectCount(query);

        PageVo<List<MealTicket>> pageVo = new PageVo<>();
        pageVo.setData(data);
        pageVo.setPage(current);
        pageVo.setSize(pageSize);
        pageVo.setTotal(total.intValue());
        return pageVo;
    }

    @Override
    public List<MealTicket> getByCondition(MealTicket param) {
        return mealTicketMapper.selectByCondition(param);
    }

    @Override
    public void exportExcel(MealTicket query, HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("餐券预约表.xlsx", "UTF-8");
        response.setHeader("content-disposition", "attachment;filename=" + new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
        OutputStream out = response.getOutputStream();
        List<MealTicket> list = mealTicketMapper.queryAllByLimit(query, null, null);
        log.info("导出条数:{}", list.size());
        EasyExcel.write(out, MealTicketExcel.class).sheet("餐券预约表").doWrite(list);
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

            List<MealTicket> saveList = new ArrayList<>();
            EasyExcel.read(file.getInputStream(), MealTicketExcel.class, new ReadListener<MealTicketExcel>() {
                        @Override
                        public void invoke(MealTicketExcel dto, AnalysisContext context) {
                            MealTicket entity = BeanUtil.copyProperties(dto, MealTicket.class);
                            saveList.add(entity);
                        }

                        @Override
                        public void doAfterAllAnalysed(AnalysisContext context) {
                        }
                    })
                    .sheet("餐券预约表")
                    .headRowNumber(1)
                    .doRead();

            if (saveList.isEmpty()) {
                return 0;
            }
            return mealTicketMapper.insertBatch(saveList);
        } catch (IOException e) {
            log.error("餐券预约表导入异常", e);
            throw new RuntimeException("Excel导入失败：" + e.getMessage());
        }
    }
}
