package com.simplebank.repository

import com.simplebank.model.Account
import org.springframework.stereotype.Repository

@Repository
class AccountRepository {

    private val accounts = mutableMapOf<Long, Account>()
    private var idCount = 1L

    fun save(account: Account): Account {
        if (account.id == null) {
            account.id = idCount++
        }

        accounts[account.id!!] = account
        return account
    }

    fun findById(id: Long): Account? = accounts[id]

    fun findByUserId(userId: Long): List<Account> = accounts.values.filter { it.userId == userId }
}