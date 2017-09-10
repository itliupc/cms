package com.cms.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
  @Autowired  
  private UserDetailsServiceImpl customUserDetailsService; 


  @Bean
  public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
  }

  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception {
      httpSecurity
             .csrf().disable()
              .authorizeRequests()
              // 允许对于网站静态资源的无授权访问
              .antMatchers(
                      HttpMethod.GET,
                      "/**/swagger-resources/**",
                      "/**/api-docs/**",
                      "/**/webjars/**",
                      "/**/static/**",
                      "/*.html",
                      "/favicon.ico",
                      "/**/*.ftl",
                      "/**/*.css",
                      "/**/*.js"
              ).permitAll()
              // 对于获取token的rest api要允许匿名访问
              .antMatchers("/login").permitAll()
              // 除上面外的所有请求全部需要鉴权认证
              .anyRequest().authenticated()
              .and()  
                .formLogin()  
                .loginPage("/login")//指定登录页是”/login”
                .defaultSuccessUrl("/", true)
                .loginProcessingUrl("/dologin")
                .permitAll()
               .and()  
                .logout()  
                .logoutSuccessUrl("/login") //退出登录后的默认网址是”/home”  
                .permitAll();  
  }
  
  @Autowired  
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {     
    auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());  
        auth.eraseCredentials(false);         
    }  
}
