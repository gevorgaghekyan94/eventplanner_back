package com.example.backeventplanner.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final static String[] PERMIT_ALL_LIST = {
//            "/register",
//            "/registration",
            "/http://localhost:8080/swagger-ui/"
    };

    private final static String[] PERMIT_ADMIN_OR_USER_LIST = {
            "/login",
            "/login/events"
    };

    private final static String[] PERMIT_ADMIN_LIST = {
            "/register/admin",
            "/address/{id}",
            "/addresses",
            "/order/{id}",
            "/orders",
            "/order/delete/{id}",
            "/person/{id}",
            "/persons",
            "/person/age/{age}",
            "/person/name/{name}",
            "/person/update/{id}",
            "/person/delete/{id}",
            "/product/update/{id}",
            "/product/delete/{id}"
    };

    @Qualifier("myUserDetailsService")
    @Autowired
    UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
//                .antMatchers(PERMIT_ADMIN_LIST).hasRole("ADMIN")
                .antMatchers("/login","/login/events").hasAnyRole("ADMIN","USER")
                .antMatchers(PERMIT_ALL_LIST).permitAll()
                .and().httpBasic();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
