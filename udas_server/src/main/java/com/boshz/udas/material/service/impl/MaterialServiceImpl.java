package com.boshz.udas.material.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.boshz.udas.material.entity.Material;
import com.boshz.udas.material.excel.MaterialExcel;
import com.boshz.udas.material.mapper.MaterialMapper;
import com.boshz.udas.material.service.MaterialService;
import com.boshz.udas.vo.PageVo;
import com.boshz.udas.vo.QueryEntity;
import com.boshz.udas.vo.ResultVO;
import com.boshz.udas.vo.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class MaterialServiceImpl implements MaterialService {

    @Resource
    private MaterialMapper materialMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO insert(Material material) {
        // 校验名称重复
        Material exist = materialMapper.selectByName(material.getMaterialName());
        if (exist != null) {
            return ResultVOUtil.error(9000,"物资【" + material.getMaterialName() + "】已存在，不可重复新增");
        }
        material.setStock(material.getStock() == null ? BigDecimal.ZERO : material.getStock());
        materialMapper.insert(material);
        return ResultVOUtil.success("新增物资成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(Long id) {
        if (materialMapper.selectById(id) == null) {
            throw new RuntimeException("数据不存在");
        }
        return materialMapper.deleteById(id) > 0;
    }


    @Override
    public Material getById(Long id) {
        return materialMapper.selectById(id);
    }

    @Override
    public PageVo<List<Material>> getListByPage(QueryEntity<Material> queryEntity) {
        Material query = queryEntity.getQuery();
        Integer current = queryEntity.getCurrent();
        Integer pageSize = queryEntity.getPageSize();
        int offset = (current - 1) * pageSize;

        List<Material> data = materialMapper.queryAllByLimit(query, offset, pageSize);
        Long total = materialMapper.selectCount(query);

        PageVo<List<Material>> pageVo = new PageVo<>();
        pageVo.setData(data);
        pageVo.setPage(current);
        pageVo.setSize(pageSize);
        pageVo.setTotal(total.intValue());
        return pageVo;
    }

    @Override
    public List<Material> getByCondition(Material param) {
        return materialMapper.selectByCondition(param);
    }

    @Override
    public void exportExcel(Material query, HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("办公用品基础档案.xlsx", "UTF-8");
        response.setHeader("content-disposition", "attachment;filename=" + new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
        OutputStream out = response.getOutputStream();
        List<Material> list = materialMapper.queryAllByLimit(query, null, null);
        log.info("导出条数:{}", list.size());
        EasyExcel.write(out, MaterialExcel.class).sheet("办公用品基础档案").doWrite(list);
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

            List<Material> saveList = new ArrayList<>();
            EasyExcel.read(file.getInputStream(), MaterialExcel.class, new ReadListener<MaterialExcel>() {
                        @Override
                        public void invoke(MaterialExcel dto, AnalysisContext context) {
                            Material entity = BeanUtil.copyProperties(dto, Material.class);
                            saveList.add(entity);
                        }

                        @Override
                        public void doAfterAllAnalysed(AnalysisContext context) {
                        }
                    })
                    .sheet("办公用品基础档案")
                    .headRowNumber(1)
                    .doRead();

            if (saveList.isEmpty()) {
                return 0;
            }
            return materialMapper.insertBatch(saveList);
        } catch (IOException e) {
            log.error("办公用品基础档案导入异常", e);
            throw new RuntimeException("Excel导入失败：" + e.getMessage());
        }
    }


    public ResultVO<List<Material>> list(String materialName) {
        List<Material> list = materialMapper.selectList(materialName);
        return ResultVOUtil.success(list);
    }

    public ResultVO<String> add(Material material) {
        // 校验名称重复
        Material exist = materialMapper.selectByName(material.getMaterialName());
        if (exist != null) {
            return ResultVOUtil.error(9000,"物资【" + material.getMaterialName() + "】已存在，不可重复新增");
        }
        material.setStock(material.getStock() == null ? BigDecimal.ZERO : material.getStock());
        materialMapper.insert(material);
        return ResultVOUtil.success("新增物资成功");
    }

    public ResultVO update(Material material) {
        Material exist = materialMapper.selectById(material.getId());
        if (exist == null) {
            return ResultVOUtil.error(9000,"物资不存在");
        }
        // 库存仅允许出入库修改，编辑基础信息不改动库存
        material.setStock(exist.getStock());
        // 如果修改了名称，校验是否和其他物资重名
        if (!exist.getMaterialName().equals(material.getMaterialName())) {
            Material nameExist = materialMapper.selectByName(material.getMaterialName());
            if (nameExist != null) {
                return ResultVOUtil.error(9000,"修改后的物资名称已存在");
            }
        }
        materialMapper.updateById(material);
        return ResultVOUtil.success("修改成功");
    }

    public ResultVO<String> delete(Long id) {
        Material exist = materialMapper.selectById(id);
        if (exist == null) {
            return ResultVOUtil.error(9000,"物资不存在");
        }
        materialMapper.deleteById(id);
        return ResultVOUtil.success("删除成功");
    }

    // 根据名称查询物资（出入库使用）
    public Material getByName(String materialName) {
        return materialMapper.selectByName(materialName);
    }

    // 更新库存
    public void updateStock(Long id, BigDecimal newStock) {
        materialMapper.updateStock(id, newStock);
    }
}
