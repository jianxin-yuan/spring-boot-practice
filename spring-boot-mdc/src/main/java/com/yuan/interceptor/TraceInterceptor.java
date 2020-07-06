package com.yuan.interceptor;

import com.yuan.utils.TraceIdUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author yuan
 * @date 2020/7/3 9:28 上午
 * 用于请求到达前设置traceId,请求完成后清空资源
 */
@Slf4j
public class TraceInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        MDC.put(TraceIdUtils.TRACE_ID, TraceIdUtils.generateTraceId());
        log.info("TraceInterceptor.preHandle...");
        return super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("TraceInterceptor.afterCompletion ....");
        MDC.clear();
        super.afterCompletion(request, response, handler, ex);
    }


}
