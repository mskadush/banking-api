package com.banking.api.kotlinbankingapi.domain.models

sealed class AuthenticationResult(open val message: String) {
  data object Allowed : AuthenticationResult("Allowed")
  data class NotAuthorised(override val message: String) : AuthenticationResult(message)

}