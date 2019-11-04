package com.jpblancodb.users.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "jwt")
data class JwtSettings(
        var secret: String = "",
        var authEndpoint: String = "",
        var expiration: Int = 10,
        val errorMessage: String = "Invalid token",
        val authorizationHeader: String = "Authorization",
        val tokenPrefix: String = "Bearer "
) {}