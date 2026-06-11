package com.boshz.udas.secretPersonnel.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.boshz.udas.secretPersonnel.entity.SecretPersonnel;
import com.boshz.udas.secretPersonnel.excel.SecretPersonnelExcel;
import com.boshz.udas.secretPersonnel.mapper.SecretPersonnelMapper;
import com.boshz.udas.secretPersonnel.service.SecretPersonnelService;
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
public class SecretPersonnelServiceImpl implements SecretPersonnelService {

    @Resource
    private SecretPersonnelMapper secretPersonnelMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insert(SecretPersonnel record) {
        return secretPersonnelMapper.insert(record) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(Long id) {
        if (secretPersonnelMapper.selectById(id) == null) {
            throw new RuntimeException("数据不存在");
        }
        return secretPersonnelMapper.deleteById(id) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(SecretPersonnel record) {
        if (secretPersonnelMapper.selectById(record.getId()) == null) {
            throw new RuntimeException("数据不存在");
        }
        return secretPersonnelMapper.update(record) > 0;
    }

    @Override
    public SecretPersonnel getById(Long id) {
        return secretPersonnelMapper.selectById(id);
    }

    @Override
    public PageVo<List<SecretPersonnel>> getListByPage(QueryEntity<SecretPersonnel> queryEntity) {
        SecretPersonnel query = queryEntity.getQuery();
        Integer current = queryEntity.getCurrent();
        Integer pageSize = queryEntity.getPageSize();
        int offset = (current - 1) * pageSize;

        List<SecretPersonnel> data = secretPersonnelMapper.queryAllByLimit(query, offset, pageSize);
        Long total = secretPersonnelMapper.selectCount(query);

        PageVo<List<SecretPersonnel>> pageVo = new PageVo<>();
        pageVo.setData(data);
        pageVo.setPage(current);
        pageVo.setSize(pageSize);
        pageVo.setTotal(total.intValue());
        return pageVo;
    }

    @Override
    public List<SecretPersonnel> getByCondition(SecretPersonnel param) {
        return secretPersonnelMapper.selectByCondition(param);
    }

    @Override
    public void exportExcel(SecretPersonnel query, HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("涉密人员管理表.xlsx", "UTF-8");
        response.setHeader("content-disposition", "attachment;filename=" + new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
        OutputStream out = response.getOutputStream();
        List<SecretPersonnel> list = secretPersonnelMapper.queryAllByLimit(query, null, null);
        log.info("导出条数:{}", list.size());
        EasyExcel.write(out, SecretPersonnelExcel.class).sheet("涉密人员管理表").doWrite(list);
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

            List<SecretPersonnel> saveList = new ArrayList<>();
            EasyExcel.read(file.getInputStream(), SecretPersonnelExcel.class, new ReadListener<SecretPersonnelExcel>() {
                        @Override
                        public void invoke(SecretPersonnelExcel dto, AnalysisContext context) {
                            SecretPersonnel entity = BeanUtil.copyProperties(dto, SecretPersonnel.class);
                            saveList.add(entity);
                        }

                        @Override
                        public void doAfterAllAnalysed(AnalysisContext context) {
                        }
                    })
                    .sheet("涉密人员管理表")
                    .headRowNumber(1)
                    .doRead();

            if (saveList.isEmpty()) {
                return 0;
            }
            return secretPersonnelMapper.insertBatch(saveList);
        } catch (IOException e) {
            log.error("涉密人员管理表导入异常", e);
            throw new RuntimeException("Excel导入失败：" + e.getMessage());
        }
    }
}
