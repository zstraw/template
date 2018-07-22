package com.didi.game.configuration;

import com.didi.game.util.JsonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;


@EntityScan(basePackages = {
        "com.didi.game.domain"
})
@ComponentScan({"com.didi.game.service", "com.didi.game.controller","com.didi.game.exception"})
public class BaseConfiguration {

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        return JsonUtils.OBJECT_MAPPER;
    }

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        return new MappingJackson2HttpMessageConverter(objectMapper());
    }

}
