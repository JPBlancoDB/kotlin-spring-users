package com.jpblancodb.users.config

import com.jpblancodb.users.services.JwtTokenService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
class Container(private val jwtSettings: JwtSettings) {

    @Bean
    fun bCryptEncoder() = BCryptPasswordEncoder()

    @Bean
    fun jwtTokenService() = JwtTokenService(jwtSettings)

}