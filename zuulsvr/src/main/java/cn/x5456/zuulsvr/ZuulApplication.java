package cn.x5456.zuulsvr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @author yujx
 * @date 2019/11/26 16:28
 */
@SpringBootApplication
@EnableZuulProxy    // 成为一个zuul服务器
public class ZuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZuulApplication.class, args);
    }
}
