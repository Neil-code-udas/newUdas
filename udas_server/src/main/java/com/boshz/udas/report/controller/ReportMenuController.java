package com.boshz.udas.report.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import com.boshz.udas.report.entity.Menu;
import com.boshz.udas.report.entity.ReportDef;
import com.boshz.udas.report.mapper.ReportDefMapper;
import com.boshz.udas.report.query.MenuQuery;
import com.boshz.udas.report.service.ReportDefService;
import com.boshz.udas.vo.PageVo;
import com.boshz.udas.vo.QueryEntity;
import com.boshz.udas.vo.ResultVO;
import com.boshz.udas.vo.ResultVOUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportMenuController {
    private final ReportDefMapper reportDefMapper;
    private final ReportDefService reportDefService;


    @GetMapping("/menu")
    public ResultVO<Object> menu() {
        //查询没有删除 并且审核通过的
        List<ReportDef> list = reportDefMapper.selectAll();
        List<Map<String, Object>> menus = list.stream().map(def -> {
            String path = "/report/" + def.getReportCode();
            //todo 加菜单参数
//            Menu menu = reportDefMapper.findByPath(path);
            Map<String, Object> map = new HashMap<>();
            map.put("name1", def.getReportName());
            map.put("name", "/report/" + def.getReportCode());
            map.put("path", "/report/" + def.getReportCode());
            map.put("code", def.getReportCode());
            map.put("componentName", "/autoReport");
//            map.put("appId", menu.getAppId());
//            map.put("parentId", menu.getParentId());
            return map;
        }).collect(Collectors.toList());
        return ResultVOUtil.success(menus);
    }

    //数智台账创建菜单查询分页，根据登录用户查询角色 数智台账管理员（查看所有数据，审核） 普通用户（发布，只能看到自己的数据）
    @PostMapping("/getListByPage")
    public ResultVO<Object> getListByPage(@RequestBody QueryEntity<MenuQuery> queryEntity, @RequestParam String account) {
        if (StringUtils.isEmpty(account)) {
            return ResultVOUtil.error(9000, "请登录");
        }
        //先判断管理员，不写角色
        if(account.contains("admin")){
            account=null;
        }
        PageVo pageVo = reportDefService.getListByPage(queryEntity,account);
        return ResultVOUtil.success(pageVo);
    }

    //todo 发布
    @PostMapping("/publish")
    public ResultVO<Object> publish(@RequestParam String account) {
        if (StringUtils.isEmpty(account)) {
            return ResultVOUtil.error(9000, "请登录");
        }
//        reportDefService.publish();
        return ResultVOUtil.success();
    }
    //todo 管理员审核通过与驳回
    @PostMapping("/audit")
    public ResultVO<Object> audit(@RequestParam Long id, @RequestParam String account) {
        if (!account.contains("admin")) {
            return ResultVOUtil.error(9000, "仅管理员可审核");
        }
        //审核通过 邮件发送 状态置为1
        //审核驳回 邮件发送 状态置为2
//        reportDefMapper.updateStatus(id, 1);
        return ResultVOUtil.success("审核成功");
    }

    /**
     * 菜单逻辑删除
     * 管理员：可删所有未审核台账
     * 普通用户：只能删自己创建、且未审核的台账
     */
    @PostMapping("/remove")
    public ResultVO<Void> remove(@RequestParam Long id,
                                 @RequestParam String account) {
//        return reportDefService.removeReportById(id, account);
        return null;
    }


}