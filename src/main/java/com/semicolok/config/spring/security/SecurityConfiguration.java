package com.semicolok.config.spring.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@Import(value = {SecurityComponent.class})
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true) // Method  extends GlobalMethodSecurityConfiguration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
    
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private UserDetailsService userDetailsService;
    @Autowired private AuthenticationEntryPoint authenticationEntryPoint;
    @Autowired private AccessDeniedHandler accessDeniedHandler;
    @Autowired private AuthenticationSuccessHandler testAuthenticationSuccessHandler;
    @Autowired private AuthenticationFailureHandler testAuthenticationFailureHandler;
//    @Autowired private PersistentTokenRepository jdbcTokenRepository;
    @Autowired private PersistentTokenRepository redisTokenRepository;
//    @Autowired private DataSource dataSource;
    
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//      auth.inMemoryAuthentication().withUser("semicolok").password("123456").roles("USER");
//      auth.inMemoryAuthentication().withUser("admin").password("123456").roles("ADMIN");
//      auth.inMemoryAuthentication().withUser("dba").password("123456").roles("DBA");
//    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
//            .jdbcAuthentication()
//            .passwordEncoder(passwordEncoder);
//            .dataSource(dataSource)
//            .usersByUsernameQuery("SELECT email AS username, password AS password, 1 FROM USER WHERE email=?")
//            .authoritiesByUsernameQuery("SELECT email AS username, role FROM USER WHERE email=? AND enabled=1");
    }
    
    @Bean(name="myAuthenticationManager")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    
    @Override
    public void configure(WebSecurity web) throws Exception {
        web
        .ignoring()
        .antMatchers("/d/resources/**");
    }
 
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeRequests()
            .antMatchers("/d/fail*/**", "/d/signup", "/d/signin", "/d/login*/**", "/d/test**", "/d/boards*/**").permitAll()
//            .antMatchers(HttpMethod.GET, "/d/boards*/**").permitAll()
//            .antMatchers("/d/admin*/**").hasRole("USER")
            .antMatchers("/d/admin*/**").access("hasRole('ADMIN')")
            .antMatchers("/d/dba*/**").access("hasRole('ADMIN') or hasRole('DBA')")
            .anyRequest().authenticated()
        .and()
            .formLogin()
                .successHandler(testAuthenticationSuccessHandler)
                .failureHandler(testAuthenticationFailureHandler)
                .loginPage("/d/login/form")
                .loginProcessingUrl("/d/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/d/admin", true)
                .failureUrl("/d/board.json")
                .permitAll()
        .and()
            .logout()
            .logoutRequestMatcher(new AntPathRequestMatcher("/d/signout"))
//                .logoutUrl("/d/signout")
                .logoutSuccessUrl("/d/boards.json")
        .and()
            .httpBasic().authenticationEntryPoint(authenticationEntryPoint)
        .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)
        .and()
            .exceptionHandling().accessDeniedHandler(accessDeniedHandler)
        .and()
//            .rememberMe().key("semiKey").rememberMeServices(new TestRememberMeServices("semiKey", userDetailsService, jdbcTokenRepository))
            .rememberMe().key("semiKey").rememberMeServices(new TestRememberMeServices("semiKey", userDetailsService, redisTokenRepository))
//        .and()
//            .rememberMe().tokenRepository(persistentTokenRepository)
//            .tokenValiditySeconds(1209600)
        ;
    }
}
