package com.jpblancodb.users

import com.fasterxml.jackson.databind.ObjectMapper
import com.jpblancodb.users.config.JwtSettings
import com.jpblancodb.users.contracts.UserRequest
import com.jpblancodb.users.entities.User
import com.jpblancodb.users.repositories.UsersRepository
import com.jpblancodb.users.utils.UserUtils
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


@SpringBootTest
@AutoConfigureMockMvc
class UsersTests {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Autowired
    lateinit var usersRepository: UsersRepository

    @Autowired
    lateinit var jwtSettings: JwtSettings

    @Test
    fun `should return list of users when retrieving all`() {
        val userUtils = UserUtils(usersRepository, jwtSettings)
        val user = UserRequest(username = "user", password = "password")
        userUtils.persistUser(user)
        val token = userUtils.generateToken(User(username = user.username, password = BCryptPasswordEncoder().encode(user.password)))

        mockMvc
                .perform(get("/api/users")
                        .header(jwtSettings.authorizationHeader, jwtSettings.tokenPrefix + token))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.[0].username").value(user.username))
                .andExpect(jsonPath("$.[0].password").doesNotExist())
                .andExpect(jsonPath("$.[0].dateCreated").exists())
    }

    @Test
    fun `should return 200 and UserResponse when creating a user`() {
        val userUtils = UserUtils(usersRepository, jwtSettings)
        val user = UserRequest(username = "user", password = "password")
        userUtils.persistUser(user)
        val token = userUtils.generateToken(User(username = user.username, password = BCryptPasswordEncoder().encode(user.password)))

        val userRequest = UserRequest("testUser", password = "123456")

        mockMvc
                .perform(post("/api/users")
                        .header(jwtSettings.authorizationHeader, jwtSettings.tokenPrefix + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.username").value(userRequest.username))
                .andExpect(jsonPath("$.password").doesNotExist())
                .andExpect(jsonPath("$.dateCreated").exists())
    }
}