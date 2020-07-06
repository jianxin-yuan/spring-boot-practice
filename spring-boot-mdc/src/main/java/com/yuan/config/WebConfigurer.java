package com.yuan.config;

import com.yuan.executor.MdcThreadPoolTaskExecutor;
import com.yuan.interceptor.TraceInterceptor;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.concurrent.Executor;

/**
 * @author yuan
 * @date 2020/7/2 11:49 上午
 */
@Configuration
public class WebConfigurer implements AsyncConfigurer, WebMvcConfigurer {

    /**
     * 配置拦截器
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TraceInterceptor())
                .addPathPatterns("/**");
    }

    /**
     * 自定义线程池
     *
     * @return
     */
    @Override
    public Executor getAsyncExecutor() {
        MdcThreadPoolTaskExecutor executor = new MdcThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(30);
        executor.setThreadNamePrefix("MyExecutor-");
        executor.initialize();
        return executor;
    }


    /**
     * 线程异常处理
     *
     * @return
     */
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (ex, method, params) -> {
            String msg = "execute error,method:" + method + ",params:" + Arrays.toString(params);
            throw new RuntimeException(msg, ex);
        };
    }
}
