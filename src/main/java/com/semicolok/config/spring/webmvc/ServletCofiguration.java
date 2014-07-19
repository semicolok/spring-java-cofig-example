package com.semicolok.config.spring.webmvc;

import java.nio.charset.Charset;
import java.util.List;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.mobile.device.DeviceHandlerMethodArgumentResolver;
import org.springframework.mobile.device.DeviceResolverHandlerInterceptor;
import org.springframework.mobile.device.site.SitePreferenceHandlerInterceptor;
import org.springframework.mobile.device.site.SitePreferenceHandlerMethodArgumentResolver;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.mvc.WebContentInterceptor;

import com.semicolok.config.spring.PropertiesConfiguration;

// servlet-context.xml
@Configuration
@Import(value = {PropertiesConfiguration.class, Resolvers.class})
//@Import(value = {Resolvers.class})
@EnableWebMvc
@ComponentScan(basePackages = {"com.semicolok.web.controller"})
public class ServletCofiguration extends WebMvcConfigurerAdapter{

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }
    
    // using HTTP message converters
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.favorPathExtension(true)
            .useJaf(false)
            .ignoreAcceptHeader(false)
            .mediaType("json", MediaType.APPLICATION_JSON)
            .mediaType("xml", MediaType.APPLICATION_XML)
            .defaultContentType(MediaType.APPLICATION_JSON);
    }
    
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//        converters.add(new FormHttpMessageConverter());
        converters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        converters.add(new MappingJackson2HttpMessageConverter());
//        converters.add(new Jaxb2RootElementHttpMessageConverter());
        converters.add(createXmlHttpMessageConverter());
    }
    
    private HttpMessageConverter<Object> createXmlHttpMessageConverter() {
        MarshallingHttpMessageConverter xmlConverter = new MarshallingHttpMessageConverter();
        XStreamMarshaller xstreamMarshaller = new XStreamMarshaller();
        xmlConverter.setMarshaller(xstreamMarshaller);
        xmlConverter.setUnmarshaller(xstreamMarshaller);
        return xmlConverter;
    }

    
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new DeviceHandlerMethodArgumentResolver());
        argumentResolvers.add(new SitePreferenceHandlerMethodArgumentResolver());
        argumentResolvers.add(new PageableHandlerMethodArgumentResolver());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(webContentInterceptor());
        registry.addInterceptor(localeChangeInterceptor());
        registry.addInterceptor(new DeviceResolverHandlerInterceptor());
        registry.addInterceptor(new SitePreferenceHandlerInterceptor());
    }

    public HandlerInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");
        return localeChangeInterceptor;
    }
    
    public WebContentInterceptor webContentInterceptor() {
        WebContentInterceptor interceptor = new WebContentInterceptor();
        interceptor.setCacheSeconds(0);
        interceptor.setUseExpiresHeader(true);;
        interceptor.setUseCacheControlHeader(true);
        interceptor.setUseCacheControlNoStore(true);
        return interceptor;
    }
}
