package com.lvhaifeng.cloud.admin.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description 单数据源配置
 * @Author haifeng.lv
 * @Date 2019/12/16 17:55
 */
@Configuration
@MapperScan("com.lvhaifeng.cloud.admin.mapper")
public class MybatisPlusConfig {

    /**
     * @Description 分页插件
     * @Author haifeng.lv
     * @Date 2019/12/16 17:55
     * @return: com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        // 设置sql的limit为无限制，默认是500
        return new PaginationInterceptor().setLimit(-1);
    }

}
