package com.didi.game.configuration;

import com.didi.game.exception.AppExceptionHandlerController;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;
import java.time.LocalDateTime;


@EnableSwagger2
public class WebApplication {

//    @Bean
//    public ErrorController errorController(ErrorAttributes errorAttributes, ServerProperties serverProperties) {
//        return new AppErrorController(errorAttributes, serverProperties.getError());
//    }

    @Bean
    public AppExceptionHandlerController appExceptionHandlerController() {
        return new AppExceptionHandlerController();
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .directModelSubstitute(LocalDate.class, java.sql.Date.class)
                .directModelSubstitute(LocalDateTime.class, java.util.Date.class)
                .select()
                .apis(requestHandler -> {
                    String packageName = requestHandler.getHandlerMethod().getMethod()
                            .getDeclaringClass().getPackage().getName();
                    return packageName.startsWith("com.didi") && packageName.contains(".controller");
                })
                .paths(PathSelectors.any())
                .build();
    }

}