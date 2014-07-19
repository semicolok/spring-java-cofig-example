package com.semicolok.config.spring;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Components {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
