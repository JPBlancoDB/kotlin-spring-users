package com.jpblancodb.users.entities

import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
@Table(name = "users")
data class User(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int? = null,

        @NotBlank
        val username: String,

        @NotBlank
        val password: String,

        @Column(name = "date_created")
        val dateCreated: Date? = Date()
)