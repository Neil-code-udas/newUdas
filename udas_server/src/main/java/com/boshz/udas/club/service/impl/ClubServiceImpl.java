package com.boshz.udas.club.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.boshz.udas.club.entity.Club;
import com.boshz.udas.club.excel.ClubExcel;
import com.boshz.udas.club.mapper.ClubMapper;
import com.boshz.udas.club.service.ClubService;
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
public class ClubServiceImpl implements ClubService {

    @Resource
    private ClubMapper clubMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insert(Club record) {
        return clubMapper.insert(record) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(Long id) {
        if (clubMapper.selectById(id) == null) {
            throw new RuntimeException("数据不存在");
        }
        return clubMapper.deleteById(id) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(Club record) {
        if (clubMapper.selectById(record.getId()) == null) {
            throw new RuntimeException("数据不存在");
        }
        return clubMapper.update(record) > 0;
    }

    @Override
    public Club getById(Long id) {
        return clubMapper.selectById(id);
    }

    @Override
    public PageVo<List<Club>> getListByPage(QueryEntity<Club> queryEntity) {
        Club query = queryEntity.getQuery();
        Integer current = queryEntity.getCurrent();
        Integer pageSize = queryEntity.getPageSize();
        int offset = (current - 1) * pageSize;

        List<Club> data = clubMapper.queryAllByLimit(query, offset, pageSize);
        Long total = clubMapper.selectCount(query);

        PageVo<List<Club>> pageVo = new PageVo<>();
        pageVo.setData(data);
        pageVo.setPage(current);
        pageVo.setSize(pageSize);
        pageVo.setTotal(total.intValue());
        return pageVo;
    }

    @Override
    public List<Club> getByCondition(Club param) {
        return clubMapper.selectByCondition(param);
    }

    @Override
    public void exportExcel(Club query, HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("俱乐部收支台账.xlsx", "UTF-8");
        response.setHeader("content-disposition", "attachment;filename=" + new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
        OutputStream out = response.getOutputStream();
        List<Club> list = clubMapper.queryAllByLimit(query, null, null);
        log.info("导出条数:{}", list.size());
        EasyExcel.write(out, ClubExcel.class).sheet("俱乐部收支台账").doWrite(list);
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

            List<Club> saveList = new ArrayList<>();
            EasyExcel.read(file.getInputStream(), ClubExcel.class, new ReadListener<ClubExcel>() {
                        @Override
                        public void invoke(ClubExcel dto, AnalysisContext context) {
                            Club entity = BeanUtil.copyProperties(dto, Club.class);
                            saveList.add(entity);
                        }

                        @Override
                        public void doAfterAllAnalysed(AnalysisContext context) {
                        }
                    })
                    .sheet("俱乐部收支台账")
                    .headRowNumber(1)
                    .doRead();

            if (saveList.isEmpty()) {
                return 0;
            }
            return clubMapper.insertBatch(saveList);
        } catch (IOException e) {
            log.error("俱乐部收支台账导入异常", e);
            throw new RuntimeException("Excel导入失败：" + e.getMessage());
        }
    }
}
