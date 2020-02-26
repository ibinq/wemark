package com.ibinq.wemark.common;

import lombok.Data;

@Data
public class Result<T> {

    public final static Integer CODE_SUCCESS = 0;
    public final static Integer CODE_FAIL = 1;
    public final static String MSG_SUCCESS = "操作成功";
    public final static String MSG_FAIL = "操作失败";

    // 响应业务状态 0 成功， 1失败
    private Integer code;

    // 响应消息
    private String msg;

    // 响应中的数据
    private T data;

    public static <T> Result<T> build(Integer status, String msg, T data) {
        return new Result<T>(status, msg, data);
    }

    public static <T> Result<T> ok(T data) {
        return new Result<T>(data);
    }

    /**
     * 返回
     * @param data 数据
     * @param unull 不可null
     * @return
     */
    public static <T> Result<T> ok(T data, boolean unull) {
        if(data == null && unull){
            return fail("未获取到数据");
        }
        return ok(data);
    }

    public static <T> Result<T> ok() {
        return new Result<T>(CODE_SUCCESS, MSG_SUCCESS, null);
    }

    public static <T> Result<T> fail() {
        return new Result<T>(CODE_FAIL, MSG_FAIL, null);
    }

    public static <T> Result<T> fail(String msg) {
        return new Result<T>(CODE_FAIL, msg, null);
    }

    /**
     * 状态
     * @param flag 状态
     * @return
     */
    public static <T> Result<T> status(boolean flag) {
        if(flag){
            return ok();
        }
        return fail();
    }

    public Result() {

    }

    public static <T> Result<T> build(Integer status, String msg) {
        return new Result<T>(status, msg, null);
    }

    public static <T> Result<T> getResult(T t) {
        Result<T> result = new Result<>(t);
        return result;
    }

    public Result(Integer status, String msg, T data) {
        this.code = status;
        this.msg = msg;
        this.data = data;
    }

    public Result(T data) {
        this.code = 0;
        this.msg = MSG_SUCCESS;
        this.data = data;
    }
}
