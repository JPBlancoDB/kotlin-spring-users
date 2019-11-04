package com.jpblancodb.users

import com.fasterxml.jackson.databind.ObjectMapper
import com.jpblancodb.users.config.JwtSettings
import com.jpblancodb.users.contracts.UserRequest
import com.jpblancodb.users.repositories.UsersRepository
import com.jpblancodb.users.utils.UserUtils
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class AuthenticationTests {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Autowired
    lateinit var usersRepository: UsersRepository

    @Autowired
    lateinit var jwtSettings: JwtSettings

    @Test
    fun `should throw 401 when is not authenticated`() {
        mockMvc
                .perform(get("/api/users"))
                .andExpect(status().isUnauthorized)
    }

    @Test
    fun `should return token when authenticating`() {
        val userRequest = UserRequest("user", "test")
        UserUtils(usersRepository, jwtSettings).persistUser(userRequest)

        mockMvc
                .perform(post("/api/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isOk)
    }

    @Test
    fun `should return 401 when invalid credentials`() {
        val userRequest = UserRequest("user", "test")

        mockMvc
                .perform(post("/api/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isUnauthorized)
                .andExpect(content().string("Invalid username and/or password"))
    }
}