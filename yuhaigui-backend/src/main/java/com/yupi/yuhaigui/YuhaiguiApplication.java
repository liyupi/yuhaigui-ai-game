package com.yupi.yuhaigui;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.yupi.yuhaigui.mapper")
public class YuhaiguiApplication {

    public static void main(String[] args) {
        SpringApplication.run(YuhaiguiApplication.class, args);
    }

}
