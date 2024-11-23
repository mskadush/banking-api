package com.banking.api.kotlinbankingapi.adapters.out.persistence

import com.banking.api.kotlinbankingapi.domain.models.Account
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDateTime

@Entity
data class AccountEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val balance: Long,
    val ownerId: String,
    val lastUpdatedBy: String,
    val lastUpdatedTime: String,
) {
  fun toAccount() = Account(
      accountId = id.toString(),
      balance = balance,
      ownerId = ownerId,
  )

  companion object {
    fun Account.toEntity(lastUpdatedBy: String) = AccountEntity(
        id = accountId.toLong(),
        balance = balance,
        ownerId = ownerId,
        lastUpdatedBy = lastUpdatedBy,
        lastUpdatedTime = LocalDateTime.now().toString(),
    )
  }
}
