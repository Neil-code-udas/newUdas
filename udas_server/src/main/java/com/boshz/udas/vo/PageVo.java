package com.boshz.udas.vo;

import lombok.Data;
import java.io.Serializable;

@Data
public class PageVo<T> implements Serializable {
    private T data;
    private Integer page;
    private Integer size;
    private Integer total;
    private String message;
}