package com.simplebank.service

import com.simplebank.model.User
import com.simplebank.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {

    fun createUser(username: String, email: String, isCorporate: Boolean): User {
        if(userRepository.existsByUsername(username)) {
            throw IllegalArgumentException("Username already exists!!!")
        }

        if(!email.matches(Regex("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}\$"))) {
            throw IllegalArgumentException("Invalid email format!!!")
        }

        val user = User(
            id = null,
            username = username,
            email = email,
            isCorporate = isCorporate
        )

        return userRepository.save(user)
    }

    fun getUserById(id: Long): User = userRepository.findById(id) ?: throw  NoSuchElementException("User not found!!!")

    fun getAllUsers(): List<User> = userRepository.findAll()
}