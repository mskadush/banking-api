package com.banking.api.kotlinbankingapi.domain.port.`in`

import com.banking.api.kotlinbankingapi.domain.models.WithdrawalEvent
import com.banking.api.kotlinbankingapi.domain.models.WithdrawalResponse

interface WithdrawalUseCase {
  fun withdraw(event: WithdrawalEvent): WithdrawalResponse
}
