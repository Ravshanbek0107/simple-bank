package com.simplebank.model


import java.math.BigDecimal
import java.time.LocalDateTime

class User(
    var id: Long? = null,
    var username: String? = null,
    var email: String? = null,
    var isCorporate: Boolean = false
){}




class Account(
    var id: Long? = null,
    var userId: Long? = null,
    var balance: BigDecimal = BigDecimal.ZERO
){}



class Transaction(
    var id: Long? = null,
    var type: TransactionType? = null,
    var accountId: Long? = null,
    var amount: BigDecimal = BigDecimal.ZERO,
    var fromAccountId: Long? = null,
    var toAccountId: Long? = null,
    var description: String? = null,
    var createdAt: LocalDateTime = LocalDateTime.now(),
    var commission: Double
){}









