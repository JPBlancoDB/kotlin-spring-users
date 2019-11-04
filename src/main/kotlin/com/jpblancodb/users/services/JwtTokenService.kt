package com.jpblancodb.users.services

import com.jpblancodb.users.config.JwtSettings
import com.jpblancodb.users.entities.User
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import java.time.Instant.now
import java.util.*

class JwtTokenService(private val jwtSettings: JwtSettings) {

    fun generateToken(user: User): String {
        val date = Date()
        val expireDate = addHoursToDate(date, jwtSettings.expiration)

        return Jwts.builder()
                .setSubject(user.username)
                .setIssuedAt(date)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, jwtSettings.secret)
                .compact()
    }

    private fun addHoursToDate(date: Date, hours: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.add(Calendar.HOUR, hours)

        return calendar.time
    }
}


