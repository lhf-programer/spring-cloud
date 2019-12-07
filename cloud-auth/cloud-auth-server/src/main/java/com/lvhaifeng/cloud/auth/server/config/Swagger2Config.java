package com.lvhaifeng.cloud.auth.server.config;

import com.fasterxml.classmate.TypeResolver;
import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.*;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.ApiListingScannerPlugin;
import springfox.documentation.spi.service.contexts.DocumentationContext;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.paths.RelativePathProvider;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.spring.web.readers.operation.CachingOperationNameGenerator;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.ServletContext;
import java.util.*;

/**
 * @Author haifeng.lv
 */
@Slf4j
@Configuration
@EnableSwagger2
@EnableSwaggerBootstrapUI
public class Swagger2Config {

    @Autowired
    private ServletContext servletContext;

    @Value("${swagger.zuul.host}")
    private String host;

    @Value("${swagger.zuul.prefix}")
    private String prefix;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .host(host)
                .pathProvider(new RelativePathProvider(servletContext) {
                    @Override
                    public String getApplicationBasePath() {
                        return prefix;
                    }
                })
                // 基本信息
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.lvhaifeng.cloud"))
                .paths(PathSelectors.regex("^(?!auth).*$"))
                .build();
    }

    /**
     * @description 自定义 api文档
     * @author haifeng.lv
     * @updateTime 2019/12/7 10:58
     * @return: springfox.documentation.spi.service.ApiListingScannerPlugin
     */
//    @Bean
    public ApiListingScannerPlugin apiListingScannerPlugin() {
        return new ApiListingScannerPlugin() {
            @Override
            public List<ApiDescription> apply(DocumentationContext documentationContext) {
                return new ArrayList<>(
                        Arrays.asList(
                                new ApiDescription("login",
                                        "/oauth/token",  // url
                                        "UserToken", // 描述
                                        Arrays.asList(
                                                new OperationBuilder(
                                                        new CachingOperationNameGenerator())
                                                        .method(HttpMethod.POST)// http请求类型
                                                        .produces(Sets.newHashSet(MediaType.APPLICATION_JSON_VALUE))
                                                        .summary("获取token")
                                                        .notes("获取token")// 方法描述
                                                        .position(-1)
                                                        .tags(Sets.newHashSet("登录获取 token"))// 归类标签
                                                        .parameters(
                                                                Arrays.asList(
                                                                        new ParameterBuilder()
                                                                                .description("授权")
                                                                                .type(new TypeResolver().resolve(String.class))
                                                                                .name("Authorization")
                                                                                .parameterType("header")
                                                                                .parameterAccess("access")
                                                                                .required(true)
                                                                                .modelRef(new ModelRef("string"))
                                                                                .build(),
                                                                        new ParameterBuilder()
                                                                                .description("oauth2鉴权方式，如password")//参数描述
                                                                                .type(new TypeResolver().resolve(String.class))//参数数据类型
                                                                                .name("grant_type")//参数名称
                                                                                .defaultValue("password")//参数默认值
                                                                                .parameterType("query")//参数类型
                                                                                .parameterAccess("access")
                                                                                .required(true)//是否必填
                                                                                .modelRef(new ModelRef("string"))
                                                                                .build(),
                                                                        new ParameterBuilder()
                                                                                .description("用户名")
                                                                                .type(new TypeResolver().resolve(String.class))
                                                                                .name("username")
                                                                                .parameterType("query")
                                                                                .parameterAccess("access")
                                                                                .required(true)
                                                                                .modelRef(new ModelRef("string"))
                                                                                .build(),
                                                                        new ParameterBuilder()
                                                                                .description("密码")
                                                                                .type(new TypeResolver().resolve(String.class))
                                                                                .name("password")
                                                                                .parameterType("query")
                                                                                .parameterAccess("access")
                                                                                .required(true)
                                                                                .modelRef(new ModelRef("string"))
                                                                                .build()
                                                                ))
                                                        .build()),
                                        false)));
            }

            @Override
            public boolean supports(DocumentationType documentationType) {
                return DocumentationType.SWAGGER_2.equals(documentationType);
            }
        };
    }

    /**
     * api文档的详细信息函数,注意这里的注解引用的是哪个
     *
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                // //大标题
                .title("Spring-Cloud 后台服务API接口文档")
                // 版本号
                .version("1.0")
                // 描述
                .description("后台API接口 (授权头默认 Basic Y2xvdWQ6Y2xvdWQ=)")
                .license("The Apache License, Version 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .build();
    }

}
