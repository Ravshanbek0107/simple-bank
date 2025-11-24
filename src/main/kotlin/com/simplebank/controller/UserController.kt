package com.simplebank.controller

import com.simplebank.dto.CreateUserRequest
import com.simplebank.model.User
import com.simplebank.repository.UserRepository
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(
    private val userRepository: UserRepository
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createUser(@Valid @RequestBody request: CreateUserRequest): User {
        val user = User(
            username = request.username,
            email = request.email,
            isCorporate = request.isCorporate
        )
        return userRepository.save(user)
    }

    @GetMapping("/{id}")
    fun getUser(@PathVariable id: Long): User {
        return userRepository.findById(id) ?: throw NoSuchElementException("User not found with id: $id !!!")
    }

    @GetMapping
    fun getAllUsers(): List<User> {
        return userRepository.findAll()
    }
}
