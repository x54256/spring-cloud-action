package cn.x5456.lincenses;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

// 通过该注解，当POST访问http://127.0.0.1:8080/refresh地址，即可重新加载应用程序配置中的**自定义**Spring属性，Spring Data等数据库配置不会被其加载
// SpringCloud2.0 需要在bootstrap.yml中额外配置，地址修改为http://127.0.0.1:8080/actuator/refresh
@RefreshScope
@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient  // 使应用程序可以使用DiscoveryClient和Ribbon库
@EnableFeignClients
@EnableCircuitBreaker   // 为服务使用Hystrix（客户端熔断）
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @LoadBalanced
    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
