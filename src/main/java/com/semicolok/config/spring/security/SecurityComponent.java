package com.semicolok.config.spring.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.semicolok.config.spring.security.handler.TestAuthenticationFailureHandler;
import com.semicolok.config.spring.security.handler.TestAuthenticationSuccessHandler;

@Configuration
public class SecurityComponent {
    @Autowired private DataSource dataSource;
    
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }
    
    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(10);
    }
    
    @Bean
    public AuthenticationEntryPoint loginUrlAuthenticationEntryPoint() {
        return new LoginUrlAuthenticationEntryPoint("/d/login");
    }
    
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        AccessDeniedHandlerImpl accessDeniedHandler = new AccessDeniedHandlerImpl();
        accessDeniedHandler.setErrorPage("/d/fail/auth");
        return accessDeniedHandler;
    }
    
    @Bean
    public AuthenticationSuccessHandler testAuthenticationSuccessHandler() {
        TestAuthenticationSuccessHandler authenticationSuccessHandler = new TestAuthenticationSuccessHandler();
        authenticationSuccessHandler.setAuthenticationSuccessUrl("/d/admin");
        return authenticationSuccessHandler;
    }
    
    @Bean
    public AuthenticationFailureHandler testAuthenticationFailureHandler() {
        TestAuthenticationFailureHandler authenticationFailureHandler = new TestAuthenticationFailureHandler();
        authenticationFailureHandler.setAuthenticationFailureUrl("/d/login/form");
        return authenticationFailureHandler;
    }
    
    @Bean(name = "jdbcTokenRepository")
    public PersistentTokenRepository jdbcTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepositoryImpl = new JdbcTokenRepositoryImpl();
        jdbcTokenRepositoryImpl.setDataSource(dataSource);
//        jdbcTokenRepositoryImpl.setCreateTableOnStartup(true);
        return jdbcTokenRepositoryImpl;
    }
    
    @Bean(name = "redisTokenRepository")
    @Autowired
    public PersistentTokenRepository redisTokenRepository(RedisTemplate<String, Object> redisTemplate) {
        PersistentTokenRepository redisTokenRepository = new RedisTokenRepositoryImpl(redisTemplate);
        return redisTokenRepository;
    }
}
