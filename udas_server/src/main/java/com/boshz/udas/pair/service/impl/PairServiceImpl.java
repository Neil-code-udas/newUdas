package com.boshz.udas.pair.service.impl;


import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import com.alibaba.fastjson2.JSON;
import com.boshz.udas.pair.dto.FileDTO;
import com.boshz.udas.pair.dto.PairCreateDTO;
import com.boshz.udas.pair.dto.PairQueryDTO;
import com.boshz.udas.pair.dto.StaffDTO;
import com.boshz.udas.pair.entity.PairTimeLine;
import com.boshz.udas.pair.entity.TeacherStudentPair;
import com.boshz.udas.pair.mapper.PairMapper;
import com.boshz.udas.pair.mapper.PairTimeLineMapper;
import com.boshz.udas.pair.service.PairService;
import com.boshz.udas.vo.ResultVO;
import com.boshz.udas.vo.ResultVOUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PairServiceImpl implements PairService {
    @Resource
    private PairMapper pairMapper;
    @Resource
    private PairTimeLineMapper pairTimeLineMapper;

    @Override
    public List<StaffDTO> getTeacherList(String orgId) {
        return pairMapper.selectTeacherList(orgId);
    }

    @Override
    public List<StaffDTO> getStudentList(String orgId) {
        return pairMapper.selectStudentList(orgId);
    }

    @Override
    public List<TeacherStudentPair> getPairList(PairQueryDTO query) {
        return pairMapper.selectList(query);
    }

    @Override
    public ResultVO createPair(PairCreateDTO dto) {
        // 1. 一师一徒校验
        int teacherCount = pairMapper.countTeacherTrain(dto.getTeacherAccount());
        if (teacherCount > 0) {
            return ResultVOUtil.error(9000, "该师父当前已有在培徒弟，无法新建结对");
        }
        int studentCount = pairMapper.countStudentTrain(dto.getStudentAccount());
        if (studentCount > 0) {
            return ResultVOUtil.error(9000, "该师父当前已有在培徒弟，无法新建结对");
        }

        // 2. 生成TP结对单据主键
        String pairId = "TP" + DateUtil.format(new Date(), "yyyyMMdd") + UUID.fastUUID().toString().substring(0, 6);
        TeacherStudentPair pair = new TeacherStudentPair();
        pair.setId(pairId);
        pair.setTeacherAccount(dto.getTeacherAccount());
        pair.setStudentAccount(dto.getStudentAccount());
        pair.setOrgId(dto.getOrgId());
        pair.setStartDate(dto.getStartDate());
        // 自动计算12个月到期日
// 直接LocalDate加12个月，无需转换
        LocalDate endDate = dto.getStartDate().plusMonths(12);
        pair.setEndDate(endDate);
        pair.setSignFileUrl(dto.getSignFileUrl());
        pair.setCreateAccount(dto.getCreateAccount());
        pair.setPairStatus("TRAINING");
        pairMapper.insert(pair);

        // 3. 自动插入签约SIGN时间轴记录
        PairTimeLine signLine = new PairTimeLine();
        signLine.setPairId(pairId);
        signLine.setOperateAccount(dto.getCreateAccount());
        signLine.setOperateUserName("部门管理员");
        signLine.setOperateType("SIGN");
        signLine.setContent("完成师徒结对签约，开启12个月标准化带教周期");
        signLine.setScore(null);
        FileDTO file = new FileDTO();
        file.setFileName("师徒结对承诺书.pdf");
        file.setFileUrl(dto.getSignFileUrl());
        List<FileDTO> fileList = new ArrayList<>();
        fileList.add(file);
        signLine.setFileUrls(JSON.toJSONString(fileList));
        signLine.setOperateTime(LocalDateTime.now());
        pairTimeLineMapper.insert(signLine);
        return ResultVOUtil.success(pairId);
    }

    @Override
    public ResultVO getPairDetail(String pairId) {
        TeacherStudentPair pair = pairMapper.selectById(pairId);
        if (pair == null) {
            return ResultVOUtil.error(9000, "结对单据不存在");
        }
        // JDK原生LocalDate计算，统一LocalDate类型，无类型冲突
        LocalDate now = LocalDate.now();
        long remainDay = ChronoUnit.DAYS.between(now, pair.getEndDate());
        pair.setRemainDay((int) remainDay);
        return ResultVOUtil.success(pair);
    }
}