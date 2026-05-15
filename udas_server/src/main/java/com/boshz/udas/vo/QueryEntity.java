package com.boshz.udas.vo;

import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
public class QueryEntity<T> {
    private Long id;
    private List<Long> idList;
    private T query;
    private Integer current;
    private Integer pageSize = 10;
    private String sorter;
    private Map<String, Object> searchCondition;
}