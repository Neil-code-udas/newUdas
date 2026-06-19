package com.boshz.udas.performance.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.boshz.udas.performance.constant.PerformanceConstant;
import com.boshz.udas.performance.entity.BranchPerformance;
import com.boshz.udas.performance.entity.StaffPerformance;
import com.boshz.udas.performance.excel.BranchPerformanceExcel;
import com.boshz.udas.performance.listener.PerformanceExcelListener;
import com.boshz.udas.performance.mapper.BranchPerformanceMapper;
import com.boshz.udas.performance.mapper.StaffPerformanceMapper;
import com.boshz.udas.performance.service.BranchPerformanceService;
import com.boshz.udas.performance.vo.BranchPerformanceVO;
import com.boshz.udas.performance.vo.KeyWorkPerformanceVO;
import com.boshz.udas.performance.vo.PerformanceItemVO;
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
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class BranchPerformanceServiceImpl implements BranchPerformanceService {

    @Resource
    private BranchPerformanceMapper branchPerformanceMapper;

    @Resource
    private StaffPerformanceMapper staffPerformanceMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insert(BranchPerformance record) {
        return branchPerformanceMapper.insert(record) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(Long id) {
        if (branchPerformanceMapper.selectById(id) == null) {
            throw new RuntimeException("数据不存在");
        }
        return branchPerformanceMapper.deleteById(id) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(BranchPerformance record) {
        if (branchPerformanceMapper.selectById(record.getId()) == null) {
            throw new RuntimeException("数据不存在");
        }
        return branchPerformanceMapper.update(record) > 0;
    }

    @Override
    public BranchPerformance getById(Long id) {
        return branchPerformanceMapper.selectById(id);
    }

    @Override
    public PageVo<List<BranchPerformance>> getListByPage(QueryEntity<BranchPerformance> queryEntity) {
        BranchPerformance query = queryEntity.getQuery();
        Integer current = queryEntity.getCurrent();
        Integer pageSize = queryEntity.getPageSize();
        int offset = (current - 1) * pageSize;

        List<BranchPerformance> data = branchPerformanceMapper.queryAllByLimit(query, offset, pageSize);
        Long total = branchPerformanceMapper.selectCount(query);

        PageVo<List<BranchPerformance>> pageVo = new PageVo<>();
        pageVo.setData(data);
        pageVo.setPage(current);
        pageVo.setSize(pageSize);
        pageVo.setTotal(total.intValue());
        return pageVo;
    }

    @Override
    public List<BranchPerformance> getByCondition(BranchPerformance param) {
        return branchPerformanceMapper.selectByCondition(param);
    }

    @Override
    public void exportExcel(BranchPerformance query, HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("支行季度绩效表.xlsx", "UTF-8");
        response.setHeader("content-disposition", "attachment;filename=" + new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
        OutputStream out = response.getOutputStream();
        List<BranchPerformance> list = branchPerformanceMapper.queryAllByLimit(query, null, null);
        log.info("导出条数:{}", list.size());
        EasyExcel.write(out, BranchPerformanceExcel.class).sheet("支行季度绩效表").doWrite(list);
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

            List<BranchPerformance> saveList = new ArrayList<>();
            EasyExcel.read(file.getInputStream(), BranchPerformanceExcel.class, new ReadListener<BranchPerformanceExcel>() {
                        @Override
                        public void invoke(BranchPerformanceExcel dto, AnalysisContext context) {
                            BranchPerformance entity = BeanUtil.copyProperties(dto, BranchPerformance.class);
                            saveList.add(entity);
                        }

                        @Override
                        public void doAfterAllAnalysed(AnalysisContext context) {
                        }
                    })
                    .sheet("支行季度绩效表")
                    .headRowNumber(1)
                    .doRead();

            if (saveList.isEmpty()) {
                return 0;
            }
            return branchPerformanceMapper.insertBatch(saveList);
        } catch (IOException e) {
            log.error("支行季度绩效表导入异常", e);
            throw new RuntimeException("Excel导入失败：" + e.getMessage());
        }
    }

    /**
     * 核心导入方法（写死机构映射，无需查库）
     * @param inputStream Excel文件流
     * @param period 统计周期，例：2026.3月-2026.5月
     */
    @Transactional(rollbackFor = Exception.class)
    public void importExcel(InputStream inputStream, String period) {
        // 直接用写死的机构映射，无需查库
        PerformanceExcelListener listener = new PerformanceExcelListener(period, PerformanceConstant.BRANCH_COLUMN_MAP);
        // 读取Excel解析数据
        EasyExcel.read(inputStream,listener)
                .headRowNumber(2)
                .sheet()
                .doRead();
        // 批量入库
        List<BranchPerformance> dataList = listener.getSaveDataList();
        for (BranchPerformance branchPerformance : dataList) {
            branchPerformance.setKeyWorkCSub(branchPerformance.getPromotion1().
                                    add(branchPerformance.getAccount1()
                                    .add(branchPerformance.getCheck1()
                                    .add(branchPerformance.getBill1()
                                    .add(branchPerformance.getCash1())))));
            branchPerformance.setKeyWorkDSub(branchPerformance.getPromotion2().add(branchPerformance.getAccount2().add(branchPerformance.getCheck2()
                    .add(branchPerformance.getBill2().add(branchPerformance.getCash2())))));
            branchPerformance.setEvaluateDSub(branchPerformance.getScore());
            branchPerformance.setTotalAllD(branchPerformance.getKeyWorkDSub().add(branchPerformance.getEvaluateDSub()));
        }
        if (!dataList.isEmpty()) {
            branchPerformanceMapper.insertOrUpdateBatch(dataList);
        }
    }

    @Override
    public ResultVO<BranchPerformanceVO> getSingleBranchPerformance(BranchPerformance query) {
        List<BranchPerformance> entityList = branchPerformanceMapper.selectByCondition(query);
        if (entityList.isEmpty()) {
            return ResultVOUtil.success(null);
        }
        BranchPerformance entity = entityList.get(0);
        BranchPerformanceVO vo = convertToVO(entity);
        return ResultVOUtil.success(vo);
    }

    /** 实体转前端分层VO */
    private BranchPerformanceVO convertToVO(BranchPerformance entity) {
        StaffPerformance staffPerformance = new StaffPerformance();
        staffPerformance.setPeriod(entity.getPeriod());
        staffPerformance.setBranchCode(entity.getBranchCode());
        List<StaffPerformance> users = staffPerformanceMapper.selectByCondition(staffPerformance);
        BranchPerformanceVO vo = new BranchPerformanceVO();
        vo.setPeriod(entity.getPeriod());
        vo.setBranchCode(entity.getBranchCode());
        vo.setBranchName(entity.getBranchName());
        vo.setStaffCount(entity.getStaffCount());
//        vo.setTotalComplete(entity.getKeyWorkCSub());
//        vo.setTotalAllocate(entity.getTotalAllD());
//        vo.setCreateTime(entity.getCreateTime());
//        vo.setUpdateTime(entity.getUpdateTime());

        // 组装重点指标列表
        KeyWorkPerformanceVO keyWork = new KeyWorkPerformanceVO();
        List<PerformanceItemVO> itemList = new ArrayList<>();
        // 对公促活
        PerformanceItemVO p1 = new PerformanceItemVO();
        p1.setItemName("对公促活");
        p1.setFiled("promotion");
        p1.setCompleteScore(entity.getPromotion1());
        p1.setAllocateAmount(entity.getPromotion2());
        HashMap<String, BigDecimal> stringBigDecimalHashMap1 = new HashMap<>();
        for (StaffPerformance user : users) {
            stringBigDecimalHashMap1.put(user.getStaffNo(), user.getPromotion());
        }
        p1.setUserList(stringBigDecimalHashMap1);
        itemList.add(p1);
        // 新开户回访
        PerformanceItemVO p2 = new PerformanceItemVO();
        p2.setItemName("新开户回访");
        p2.setFiled("account");
        p2.setCompleteScore(entity.getAccount1());
        p2.setAllocateAmount(entity.getAccount2());
        HashMap<String, BigDecimal> stringBigDecimalHashMap2 = new HashMap<>();
        for (StaffPerformance user : users) {
            stringBigDecimalHashMap2.put(user.getStaffNo(), user.getAccount());
        }
        p2.setUserList(stringBigDecimalHashMap2);
        itemList.add(p2);
        // 人行账管系统核对
        PerformanceItemVO p3 = new PerformanceItemVO();
        p3.setItemName("人行账管系统核对");
        p3.setFiled("check");
        p3.setCompleteScore(entity.getCheck1());
        p3.setAllocateAmount(entity.getCheck2());
        HashMap<String, BigDecimal> stringBigDecimalHashMap3 = new HashMap<>();
        for (StaffPerformance user : users) {
            stringBigDecimalHashMap3.put(user.getStaffNo(), user.getCheok());
        }
        p3.setUserList(stringBigDecimalHashMap3);
        itemList.add(p3);
        // 催收银企对账单
        PerformanceItemVO p4 = new PerformanceItemVO();
        p4.setItemName("催收银企对账单");
        p4.setFiled("bill");
        p4.setCompleteScore(entity.getBill1());
        p4.setAllocateAmount(entity.getBill2());
        HashMap<String, BigDecimal> stringBigDecimalHashMap4 = new HashMap<>();
        for (StaffPerformance user : users) {
            stringBigDecimalHashMap4.put(user.getStaffNo(), user.getBill());
        }
        p4.setUserList(stringBigDecimalHashMap4);
        itemList.add(p4);
        // 现金网格化服务
        PerformanceItemVO p5 = new PerformanceItemVO();
        p5.setItemName("现金网格化服务");
        p5.setFiled("cash");
        p5.setCompleteScore(entity.getCash1());
        p5.setAllocateAmount(entity.getCash2());
        HashMap<String, BigDecimal> stringBigDecimalHashMap5 = new HashMap<>();
        for (StaffPerformance user : users) {
            stringBigDecimalHashMap5.put(user.getStaffNo(), user.getCash());
        }
        p5.setUserList(stringBigDecimalHashMap5);
        itemList.add(p5);

        // 重点工作绩效小计
        PerformanceItemVO p6 = new PerformanceItemVO();
        p6.setItemName("重点工作绩效小计");
        p6.setFiled("重点工作绩效小计");
        p6.setCompleteScore(entity.getKeyWorkCSub());
        p6.setAllocateAmount(entity.getKeyWorkDSub());
        HashMap<String, BigDecimal> stringBigDecimalHashMap6 = new HashMap<>();
        for (StaffPerformance user : users) {
            BigDecimal p = user.getPromotion() == null ? BigDecimal.ZERO : user.getPromotion();
            BigDecimal a = user.getAccount() == null ? BigDecimal.ZERO : user.getAccount();
            BigDecimal ch = user.getCheok() == null ? BigDecimal.ZERO : user.getCheok();
            BigDecimal b = user.getBill() == null ? BigDecimal.ZERO : user.getBill();
            BigDecimal c = user.getCash() == null ? BigDecimal.ZERO : user.getCash();
            BigDecimal keyWorkTotal = p.add(a).add(ch).add(b).add(c);
            stringBigDecimalHashMap6.put(user.getStaffNo(), keyWorkTotal);
        }
        p6.setUserList(stringBigDecimalHashMap6);
        itemList.add(p6);

        // 网点评价模块
        PerformanceItemVO p7 = new PerformanceItemVO();
        p7.setItemName("工作效率、团队协作、内控管理、培训考勤等");
        p7.setFiled("工作效率、团队协作、内控管理、培训考勤等");
        p7.setCompleteScore(null);
        p7.setAllocateAmount(entity.getScore());
        HashMap<String, BigDecimal> stringBigDecimalHashMap7 = new HashMap<>();
        for (StaffPerformance user : users) {
            stringBigDecimalHashMap7.put(user.getStaffNo(), user.getScore());
        }
        p7.setUserList(stringBigDecimalHashMap7);
        itemList.add(p7);

        PerformanceItemVO p8 = new PerformanceItemVO();
        p8.setItemName("网点评价绩效小计");
        p8.setFiled("网点评价绩效小计");
        p8.setCompleteScore(null);
        p8.setAllocateAmount(entity.getEvaluateDSub());
        HashMap<String, BigDecimal> stringBigDecimalHashMap8 = new HashMap<>();
        for (StaffPerformance user : users) {
            stringBigDecimalHashMap8.put(user.getStaffNo(), user.getNetworkEvalPerf());
        }
        p8.setUserList(stringBigDecimalHashMap8);
        itemList.add(p8);

        PerformanceItemVO p9 = new PerformanceItemVO();
        p9.setItemName("总合计");
        p9.setFiled("总合计");
        p9.setCompleteScore(entity.getKeyWorkCSub());
        p9.setAllocateAmount(entity.getKeyWorkDSub().add(entity.getEvaluateDSub()));
        HashMap<String, BigDecimal> stringBigDecimalHashMap9 = new HashMap<>();
        for (StaffPerformance user : users) {
            stringBigDecimalHashMap9.put(user.getStaffNo(), user.getTotalAmount());
        }
        p9.setUserList(stringBigDecimalHashMap9);
        itemList.add(p9);


        keyWork.setItemList(itemList);
//        keyWork.setSubCompleteTotal(entity.getKeyWorkCSub());
//        keyWork.setSubAmountTotal(entity.getKeyWorkDSub());
        vo.setKeyWork(keyWork);
        return vo;
    }

}
