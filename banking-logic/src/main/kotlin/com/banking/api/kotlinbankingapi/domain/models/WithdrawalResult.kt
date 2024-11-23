package com.banking.api.kotlinbankingapi.domain.models

sealed class WithdrawalResult(open val reason: String) {
  data object Unknown : WithdrawalResult("Invalid withdrawal state")
  data object Success : WithdrawalResult("Success")
  data class Failed(override val reason: String) : WithdrawalResult(reason = reason)
}