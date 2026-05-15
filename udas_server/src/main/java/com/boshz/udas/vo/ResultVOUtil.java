package com.boshz.udas.vo;

public class ResultVOUtil {
    private final static Integer SUCCESS_CODE = 0;
    private final static String SUCCESS_MESSAGE = "success";

    public static <T> ResultVO<T> success(T t) {
        ResultVO<T> r = new ResultVO<>();
        r.setCode(SUCCESS_CODE);
        r.setMessage(SUCCESS_MESSAGE);
        r.setData(t);
        return r;
    }
    public static ResultVO<Object> success() {
        return success(null);
    }
    public static <T> ResultVO<T> error(Integer code, String msg) {
        ResultVO<T> r = new ResultVO<>();
        r.setCode(code);
        r.setMessage(msg);
        return r;
    }
}