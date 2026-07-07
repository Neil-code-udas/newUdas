package com.boshz.udas.pair.controller;


import com.boshz.udas.pair.dto.PairCreateDTO;
import com.boshz.udas.pair.dto.PairQueryDTO;
import com.boshz.udas.pair.dto.StaffDTO;
import com.boshz.udas.pair.entity.TeacherStudentPair;
import com.boshz.udas.pair.service.PairService;
import com.boshz.udas.vo.ResultVO;
import com.boshz.udas.vo.ResultVOUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/pair")
public class PairController {
    @Resource
    private PairService pairService;

    // 获取机构下符合条件师父列表
    @GetMapping("/teacher/list")
    public ResultVO getTeacherList(@RequestParam String orgId) {
        List<StaffDTO> list = pairService.getTeacherList(orgId);
        return ResultVOUtil.success(list);
    }

    // 获取机构下符合条件徒弟列表
    @GetMapping("/student/list")
    public ResultVO getStudentList(@RequestParam String orgId) {
        List<StaffDTO> list = pairService.getStudentList(orgId);
        return ResultVOUtil.success(list);
    }

    // 查询本机构结对台账
    @GetMapping("/list")
    public ResultVO getPairList(PairQueryDTO query) {
        List<TeacherStudentPair> list = pairService.getPairList(query);
        return ResultVOUtil.success(list);
    }

    // 创建师徒结对签约（无泛型写法）
    @PostMapping("/create")
    public ResultVO createPair(@RequestBody PairCreateDTO dto) {
        return pairService.createPair(dto);
    }

    // 根据TP编号查询结对详情（工作台顶部卡片）
    @GetMapping("/detail")
    public ResultVO getPairDetail(@RequestParam String pairId) {
        return pairService.getPairDetail(pairId);
    }
}