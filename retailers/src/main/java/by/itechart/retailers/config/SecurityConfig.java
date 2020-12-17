package by.itechart.retailers.config;

import by.itechart.retailers.security.jwt.JwtConfigurer;
import by.itechart.retailers.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.CorsConfiguration;

import static by.itechart.retailers.constant.RoleConstant.*;
import static by.itechart.retailers.constant.UrlConstants.*;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                .disable()
                .cors()
                .configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues())
                .and()
                .csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(URL_API + URL_LOGIN_ENDPOINT)
                .permitAll()
                .antMatchers(URL_API + URL_LOGOUT_ENDPOINT)
                .hasAnyAuthority(ROLE_SYSTEM_ADMIN, ROLE_DIRECTOR, ROLE_WAREHOUSE_MANAGER, ROLE_SHOP_MANAGER, ROLE_DISPATCHER, ROLE_ADMIN)
                .antMatchers(URL_API + URL_BILLS + URL_ALL)
                .hasAnyAuthority(ROLE_DIRECTOR, ROLE_SHOP_MANAGER)
                .antMatchers(URL_API + URL_CATEGORIES + URL_ALL)
                .hasAnyAuthority(ROLE_DIRECTOR, ROLE_DISPATCHER)
                .antMatchers(URL_API + URL_CUSTOMERS + URL_ALL)
                .hasAuthority(ROLE_SYSTEM_ADMIN)
                .antMatchers(URL_API + URL_INNER_APPLICATIONS + URL_ALL)
                .hasAnyAuthority(ROLE_WAREHOUSE_MANAGER, ROLE_SHOP_MANAGER)
                .antMatchers(URL_API + URL_LOCATIONS + URL_ALL)
                .hasAnyAuthority(ROLE_DIRECTOR, ROLE_WAREHOUSE_MANAGER, ROLE_SHOP_MANAGER, ROLE_DISPATCHER, ROLE_ADMIN)
                .antMatchers(URL_API + URL_LOCATION_PRODUCTS + URL_ALL)
                .hasAnyAuthority(ROLE_DISPATCHER, ROLE_WAREHOUSE_MANAGER, ROLE_SHOP_MANAGER)
                .antMatchers(URL_API + URL_PRODUCTS + URL_ALL)
                .hasAuthority(ROLE_DISPATCHER)
                .antMatchers(URL_API + URL_STATES + URL_ALL)
                .hasAnyAuthority(ROLE_SYSTEM_ADMIN, ROLE_DIRECTOR, ROLE_WAREHOUSE_MANAGER, ROLE_SHOP_MANAGER, ROLE_DISPATCHER, ROLE_ADMIN)
                .antMatchers(URL_API + URL_SUPPLIER_APPLICATIONS + URL_ALL)
                .hasAuthority(ROLE_DISPATCHER)
                .antMatchers(URL_API + URL_SUPPLIERS + URL_ALL)
                .hasAnyAuthority(ROLE_DISPATCHER, ROLE_ADMIN)
                .antMatchers(URL_API + URL_SUPPLIER_WAREHOUSES + URL_ALL)
                .hasAnyAuthority(ROLE_DISPATCHER, ROLE_DIRECTOR)
                .antMatchers(URL_API + URL_USERS + URL_ALL)
                .hasAnyAuthority(ROLE_SYSTEM_ADMIN, ROLE_DIRECTOR, ROLE_WAREHOUSE_MANAGER, ROLE_SHOP_MANAGER, ROLE_DISPATCHER, ROLE_ADMIN)
                .antMatchers(URL_API + URL_WRITE_OFF_ACTS + URL_ALL)
                .hasAnyAuthority(ROLE_DIRECTOR, ROLE_WAREHOUSE_MANAGER, ROLE_SHOP_MANAGER, ROLE_DISPATCHER)
                .anyRequest()
                .authenticated()
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider));
    }
}

