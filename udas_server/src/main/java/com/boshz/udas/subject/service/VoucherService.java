package com.boshz.udas.subject.service;


import com.boshz.udas.subject.entity.Voucher;

import java.util.Date;
import java.util.List;

public interface VoucherService {

    /**
     * 新增凭证（自动生成单号）
     */
    int add(Voucher voucher);

    /**
     * 编辑凭证
     */
    int update(Voucher voucher);

    /**
     * 根据ID删除凭证
     */
    int deleteById(Long id);

    /**
     * 根据ID查询凭证详情
     */
    Voucher getById(Long id);

    /**
     * 凭证列表条件分页查询
     */
    List<Voucher> pageList(String voucherNo, String applyDept, String subjectCode, Date startDate, Date endDate);

    /**
     * 生成唯一凭证单号
     */
    String generateVoucherNo();
}