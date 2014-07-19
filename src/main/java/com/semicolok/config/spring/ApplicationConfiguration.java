package com.semicolok.config.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Controller;

import com.semicolok.config.spring.data.DatabaseCofiguration;
import com.semicolok.config.spring.redis.RedisConfiguration;

// root-context.xml
@Configuration
@Import(value = {PropertiesConfiguration.class, DatabaseCofiguration.class, RedisConfiguration.class, Components.class})
//@Import(value = {PropertiesConfiguration.class, SecurityConfiguration.class, DatabaseCofiguration.class})
@ComponentScan( basePackages = {"com.semicolok.web"}, 
                excludeFilters = { @ComponentScan.Filter(Controller.class), @ComponentScan.Filter(Configuration.class)})
public class ApplicationConfiguration {

}
