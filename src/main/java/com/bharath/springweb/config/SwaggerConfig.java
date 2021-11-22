package com.bharath.springweb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(getApiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("com.bharath.springweb.controllers"))
                .paths(PathSelectors.regex("/productapi/products.*")).build();
    }

    private ApiInfo getApiInfo() {
        return new ApiInfoBuilder()
                .title("Product API")
                .description("Product CRUD Operations")
                .termsOfServiceUrl("Open Source")
                .license("Bahrath License")
                .licenseUrl("bharaththippireddy.com")
                .version("2.0")
                .build();
    }

}
