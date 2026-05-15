package com.boshz.udas.report.entity;


import lombok.Data;

/**
 * @author chencheng
 * @date 2020/3/30
 */

@Data
public class Menu{

    private Long id;
    /**
     * 所属应用id {@link}
     */
    private Long appId;

    /**
     * 上级菜单id
     */
    private Long parentId;

    /**
     * 菜单显示名称
     */
    private String name;

    /**
     * 菜单访问路径
     */
    private String path;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 是否隐藏
     */
    private Integer hideInMenu;


    /**
     * 是否允许复制
     */
    private Boolean copy;

    /**
     * 菜单排序码
     */
    private String sort;

    /**
     * 条线id
     */
    private Long lineId;

    /**
     * 访问条线id
     */
    private String accessLineId;


    /**
     * 自然语言描述
     */
    private String description;
}