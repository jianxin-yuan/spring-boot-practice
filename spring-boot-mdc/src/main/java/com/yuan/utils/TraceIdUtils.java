package com.yuan.utils;

import java.util.UUID;

/**
 * @author yuan
 * @date 2020/7/3 3:58 下午
 */
public class TraceIdUtils {
    public static final String TRACE_ID = "traceId";

    private TraceIdUtils() {
    }

    public static String generateTraceId() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
