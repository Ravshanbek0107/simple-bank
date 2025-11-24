package com.simplebank.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class CreateAccountRequest(

    @field:NotNull
    val userId: Long
)

data class TransactionRequest(

    @field:NotNull
    val accountId: Long,

    @field:Min(0, message = "Amount must be positive!!!")
    val amount: Double
)

data class TransferRequest(

    @field:NotNull
    val fromAccountId: Long,

    @field:NotNull
    val toAccountId: Long,

    @field:Min(0, message = "Amount must be positive!!!")
    val amount: Double
)

data class CreateUserRequest(
    @field:NotBlank(message = "Username is required!!!")
    val username: String,

    @field:Email(message = "Email should be valid!!!")
    val email: String,

    val isCorporate: Boolean = false
)