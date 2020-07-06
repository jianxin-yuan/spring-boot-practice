package com.yuan.advice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yuan.response.Result;
import com.yuan.utils.TraceIdUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author yuan
 * @date 2020/7/3 5:54 下午
 * <p>
 * 通过实现ResponseBodyAdvice接口controller接口的返回值做一个包装,给外部返回一个统一的结构,同时带上traceId
 */
@ControllerAdvice
@Slf4j
public class ResponseWrapper implements ResponseBodyAdvice<Object> {

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 返回true表示执行advice中的逻辑,返回false表示不执行
     *
     * @param methodParameter
     * @param aClass
     * @return
     */
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }


    /**
     * 对返回值进行统一处理,加入traceId便于查找问题
     *
     * @param body
     * @param methodParameter
     * @param mediaType
     * @param aClass
     * @param request
     * @param response
     * @return
     */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType,
                                  Class<? extends HttpMessageConverter<?>> aClass,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response) {
        Object wrapper = body;
        String traceId = MDC.get(TraceIdUtils.TRACE_ID);
        try {
            if (!(body instanceof Result)) {
                //字符串特殊处理
                if (body instanceof String) {
                    wrapper = objectMapper.writeValueAsString(Result.success(body, traceId));
                } else {
                    wrapper = Result.success(body, traceId);
                }
            } else {
                ((Result) wrapper).setTraceId(traceId);
            }
        } catch (Exception e) {
            log.error("request uri path: {}, format response body error", request.getURI().getPath(), e);
        }

        log.info("response wrapper end.....");
        return wrapper;
    }
}