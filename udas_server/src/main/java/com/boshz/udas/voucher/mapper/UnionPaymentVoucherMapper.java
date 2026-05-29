package com.boshz.udas.voucher.mapper;
import com.boshz.udas.voucher.entity.UnionPaymentVoucher;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UnionPaymentVoucherMapper {
    // 新增凭证
    int insert(UnionPaymentVoucher voucher);
    // 根据ID查询凭证（用于预览、打印）
    UnionPaymentVoucher selectById(@Param("id") Long id);
}