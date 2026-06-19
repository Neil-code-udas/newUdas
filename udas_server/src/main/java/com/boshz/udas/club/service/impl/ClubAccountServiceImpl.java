package com.boshz.udas.club.service.impl;


import com.boshz.udas.club.mapper.ClubAccountMapper;
import com.boshz.udas.club.service.ClubAccountService;
import com.boshz.udas.club.vo.ClubGroupVO;
import com.boshz.udas.club.vo.ClubItemDetailVO;
import com.boshz.udas.club.vo.ClubItemVO;
import com.boshz.udas.club.vo.ClubYearTotalVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ClubAccountServiceImpl implements ClubAccountService {

    @Resource
    private ClubAccountMapper clubAccountMapper;

    @Override
    public ClubYearTotalVO getYearClubAccountDetail(String year) {
        // 1. 查询汇总金额
        List<ClubItemVO> clubSumList = clubAccountMapper.selectClubSumByYear(year);
        // 2. 查询全部流水明细
        List<Map<String, Object>> allDetailList = clubAccountMapper.selectAllDetailByYear(year);
        // 3. 按俱乐部编码分组明细
        Map<String, List<Map<String, Object>>> detailGroupMap = allDetailList.stream()
                .collect(Collectors.groupingBy(m -> (String) m.get("club_code")));

        // 4. 给每个俱乐部填充4个分组明细（收入会费、收入经费、支出会费、支出经费）
        for (ClubItemVO clubItem : clubSumList) {
            String clubCode = clubItem.getClubCode();
            List<Map<String, Object>> clubDetailList = detailGroupMap.getOrDefault(clubCode, new ArrayList<>());

            // 初始化4个分组
            ClubGroupVO incomeFeeGroup = new ClubGroupVO();
            ClubGroupVO incomeFundGroup = new ClubGroupVO();
            ClubGroupVO outFeeGroup = new ClubGroupVO();
            ClubGroupVO outFundGroup = new ClubGroupVO();

            List<ClubItemDetailVO> incomeFeeDetail = new ArrayList<>();
            List<ClubItemDetailVO> incomeFundDetail = new ArrayList<>();
            List<ClubItemDetailVO> outFeeDetail = new ArrayList<>();
            List<ClubItemDetailVO> outFundDetail = new ArrayList<>();

            // 遍历当前俱乐部所有流水，归类
            for (Map<String, Object> row : clubDetailList) {
                String ioType = row.get("io_type").toString();
                String tradeType = (String) row.get("trade_type");

                // 组装单条明细
                ClubItemDetailVO detail = new ClubItemDetailVO();
                String tradeTime = row.get("trade_time").toString();
                detail.setTradeTime(LocalDate.parse(tradeTime));
                detail.setSubject((String) row.get("subject"));
                detail.setAmount((BigDecimal) row.get("amount"));
                detail.setRemark((String) row.get("remark"));

                // 分类存入对应列表
                if ("1".equals(ioType)) {
                    // 收入：历年结余、会费 全部放入会费明细数组
                    if ("会费".equals(tradeType) || "历年结余".equals(tradeType)) {
                        incomeFeeDetail.add(detail);
                    } else if ("经费".equals(tradeType) || "奖励".equals(tradeType)) {
                        incomeFundDetail.add(detail);
                    }
                } else {
                    // 支出
                    if ("会费".equals(tradeType)) {
                        outFeeDetail.add(detail);
                    } else if ("经费".equals(tradeType)) {
                        outFundDetail.add(detail);
                    }
                }
            }

            // 给分组赋值小计、明细
            incomeFeeGroup.setSubTotal(clubItem.getIncomeFeeSum());
            incomeFeeGroup.setDetailList(incomeFeeDetail);
            incomeFundGroup.setSubTotal(clubItem.getIncomeFundSum());
            incomeFundGroup.setDetailList(incomeFundDetail);
            outFeeGroup.setSubTotal(clubItem.getOutFeeSum());
            outFeeGroup.setDetailList(outFeeDetail);
            outFundGroup.setSubTotal(clubItem.getOutFundSum());
            outFundGroup.setDetailList(outFundDetail);

            // 填充到俱乐部VO
            clubItem.setIncomeFeeGroup(incomeFeeGroup);
            clubItem.setIncomeFundGroup(incomeFundGroup);
            clubItem.setOutFeeGroup(outFeeGroup);
            clubItem.setOutFundGroup(outFundGroup);
        }

        // 5. 计算年度全局合计
        BigDecimal allIncome = BigDecimal.ZERO;
        BigDecimal allOut = BigDecimal.ZERO;
        BigDecimal allSiteFee = BigDecimal.ZERO;
        BigDecimal allSurplus = BigDecimal.ZERO;
        for (ClubItemVO item : clubSumList) {
            allIncome = allIncome.add(item.getIncomeTotal());
            allOut = allOut.add(item.getOutTotal());
            allSiteFee = allSiteFee.add(item.getSiteFeeTotal());
            allSurplus = allSurplus.add(item.getYearSurplus());
        }

        // 6. 组装顶层返回对象
        ClubYearTotalVO result = new ClubYearTotalVO();
        result.setYear(year);
        result.setClubList(clubSumList);
        result.setAllIncomeTotal(allIncome);
        result.setAllOutTotal(allOut);
        result.setAllSiteFeeTotal(allSiteFee);
        result.setAllSurplusTotal(allSurplus);
        return result;
    }
}