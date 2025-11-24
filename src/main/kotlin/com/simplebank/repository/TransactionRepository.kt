package com.simplebank.repository

import com.simplebank.model.Transaction
import org.springframework.stereotype.Repository

@Repository
class TransactionRepository {

    private val transactions = mutableMapOf<Long, Transaction>()
    private var idCount = 1L

    fun save(transaction: Transaction): Transaction {
        if (transaction.id == null) {
            transaction.id = idCount++
        }

        transactions[transaction.id!!] = transaction
        return transaction
    }

    fun findAll(): List<Transaction> = transactions.values.toList()
}