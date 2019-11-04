package com.jpblancodb.users.repositories

import com.jpblancodb.users.entities.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UsersRepository : JpaRepository<User, Int> {
    fun findUserByUsername(username: String): User?
}