package com.boshz.udas.subject.service.impl;


import com.boshz.udas.subject.entity.Voucher;
import com.boshz.udas.subject.mapper.VoucherMapper;
import com.boshz.udas.subject.service.VoucherService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class VoucherServiceImpl implements VoucherService {

    @Resource
    private VoucherMapper voucherMapper;

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    @Override
    public int add(Voucher voucher) {
        voucher.setVoucherNo(generateVoucherNo());
        return voucherMapper.insert(voucher);
    }

    @Override
    public int update(Voucher voucher) {
        return voucherMapper.update(voucher);
    }

    @Override
    public int deleteById(Long id) {
        return voucherMapper.deleteById(id);
    }

    @Override
    public Voucher getById(Long id) {
        return voucherMapper.selectById(id);
    }

    @Override
    public List<Voucher> pageList(String voucherNo, String applyDept, String subjectCode, Date startDate, Date endDate) {
        return voucherMapper.selectPage(voucherNo, applyDept, subjectCode, startDate, endDate);
    }

    @Override
    public String generateVoucherNo() {
        // 格式：PAY_20260602_随机串
        String dateStr = sdf.format(new Date());
        String uuid = UUID.randomUUID().toString().replace("-","").substring(0,8);
        return "PAY_" + dateStr + "_" + uuid;
    }
}