package com.banking.api.kotlinbankingapi.adapters.out.persistence

import com.banking.api.kotlinbankingapi.domain.models.Account
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
data class AccountEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val balance: Long,
    val ownerId: String,
) {
  fun toAccount() = Account(
      accountId = id.toString(),
      balance = balance,
      ownerId = ownerId,
  )

  companion object {
    fun Account.toEntity() = AccountEntity(
        id = accountId.toLong(),
        balance = balance,
        ownerId = ownerId,
    )
  }
}
