package com.jpblancodb.users.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/users")
class UsersController {
    @GetMapping
    fun getUsers() = ArrayList<String>()
}