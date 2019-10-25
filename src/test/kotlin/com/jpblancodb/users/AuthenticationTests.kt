package com.jpblancodb.users

import com.fasterxml.jackson.databind.ObjectMapper
import com.jpblancodb.users.entities.User
import com.jpblancodb.users.repositories.UsersRepository
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@SpringBootTest
@AutoConfigureMockMvc
class AuthenticationTests {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Autowired
    lateinit var usersRepository: UsersRepository

    @Test
    fun `should throw 401 when is not authenticated`() {
        mockMvc
                .perform(get("/api/private"))
                .andExpect(status().isUnauthorized)
                .andExpect(content().string(""))
    }

    @Test
    fun `should return 200 when route is not protected`() {
        val user = User(username = "user", password = "password")
        usersRepository.save(user)

        mockMvc
                .perform(get("/api/users"))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.[0].username").value(user.username))
                .andExpect(jsonPath("$.[0].id").value(1))
    }
}