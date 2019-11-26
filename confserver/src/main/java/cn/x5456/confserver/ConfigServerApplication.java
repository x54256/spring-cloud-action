package cn.x5456.confserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * @author yujx
 * @date 2019/11/26 10:34
 */
@SpringBootApplication
// 使服务成为SpringCloudConfig服务
@EnableConfigServer
public class ConfigServerApplication {
    public static void main(String[] args) {
        // 启动服务并启动Spring容器
        SpringApplication.run(ConfigServerApplication.class, args);
    }
}
