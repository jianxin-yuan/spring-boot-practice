# spring-boot

###  版本升级

添加`migrator`依赖来检查过时的配置项,完成迁移后,需要删除此模块

```xml
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-properties-migrator</artifactId>
	<scope>runtime</scope>
</dependency>
```

### 禁用特定的 Auto-configuration

可以使用下面三种方式来排除特定的auto-configuration

```yaml
# 配置文件方式
spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jms.JmsAutoConfiguration

# 在SpringBootApplication中排除特定类
@SpringBootApplication(exclude = RedisAutoConfiguration.class)
# 如果class不在 classpath 上，则可以使用 annotation 的excludeName属性并指定完全限定的 name
@SpringBootApplication(excludeName = "org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration")`
```

### Runner

如果需要在系统启动后执行特定的任务,可以实现`CommandLineRunner` 或者 `ApplicationRunner` 接口, 重写 `run`方法即可, 2种方式是一样的, 如果需要定义runner的执行顺序, 可以使用`@Order` 或 实现 `Order` 接口来定义执行顺序, 返回的值越小执行顺序越早

### 加载其他配置文件

使用`@PropertySource("classpath:test.properties")`可以加载其他的配置文件.

