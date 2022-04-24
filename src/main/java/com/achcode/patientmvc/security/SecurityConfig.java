package com.achcode.patientmvc.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder passwordEncoder = passwordEncoder();
        //Authentication with user in memory
        //{noop} use password without encoding
        auth.inMemoryAuthentication()
                .withUser("user1").password(passwordEncoder.encode("1234")).roles("USER")
                .and()
                .withUser("admin").password(passwordEncoder.encode("1234")).roles("ADMIN","USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin();
        http.authorizeRequests().antMatchers("/").permitAll();
        http.authorizeRequests().antMatchers("/delete/**","/edit/**","/editPatient/**","/save/**","/formPatient/**").hasRole("ADMIN");
        http.authorizeRequests().antMatchers("/index/**").hasRole("USER");
        //http.authorizeRequests().anyRequest().authenticated();
        http.exceptionHandling().accessDeniedPage("/403");

    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


}
