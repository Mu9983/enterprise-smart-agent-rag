package com.mu9983.entity;

import lombok.Data;

/**
 * 标准返回类
 */
@Data
public class Result {

    private Integer code;   // 状态码
    private String msg;     // 响应信息
    private Object data;    // 返回数据

    public static Result success(){
        Result result = new Result();
        result.msg = "success";
        result.code = 1;
        return result;
    }

    public static Result success(Object data){
        Result result = success();
        result.data = data;
        return result;
    }

    public static Result error(String msg){
        Result result = new Result();
        result.msg = msg;
        result.code = 0;
        return result;
    }

}
