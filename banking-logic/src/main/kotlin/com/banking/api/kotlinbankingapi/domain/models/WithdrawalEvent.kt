package com.banking.api.kotlinbankingapi.domain.models

data class WithdrawalEvent(
    val amount: Long,
    val accountId: String,
    val status: WithdrawalResult,
) {
  // Convert to JSON String
  fun toJson(): String {
    // TODO: this is fine for now, but as needs change, it may be necessary to use a json library
    return String.format(
        "{\"amount\":\"%s\",\"accountId\":%d,\"status\":\"%s\"}", amount, accountId, status
    )
  }

}

