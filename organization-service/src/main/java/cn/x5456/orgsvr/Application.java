package cn.x5456.orgsvr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


@SpringBootApplication
// 注册到Eureka，访问地址http://127.0.0.1:8761/eureka/apps/CONFIGSERVER可以看到注册信息
@EnableEurekaClient
//@EnableFeignClients
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
