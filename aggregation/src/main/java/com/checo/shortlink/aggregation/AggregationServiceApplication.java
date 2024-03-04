package com.checo.shortlink.aggregation;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 短链接聚合应用
 * 公众号：马丁玩编程，回复：加群，添加马哥微信（备注：link）获取项目资料
 */
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {
        "com.checo.shortlink.admin",
        "com.checo.shortlink.project"
})
@MapperScan(value = {
        "com.checo.shortlink.project.dao.mapper",
        "com.checo.shortlink.admin.dao.mapper"
})
public class AggregationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AggregationServiceApplication.class, args);
    }
}
