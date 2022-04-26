package com.achcode.patientmvc.security;

import com.achcode.patientmvc.service.SecurityService;
import com.achcode.patientmvc.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    //@Autowired
    //@Lazy
    private UserDetailsServiceImpl userDetailsService;

    /**
     * 4.2 utiliser l'annotation @ Lazy
     * L'un des moyens les plus simples d'éliminer les dépendances circulaires consiste à retarder le chargement.
     * Lors de l'injection de dépendances, injectez d'abord des objets proxy, puis créez des objets pour terminer
     * l'injection lorsqu'ils sont utilisés pour la première fois.
     * @param userDetailsService
     */
    @Autowired
    public SecurityConfig(@Lazy UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {


        PasswordEncoder passwordEncoder = passwordEncoder();
        //Authentication with user in memory
        //{noop} use password without encoding

        /*auth.inMemoryAuthentication()
                .withUser("user1").password(passwordEncoder.encode("1234")).roles("USER")
                .and()
                .withUser("admin").password(passwordEncoder.encode("1234")).roles("ADMIN","USER");
         */


        auth.userDetailsService(new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return userDetailsService.loadUserByUsername(username);
            }
        });

        //auth.userDetailsService(userDetailsService);


    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin();
        http.authorizeRequests().antMatchers("/").permitAll();
        http.authorizeRequests().antMatchers("/login","/register").anonymous();

        http.authorizeRequests().antMatchers("/delete/**","/edit/**","/editPatient/**","/save/**","/formPatient/**").hasAuthority("ADMIN");
        //http.authorizeRequests().antMatchers("/delete/**","/edit/**","/editPatient/**","/save/**","/formPatient/**").hasRole("ADMIN");
        //http.authorizeRequests().antMatchers("/index/**").hasRole("USER");
        http.authorizeRequests().antMatchers("/index/**").hasAuthority("USER");
        http.authorizeRequests().antMatchers("/webjars/**").permitAll();
        http.authorizeRequests().anyRequest().authenticated();
        http.exceptionHandling().accessDeniedPage("/403");

    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


}
