package com.simplebank.service

import com.simplebank.model.Transaction
import com.simplebank.model.TransactionType
import com.simplebank.repository.AccountRepository
import com.simplebank.repository.TransactionRepository
import com.simplebank.repository.UserRepository
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.LocalDateTime

@Service
class TransactionService(
    private val accountRepository: AccountRepository,
    private val transactionRepository: TransactionRepository,
    private val userRepository: UserRepository
) {

    fun deposit(accountId: Long, amount: Double): Transaction {
        if (amount <= 0) throw IllegalArgumentException("Amount must be positive!!!")

        val account = accountRepository.findById(accountId) ?: throw NoSuchElementException("Account not found!!!")
        account.balance += BigDecimal.valueOf(amount)
        accountRepository.save(account)

        val transaction = Transaction(
            type = TransactionType.DEPOSIT,
            accountId = accountId,
            amount = BigDecimal.valueOf(amount),
            createdAt = LocalDateTime.now(),
            commission = 0.0
        )
        return transactionRepository.save(transaction)
    }

    fun withdraw(accountId: Long, amount: Double): Transaction {
        if (amount <= 0) throw IllegalArgumentException("Amount must be positive!!!")

        val account = accountRepository.findById(accountId) ?: throw NoSuchElementException("Account not found!!!")
        if (account.balance < BigDecimal.valueOf(amount)) throw IllegalStateException("Balance not enough!!!")

        account.balance -= BigDecimal.valueOf(amount)
        accountRepository.save(account)

        // bu yerda pulni qayerga saqlashni bilmadm transferga oxshab qolardi unda pulni shunchaki yoq qildim

        val transaction = Transaction(
            type = TransactionType.WITHDRAW,
            accountId = accountId,
            amount = BigDecimal.valueOf(amount),
            fromAccountId = accountId,
            createdAt = LocalDateTime.now(),
            commission = 0.0
        )
        return transactionRepository.save(transaction)
    }

    fun transfer(fromAccountId: Long, toAccountId: Long, amount: Double): Transaction {
        if (amount <= 0) throw IllegalArgumentException("Amount must be positive!!!")

        val fromAccount = accountRepository.findById(fromAccountId) ?: throw NoSuchElementException("From account not found!!!")
        val toAccount = accountRepository.findById(toAccountId) ?: throw NoSuchElementException("To account not found!!!")

        val toUser = userRepository.findById(toAccount.userId ?: throw NoSuchElementException("To user not found!!!")) ?: throw NoSuchElementException("To user not found!!!")
        val commission = if (toUser.isCorporate) amount * 0.02 else 0.0 // 2% comission for corporate
        val totalAmount = BigDecimal.valueOf(amount + commission)

        if (fromAccount.balance < totalAmount) throw IllegalStateException("Balance not enough for commission!!!")

        fromAccount.balance -= totalAmount
        toAccount.balance += BigDecimal.valueOf(amount)

        accountRepository.save(fromAccount)
        accountRepository.save(toAccount)

        val transaction = Transaction(
            type = TransactionType.TRANSFER,
            fromAccountId = fromAccountId,
            toAccountId = toAccountId,
            accountId = null,
            amount = BigDecimal.valueOf(amount),
            commission = commission,
            createdAt = LocalDateTime.now()
        )
        return transactionRepository.save(transaction)
    }
}
