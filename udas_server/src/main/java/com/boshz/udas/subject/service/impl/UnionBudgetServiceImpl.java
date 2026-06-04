package com.boshz.udas.subject.service.impl;


import com.alibaba.excel.util.StringUtils;
import com.boshz.udas.subject.dto.SubjectBudgetDTO;
import com.boshz.udas.subject.dto.TableDataDTO;
import com.boshz.udas.subject.dto.YearDataDTO;
import com.boshz.udas.subject.mapper.UnionBudgetMapper;
import com.boshz.udas.subject.mapper.UnionSubjectMapper;
import com.boshz.udas.subject.service.UnionBudgetService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
public class UnionBudgetServiceImpl implements UnionBudgetService {

    @Resource
    private UnionSubjectMapper subjectMapper;

    @Resource
    private UnionBudgetMapper budgetMapper;

    @Override
    public TableDataDTO getTableData(List<Integer> yearList, String subjectCode, String subjectName) {
        List<String> codeList = new ArrayList<>();

        // 递归查当前+所有下级科目编码
        if (StringUtils.isNotBlank(subjectCode)) {
            Set<String> allCodeSet = new HashSet<>();
            getAllChildCode(subjectCode, allCodeSet);
            codeList = new ArrayList<>(allCodeSet);
            if (CollectionUtils.isEmpty(codeList)) {
                TableDataDTO empty = new TableDataDTO();
                empty.setYears(yearList);
                empty.setRows(new ArrayList<>());
                return empty;
            }
        }

        // 查询原始扁平数据
        List<SubjectBudgetDTO> flatList = budgetMapper.selectSubjectBudgetList(yearList, codeList, subjectName);
        if (CollectionUtils.isEmpty(flatList)) {
            TableDataDTO empty = new TableDataDTO();
            empty.setYears(yearList);
            empty.setRows(new ArrayList<>());
            return empty;
        }

        List<SubjectBudgetDTO> mergedList = mergeSubjectData(flatList);
        List<SubjectBudgetDTO> treeRows = buildTree(mergedList);

        TableDataDTO result = new TableDataDTO();
        result.setYears(yearList);
        result.setRows(treeRows);
        return result;
    }

    /** 递归：获取自身+全子节点编码 */
    private void getAllChildCode(String parentCode, Set<String> codeSet) {
        List<String> self = subjectMapper.selectCodeByCode(parentCode);
        if (!CollectionUtils.isEmpty(self)) {
            codeSet.addAll(self);
        }
        List<String> childList = subjectMapper.selectDirectChildCode(parentCode);
        if (CollectionUtils.isEmpty(childList)) {
            return;
        }
        for (String childCode : childList) {
            codeSet.add(childCode);
            getAllChildCode(childCode, codeSet);
        }
    }

    /** 同科目多年份数据合并 */
    private List<SubjectBudgetDTO> mergeSubjectData(List<SubjectBudgetDTO> flatList) {
        Map<String, SubjectBudgetDTO> subjectMap = new HashMap<>();

        for (SubjectBudgetDTO dto : flatList) {
            String code = dto.getCode();
            Integer year = dto.getYear();
            if (year == null) {
                continue;
            }
            String yearKey = year.toString();

            if (!subjectMap.containsKey(code)) {
                subjectMap.put(code, dto);
            }
            SubjectBudgetDTO mainDto = subjectMap.get(code);

            YearDataDTO yearData = new YearDataDTO();
            yearData.setBudget(dto.getBudget());
            yearData.setActual(dto.getActual());

            BigDecimal budget = dto.getBudget();
            BigDecimal actual = dto.getActual();
            if (budget != null && actual != null && budget.compareTo(BigDecimal.ZERO) > 0) {
                BigDecimal rate = actual.divide(budget, 4, RoundingMode.HALF_UP)
                        .multiply(new BigDecimal("100"))
                        .setScale(2, RoundingMode.HALF_UP);
                yearData.setRate(rate);
            }

            if (!mainDto.getYearData().containsKey(yearKey)) {
                mainDto.getYearData().put(yearKey, yearData);
            }
        }

        List<SubjectBudgetDTO> resultList = new ArrayList<>(subjectMap.values());
        for (SubjectBudgetDTO dto : resultList) {
            dto.setYear(null);
            dto.setBudget(null);
            dto.setActual(null);
            dto.setChildren(new ArrayList<>());
        }
        return resultList;
    }

    /** 组装科目树形 */
    private List<SubjectBudgetDTO> buildTree(List<SubjectBudgetDTO> list) {
        Map<String, SubjectBudgetDTO> codeMap = new HashMap<>();
        List<SubjectBudgetDTO> rootList = new ArrayList<>();

        for (SubjectBudgetDTO dto : list) {
            codeMap.put(dto.getCode(), dto);
        }

        for (SubjectBudgetDTO dto : list) {
            String parentCode = dto.getParentCode();
            if (StringUtils.isBlank(parentCode)) {
                rootList.add(dto);
            } else {
                SubjectBudgetDTO parent = codeMap.get(parentCode);
                if (parent != null) {
                    parent.getChildren().add(dto);
                }
            }
        }
        return rootList;
    }
}