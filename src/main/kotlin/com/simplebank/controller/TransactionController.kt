package com.simplebank.controller

import com.simplebank.dto.TransactionRequest
import com.simplebank.dto.TransferRequest
import com.simplebank.service.TransactionService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/transactions")
class TransactionController(
    private val transactionService: TransactionService
) {

    @PostMapping("/deposit")
    @ResponseStatus(HttpStatus.CREATED)
    fun deposit(@Valid @RequestBody request: TransactionRequest) = transactionService.deposit(request.accountId, request.amount)

    @PostMapping("/withdraw")
    @ResponseStatus(HttpStatus.CREATED)
    fun withdraw(@Valid @RequestBody request: TransactionRequest) = transactionService.withdraw(request.accountId, request.amount)

    @PostMapping("/transfer")
    @ResponseStatus(HttpStatus.CREATED)
    fun transfer(@Valid @RequestBody request: TransferRequest) = transactionService.transfer(request.fromAccountId, request.toAccountId, request.amount)
}