package com.semicolok.config.spring.webmvc;

import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
public class Resolvers {
    
    // using MVC with ModelAndView
//  @Bean
//  public ViewResolver contentNegotiatingViewResolver(ContentNegotiationManager manager) {
//       
//      List< ViewResolver > resolvers = new ArrayList<ViewResolver>(2);
//       
//      InternalResourceViewResolver resourceViewResolver = new InternalResourceViewResolver();
//      resourceViewResolver.setPrefix("/WEB-INF/views/");
//      resourceViewResolver.setSuffix(".jsp");
//      resourceViewResolver.setViewClass(JstlView.class);
//      resolvers.add(resourceViewResolver);
//       
//      JsonViewResolver jsonViewResolver = new JsonViewResolver();
//      resolvers.add(jsonViewResolver);
//       
//      ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
//      resolver.setViewResolvers(resolvers);
//      resolver.setContentNegotiationManager(manager);
//      return resolver;
//  }
//  
//  public class JsonViewResolver implements ViewResolver {
//      public View resolveViewName(String viewName, Locale locale) throws Exception {
//              MappingJackson2JsonView view = new MappingJackson2JsonView();
//              view.setPrettyPrint(true);
//              return view;
//      }
//  }
    
    @Bean
    public ViewResolver viewResolver () {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }
    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(Locale.KOREA);
        return localeResolver;
    }
}
