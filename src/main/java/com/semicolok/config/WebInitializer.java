package com.semicolok.config;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;

import org.springframework.core.annotation.Order;
import org.springframework.mock.web.MockFilterConfig;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.semicolok.config.spring.ApplicationConfiguration;
import com.semicolok.config.spring.security.SecurityConfiguration;
import com.semicolok.config.spring.webmvc.ServletCofiguration;

// web.xml
@Order(2)
public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer  {
    
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[] {SecurityConfiguration.class, ApplicationConfiguration.class };
//        return new Class<?>[] { ApplicationConfiguration.class };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[] { ServletCofiguration.class };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] {"/d/*"};
    }
    
    @Override
    protected Filter[] getServletFilters() {
        return new Filter[] {
                new HiddenHttpMethodFilter()
                , characterEncodingFilter()
                , openEntityManagerInViewFilter()
//                , new DelegatingFilterProxy("springSecurityFilterChain")
                };
    }
    
    private Filter characterEncodingFilter() {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        return characterEncodingFilter;
    }

    private Filter openEntityManagerInViewFilter() {
        OpenEntityManagerInViewFilter oemInViewFilter = new OpenEntityManagerInViewFilter();
        MockFilterConfig filterConfig = new MockFilterConfig("OpenEntityManagerInViewFilter");
        filterConfig.addInitParameter("singleSession", "true");
        filterConfig.addInitParameter("flushMode", "AUTO");
        filterConfig.addInitParameter("entityManagerFactoryBeanName", "entityManagerFactory");
        try {
            oemInViewFilter.init(getFilterConfig());
        } catch (ServletException e) {
            e.printStackTrace();
        }
        return oemInViewFilter;
    }

    private FilterConfig getFilterConfig() {
        MockFilterConfig filterConfig = new MockFilterConfig("OpenEntityManagerInViewFilter");
        filterConfig.addInitParameter("singleSession", "true");
        filterConfig.addInitParameter("flushMode", "AUTO");
        filterConfig.addInitParameter("entityManagerFactoryBeanName", "entityManagerFactory");
        return filterConfig;
    }
}
