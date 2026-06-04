package com.boshz.udas.subject.mapper;


import com.boshz.udas.subject.entity.Voucher;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface VoucherMapper {

    int insert(Voucher voucher);

    int update(Voucher voucher);

    int deleteById(@Param("id") Long id);

    Voucher selectById(@Param("id") Long id);

    List<Voucher> selectPage(@Param("voucherNo") String voucherNo,
                             @Param("applyDept") String applyDept,
                             @Param("subjectCode") String subjectCode,
                             @Param("startDate") Date startDate,
                             @Param("endDate") Date endDate);
}