package com.banking.api.kotlinbankingapi.domain.models

data class WithdrawalEvent(
    val amount: Long,
    val accountId: String,
    val status: WithdrawalResult,
) {
  // Convert to JSON String
  fun toJson(): String {
    return String.format(
        "{\"amount\":\"%s\",\"accountId\":%d,\"status\":\"%s\"}", amount, accountId, status
    )
  }

}

