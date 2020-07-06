package com.yuan.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author yuan
 * @date 2020/7/3 5:40 下午
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    private Object data;
    private String code;
    private String message;
    private String traceId;

    public static Result success(Object obj) {
        return new Result(obj, "200", "success", "");
    }

    public static Result success(Object obj, String traceId) {
        return new Result(obj, "200", "success", traceId);
    }

    public static Result error(Object obj, String message) {
        return new Result(obj, "500", message, "");
    }
}
