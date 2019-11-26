package cn.x5456.lincenses;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;

// 通过该注解，当POST访问http://127.0.0.1:8080/refresh地址，即可重新加载应用程序配置中的**自定义**Spring属性，Spring Data等数据库配置不会被其加载
// SpringCloud2.0 需要在bootstrap.yml中额外配置，地址修改为http://127.0.0.1:8080/actuator/refresh
// 未测试成功，可能打开方式不对
@RefreshScope
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
