package com.jpblancodb.users.utils

import com.jpblancodb.users.config.JwtSettings
import com.jpblancodb.users.contracts.UserRequest
import com.jpblancodb.users.entities.User
import com.jpblancodb.users.repositories.UsersRepository
import com.jpblancodb.users.services.JwtTokenService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

class UserUtils(private val usersRepository: UsersRepository, private val jwtSettings: JwtSettings) {

    fun persistUser(userRequest: UserRequest) {
        val user = User(username = userRequest.username, password = BCryptPasswordEncoder().encode(userRequest.password))
        usersRepository.save(user)
    }

    fun generateToken(user: User): String {
        val jwtTokenService = JwtTokenService(jwtSettings)

        return jwtTokenService.generateToken(user)
    }
}