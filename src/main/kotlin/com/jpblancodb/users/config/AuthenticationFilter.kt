package com.jpblancodb.users.config

import io.jsonwebtoken.Jwts
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthenticationFilter(authenticationManager: AuthenticationManager?, private val jwtSettings: JwtSettings) : BasicAuthenticationFilter(authenticationManager) {
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        if(isAuthEndpoint(request, jwtSettings)){
            filterChain.doFilter(request, response)
            return
        }

        try {
            val token = request.getHeader(jwtSettings.authorizationHeader).replace(jwtSettings.tokenPrefix, "")
            val parsedToken = Jwts.parser()
                    .setSigningKey(jwtSettings.secret)
                    .parseClaimsJws(token)
            val username = parsedToken.body.subject

            SecurityContextHolder.getContext().authentication = UsernamePasswordAuthenticationToken(username, null, null)
            filterChain.doFilter(request, response)
        } catch (exception: Exception) {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), jwtSettings.errorMessage)
        }
    }

    private fun isAuthEndpoint(request: HttpServletRequest, jwtSettings: JwtSettings): Boolean = request.requestURI == jwtSettings.authEndpoint
}