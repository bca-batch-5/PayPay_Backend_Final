package com.paypay.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@SuppressWarnings("deprecation")
public class PayPaySecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }

    // @Bean
    // FilterConfig filterConfig() throws Exception{
    //     return new FilterConfig(authenticationManagerBean());
    // }

    @Bean
    FilterConfig jwtFilter(){
        return new FilterConfig();
    }

    @Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/paypay/login").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/paypay/register").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.PUT, "/paypay/forget-pass").permitAll();
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    
}
