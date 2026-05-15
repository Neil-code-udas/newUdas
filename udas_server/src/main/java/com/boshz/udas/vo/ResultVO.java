package com.boshz.udas.vo;

import lombok.Data;
import java.io.Serializable;

@Data
public class ResultVO<T> implements Serializable {
    private static final long serialVersionUID = 7975552769865038647L;
    private Integer code;
    private String message;
    private T data;
}