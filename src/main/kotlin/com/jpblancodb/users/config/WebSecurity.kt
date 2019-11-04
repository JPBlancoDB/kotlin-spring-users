package com.jpblancodb.users.config

import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.security.web.authentication.HttpStatusEntryPoint
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Configuration
@EnableWebSecurity
class WebSecurity(private val jwtSettings: JwtSettings) : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(jwtSettings.authEndpoint).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(AuthenticationFilter(authenticationManager(), jwtSettings))
    }
}
