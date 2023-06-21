package com.macro.mall.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author lianglei
 * @version 1.0
 * @date 2023/6/20 21:16
 * @deprecated mybatis相关配置
 */
@Configuration
@EnableTransactionManagement
@MapperScan({"com.macro.mall.mapper", "com.macro.mall.dao"})
public class MyBatisConfig {

}
