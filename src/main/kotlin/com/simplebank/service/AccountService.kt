package com.simplebank.service

import com.simplebank.model.Account
import com.simplebank.repository.AccountRepository
import com.simplebank.repository.UserRepository
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class AccountService(
    private val accountRepository: AccountRepository,
    private val userRepository: UserRepository
) {

    fun createAccount(userId: Long): Account {

        val user = userRepository.findById(userId) ?: throw NoSuchElementException("User not found!!!")

        val maxAccounts = if(user.isCorporate) 10 else 5
        val currentAccounts = accountRepository.findByUserId(userId).size
        if(currentAccounts >= maxAccounts) {
            throw IllegalStateException("Account limit reached for this user!!!")
        }

        val account = Account(
            id = null,
            userId = userId,
            balance = BigDecimal.valueOf(0.0)
        )
        return accountRepository.save(account)
    }

    fun getBalance(accountId: Long): BigDecimal {

        val account = accountRepository.findById(accountId) ?: throw NoSuchElementException("Account not found!!!")
        return account.balance
    }
}
