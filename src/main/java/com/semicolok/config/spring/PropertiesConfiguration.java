package com.semicolok.config.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;


@Configuration
@PropertySource("classpath:/META-INF/application.properties")
//@PropertySources(value = {@PropertySource("classpath:/datasource.properties")})
public class PropertiesConfiguration {
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
