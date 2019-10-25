package com.jpblancodb.users

import com.fasterxml.jackson.databind.ObjectMapper
import com.jpblancodb.users.contracts.UserRequest
import com.jpblancodb.users.entities.User
import com.jpblancodb.users.repositories.UsersRepository
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
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

    @Test
    fun `should return list of users when retrieving all`() {
        val user = User(username = "user", password = "password")
        usersRepository.save(user)

        mockMvc
                .perform(get("/api/users"))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.[0].username").value(user.username))
                .andExpect(jsonPath("$.[0].password").doesNotExist())
                .andExpect(jsonPath("$.[0].dateCreated").exists())
    }

    @Test
    fun `should return 200 and UserResponse when creating a user`() {
        val userRequest = UserRequest("testUser", password = "123456")

        mockMvc
                .perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.username").value(userRequest.username))
                .andExpect(jsonPath("$.password").doesNotExist())
                .andExpect(jsonPath("$.dateCreated").exists())
    }
}