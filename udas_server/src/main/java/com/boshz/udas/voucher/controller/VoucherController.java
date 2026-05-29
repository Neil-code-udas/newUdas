package com.boshz.udas.voucher.controller;


import com.boshz.udas.voucher.entity.UnionPaymentVoucher;
import com.boshz.udas.voucher.mapper.UnionPaymentVoucherMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/voucher")
public class VoucherController {

    @Resource
    private UnionPaymentVoucherMapper voucherMapper;

    // 保存凭证
    @PostMapping("/save")
    public Map<String,Object> save(@RequestBody UnionPaymentVoucher voucher){
        Map<String,Object> res = new HashMap<>();
        int rows = voucherMapper.insert(voucher);
        res.put("code",200);
        res.put("data",voucher.getId());
        return res;
    }

    // 查询凭证（用于打印页面回显）
    @GetMapping("/get/{id}")
    public UnionPaymentVoucher get(@PathVariable Long id){
        return voucherMapper.selectById(id);
    }


}