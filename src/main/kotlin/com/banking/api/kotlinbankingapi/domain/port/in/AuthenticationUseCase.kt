package com.banking.api.kotlinbankingapi.domain.port.`in`

interface AuthenticationUseCase {
  fun authenticate(accountId: String, password: String): AuthenticationResult
}

