package com.simplebank.controller

import com.simplebank.dto.CreateAccountRequest
import com.simplebank.service.AccountService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/accounts")
class AccountController(
    private val accountService: AccountService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createAccount(@Valid @RequestBody request: CreateAccountRequest) = accountService.createAccount(request.userId)

    @GetMapping("/{accountId}/balance")
    fun getBalance(@PathVariable accountId: Long) = accountService.getBalance(accountId)
}