package com.yuan.advice;

import com.yuan.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author yuan
 * @date 2020/7/3 10:28 下午
 * 全局异常处理类
 */
@RestControllerAdvice
@Slf4j
public class ExceptionHandlerAdvice {

    @ExceptionHandler(Exception.class)
    public Result exceptionHandler(Exception e) {
        log.error("global exception handler...");
        return Result.error(null, e.getMessage());
    }
}
