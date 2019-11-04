package com.jpblancodb.users.controllers

import com.jpblancodb.users.contracts.UserRequest
import com.jpblancodb.users.repositories.UsersRepository
import com.jpblancodb.users.services.JwtTokenService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.HttpClientErrorException

@RestController
@RequestMapping("/api/auth")
class AuthController(
        private val usersRepository: UsersRepository,
        private val bCryptPasswordEncoder: BCryptPasswordEncoder,
        private val jwtTokenService: JwtTokenService
) {

    @PostMapping
    fun authenticate(@RequestBody userRequest: UserRequest): ResponseEntity<String> {
        val user = usersRepository.findUserByUsername(userRequest.username)

        if(bCryptPasswordEncoder.matches(userRequest.password, user?.password)){
            return ResponseEntity.ok(jwtTokenService.generateToken(user!!))
        }

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body("Invalid username and/or password")
    }
}