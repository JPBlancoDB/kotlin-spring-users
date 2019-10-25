package com.jpblancodb.users.controllers

import com.jpblancodb.users.contracts.UserRequest
import com.jpblancodb.users.contracts.UserResponse
import com.jpblancodb.users.entities.User
import com.jpblancodb.users.repositories.UsersRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UsersController(private val usersRepository: UsersRepository) {

    @GetMapping
    fun getUsers(): List<UserResponse> {
        val users = usersRepository.findAll()

        return users.map { mapToUserResponse(it) }
    }

    @PostMapping
    fun createUser(@RequestBody userRequest: UserRequest): UserResponse {
        val user = User(username = userRequest.username, password = BCryptPasswordEncoder().encode(userRequest.password))
        usersRepository.save(user)

        return mapToUserResponse(user)
    }

    private fun mapToUserResponse(user: User) = UserResponse(user.id!!, user.username, user.dateCreated!!)
}