package com.yuan.executor;

import com.yuan.utils.TraceIdUtils;
import org.slf4j.MDC;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * @author yuan
 * @date 2020/7/3 3:47 下午
 * 覆盖线程池,使用MDC设置traceId
 */
public class MdcThreadPoolTaskExecutor extends ThreadPoolTaskExecutor {

    @Override
    public void execute(Runnable task) {
        super.execute(wrap(task));
    }

    @Override
    public void execute(Runnable task, long startTimeout) {
        super.execute(wrap(task), startTimeout);
    }

    @Override
    public Future<?> submit(Runnable task) {
        return super.submit(wrap(task));
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        return super.submit(wrap(task));
    }

    @Override
    public ListenableFuture<?> submitListenable(Runnable task) {
        return super.submitListenable(wrap(task));
    }

    @Override
    public <T> ListenableFuture<T> submitListenable(Callable<T> task) {
        return super.submitListenable(wrap(task));
    }

    @Override
    protected void cancelRemainingTask(Runnable task) {
        super.cancelRemainingTask(wrap(task));
    }


    private static Runnable wrap(Runnable task) {
        Map<String, String> context = MDC.getCopyOfContextMap();
        return () -> {
            MDC.setContextMap(context);
            putIfAbsent();
            try {
                task.run();
            } finally {
                MDC.clear();
            }
        };

    }

    private static <T> Callable<T> wrap(Callable<T> task) {
        Map<String, String> context = MDC.getCopyOfContextMap();
        return () -> {
            MDC.setContextMap(context);
            putIfAbsent();
            try {
                return task.call();
            } finally {
                MDC.clear();
            }
        };
    }

    private static void putIfAbsent() {
        if (MDC.get(TraceIdUtils.TRACE_ID) == null) {
            MDC.put(TraceIdUtils.TRACE_ID, TraceIdUtils.generateTraceId());
        }
    }

}
