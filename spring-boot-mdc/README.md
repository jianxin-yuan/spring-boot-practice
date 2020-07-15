### 使用MDC实现请求全流程日志跟踪

思路: 要实现日志的全链路跟踪,最简单的方法就是在请求到达是创建一个traceId,方法如ThreadLocal中,然后后续方法打印日志的时候从ThreadLocal中取就行了, 这里使用MDC实现(内部也是使用ThreadLocal实现的)

> MDC（Mapped Diagnostic Context，映射调试上下文）是 log4j 和 logback 提供的一种方便在多线程条件下记录日志的功能。MDC 可以看成是一个与当前线程绑定的Map，可以往其中添加键值对。MDC 中包含的内容可以被同一线程中执行的代码所访问。当前线程的子线程会继承其父线程中的 MDC 的内容。当需要记录日志时，只需要从 MDC 中获取所需的信息即可。MDC 的内容则由程序在适当的时候保存进去。对于一个 Web 应用来说，通常是在请求被处理的最开始保存这些数据。

#### 实现

* 创建一个拦截器, 将traceId设置进MDC中

  ```java
  public class TraceInterceptor extends HandlerInterceptorAdapter {
  
      @Override
      public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
          MDC.put(TraceIdUtils.TRACE_ID, TraceIdUtils.generateTraceId());
          return super.preHandle(request, response, handler);
      }
  
      @Override
      public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
          MDC.clear();
          super.afterCompletion(request, response, handler, ex);
      }
  }	
  ```

* 日志系统配置文件中通过 `%X{traceid}` 即可取得MDC中的值

  ```xml
   <property name="FILE_LOG_PATTERN"
                value="${FILE_LOG_PATTERN:-%d{yyyy-MM-dd HH:mm:ss.SSS} [%X{traceId}] ${LOG_LEVEL_PATTERN:-%5p} ${PID:- } --- [%t] %-40.40logger{39} : %m%n${LOG_EXCEPTION_CONVERSION_WORD:-%wEx}}"/>
  ```

  

  这个可以实现简单的日志跟踪, 但是由于在项目中很多地方使用的是线程池,而线程池中的线程时复用的,所以会导致后续请求的traceId不正确,所以需要对线程池进行一定的封装改造.



* 新建一个`MdcThreadPoolTaskExecutor` ,继承 `ThreadPoolTaskExecutor` ,然后重写里面的几个`execute(),submit()` 方法

  ```java
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
  ```

  

