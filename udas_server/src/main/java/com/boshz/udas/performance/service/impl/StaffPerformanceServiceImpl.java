package com.boshz.udas.performance.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.boshz.udas.department.entity.Department;
import com.boshz.udas.department.mapper.DepartmentMapper;
import com.boshz.udas.performance.dto.BatchStaffPerfUpdateDTO;
import com.boshz.udas.performance.entity.StaffPerformance;
import com.boshz.udas.performance.excel.StaffPerformanceExcel;
import com.boshz.udas.performance.mapper.StaffPerformanceMapper;
import com.boshz.udas.performance.query.StaffPerfBatchUpdateQuery;
import com.boshz.udas.performance.service.StaffPerformanceService;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class StaffPerformanceServiceImpl implements StaffPerformanceService {

    @Resource
    private StaffPerformanceMapper staffPerformanceMapper;

    @Resource
    private DepartmentMapper departmentMapper;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insert(StaffPerformance record) {
        return staffPerformanceMapper.insert(record) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(Long id) {
        if (staffPerformanceMapper.selectById(id) == null) {
            throw new RuntimeException("数据不存在");
        }
        return staffPerformanceMapper.deleteById(id) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(StaffPerformance record) {
        if (staffPerformanceMapper.selectById(record.getId()) == null) {
            throw new RuntimeException("数据不存在");
        }
        return staffPerformanceMapper.update(record) > 0;
    }

    @Override
    public StaffPerformance getById(Long id) {
        return staffPerformanceMapper.selectById(id);
    }

    @Override
    public PageVo<List<StaffPerformance>> getListByPage(QueryEntity<StaffPerformance> queryEntity) {
        StaffPerformance query = queryEntity.getQuery();
        Integer current = queryEntity.getCurrent();
        Integer pageSize = queryEntity.getPageSize();
        int offset = (current - 1) * pageSize;

        List<StaffPerformance> data = staffPerformanceMapper.queryAllByLimit(query, offset, pageSize);
        for (StaffPerformance datum : data) {
//            BigDecimal totalAmount = datum.getTotalAmount();
//            BigDecimal add = totalAmount.add(datum.getSpecialBonus() == null ? BigDecimal.ZERO : datum.getSpecialBonus());

            datum.setTotal(
                    (datum.getSpecialBonus() == null ? BigDecimal.ZERO : datum.getSpecialBonus()).add(
                            datum.getTotalAmount() == null ? BigDecimal.ZERO : datum.getTotalAmount()
                    )
            );
        }
        Long total = staffPerformanceMapper.selectCount(query);

        PageVo<List<StaffPerformance>> pageVo = new PageVo<>();
        pageVo.setData(data);
        pageVo.setPage(current);
        pageVo.setSize(pageSize);
        pageVo.setTotal(total.intValue());
        return pageVo;
    }

    @Override
    public List<StaffPerformance> getByCondition(StaffPerformance param) {
        return staffPerformanceMapper.selectByCondition(param);
    }

    @Override
    public void exportExcel(StaffPerformance query, HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("员工绩效汇总表.xlsx", "UTF-8");
        response.setHeader("content-disposition", "attachment;filename=" + new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
        OutputStream out = response.getOutputStream();
        List<StaffPerformance> list = staffPerformanceMapper.queryAllByLimit(query, null, null);
        log.info("导出条数:{}", list.size());
        EasyExcel.write(out, StaffPerformanceExcel.class).sheet("员工绩效汇总表").doWrite(list);
        out.flush();
        out.close();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer importExcel(MultipartFile file,String period) {
        try {
            String fileName = file.getOriginalFilename();
            Assert.notNull(fileName, "导入文件不能为空");
            Assert.isTrue(fileName.endsWith(".xlsx"), "仅支持.xlsx格式文件");

            List<StaffPerformance> saveList = new ArrayList<>();
            EasyExcel.read(file.getInputStream(), StaffPerformanceExcel.class, new ReadListener<StaffPerformanceExcel>() {
                        @Override
                        public void invoke(StaffPerformanceExcel dto, AnalysisContext context) {
                            StaffPerformance entity = BeanUtil.copyProperties(dto, StaffPerformance.class);
                            Department department = departmentMapper.selectByOrgName(entity.getBranchName());
                            entity.setPeriod(period);
                            entity.setBranchCode(department.getOrgCode());
                            saveList.add(entity);
                        }

                        @Override
                        public void doAfterAllAnalysed(AnalysisContext context) {
                        }
                    })
                    .sheet()
                    .headRowNumber(1)
                    .doRead();

            if (saveList.isEmpty()) {
                return 0;
            }
            return staffPerformanceMapper.insertBatch(saveList);
        } catch (IOException e) {
            log.error("员工绩效汇总表导入异常", e);
            throw new RuntimeException("Excel导入失败：" + e.getMessage());
        }
    }

    // 合法更新字段白名单
    private static final Set<String> ALLOW_FIELD = new HashSet<>();
    static {
        ALLOW_FIELD.add("promotion");
        ALLOW_FIELD.add("account");
        ALLOW_FIELD.add("cheok");
        ALLOW_FIELD.add("bill");
        ALLOW_FIELD.add("cash");
        ALLOW_FIELD.add("score");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO batchUpdatePerf(StaffPerfBatchUpdateQuery query) {
        String period = query.getPeriod();
        List<BatchStaffPerfUpdateDTO> updateList = query.getUpdateList();
        BigDecimal frontTotal = query.getTotalAmount();

        // 1. 参数校验
        if (period == null || period.isEmpty() || updateList == null || updateList.isEmpty()) {
            return ResultVOUtil.error(9000, "参数错误");
        }
        for (BatchStaffPerfUpdateDTO dto : updateList) {
            if (!ALLOW_FIELD.contains(dto.getTargetField())
                    || dto.getStaffNo() == null || dto.getStaffNo().isEmpty()
                    || dto.getAmount() == null) {
                return ResultVOUtil.error(9000, "参数错误");
            }
        }
        // 2. 计算明细里所有amount总和
        BigDecimal sumAllAmount = BigDecimal.ZERO;
        for (BatchStaffPerfUpdateDTO dto : updateList) {
            sumAllAmount = sumAllAmount.add(dto.getAmount());
        }

        // 3. 校验：明细总和 不等于 前端传入totalAmount则返回错误
        // compareTo: 0相等、1大于、-1小于
        if (sumAllAmount.compareTo(frontTotal) != 0) {
            return ResultVOUtil.error(9001, "各人员金额相加应等于totalAmount");
        }

        // 2. 批量更新指定指标字段
        for (BatchStaffPerfUpdateDTO dto : updateList) {
            int updateCount = staffPerformanceMapper.updatePerfField(period,query.getBranchCode(), dto);
        }

        // 3. 循环重算汇总值 key_work_perf / total_amount
        for (BatchStaffPerfUpdateDTO dto : updateList) {
            StaffPerformance perf = staffPerformanceMapper.selectByPeriodStaffNo(period,query.getBranchCode(), dto.getStaffNo());
            if (perf == null) continue;
            // 重点工作绩效 = 5项指标相加
            BigDecimal keyWork = (perf.getPromotion() == null ? BigDecimal.ZERO : perf.getPromotion())
                    .add(perf.getAccount() == null ? BigDecimal.ZERO : perf.getAccount())
                    .add(perf.getCheok() == null ? BigDecimal.ZERO : perf.getCheok())
                    .add(perf.getBill() == null ? BigDecimal.ZERO : perf.getBill())
                    .add(perf.getCash() == null ? BigDecimal.ZERO : perf.getCash());
            // 总金额 = 重点工作 + 网点评价 + 专项奖励
            BigDecimal total = perf.getNetworkEvalPerf()== null ? BigDecimal.ZERO : perf.getNetworkEvalPerf()
                    .add(perf.getKeyWorkPerf()== null ? BigDecimal.ZERO : perf.getKeyWorkPerf());

            // 更新汇总字段
            StaffPerformance updateEntity = new StaffPerformance();
            updateEntity.setNetworkEvalPerf(perf.getScore());
            updateEntity.setId(perf.getId());
            updateEntity.setKeyWorkPerf(keyWork);
            updateEntity.setTotalAmount(total);
            staffPerformanceMapper.update(updateEntity);
        }

        return ResultVOUtil.success();
    }
}
