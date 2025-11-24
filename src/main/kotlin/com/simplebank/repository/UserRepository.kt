package com.simplebank.repository

import com.simplebank.model.User
import org.springframework.stereotype.Repository


@Repository
class UserRepository {

    private val users = mutableMapOf<Long, User>()
    private var idCount = 1L

    fun save(user: User): User {
        if (user.id == null) {
            user.id = idCount++
        }
        users[user.id!!] = user
        return user
    }

    fun findById(id: Long?): User? = users[id]

    fun findAll(): List<User> = users.values.toList()

    fun existsByUsername(username: String): Boolean = users.values.any { it.username == username }
}