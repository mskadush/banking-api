package com.banking.api.kotlinbankingapi.adapters.`in`.web

import com.banking.api.kotlinbankingapi.domain.models.WithdrawalEvent
import com.banking.api.kotlinbankingapi.domain.models.WithdrawalResult.Success
import com.banking.api.kotlinbankingapi.domain.models.WithdrawalResult.Unknown
import com.banking.api.kotlinbankingapi.domain.port.`in`.AuthenticationUseCase
import com.banking.api.kotlinbankingapi.domain.port.`in`.WithdrawalUseCase
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal


@RestController
@RequestMapping("/bank")
class BankAccountController(
    private val withdrawalUseCase: WithdrawalUseCase,
    private val authenticationUseCase: AuthenticationUseCase,
) {

  @PostMapping("/withdraw")
  fun withdraw(
      @RequestParam("accountId") accountId: Long, @RequestParam("amount") amount: BigDecimal?,
  ): String {

    if (amount == null) {
      return "Insufficient funds for withdrawal"
    }
    val longAmount = amount.multiply(100.toBigDecimal()).toLong()
    // TODO: authenticationUseCase.authenticate(accountId = accountId.toString(), )
    val response = withdrawalUseCase.withdraw(
        WithdrawalEvent(amount = longAmount, accountId = accountId.toString(), status = Unknown)
    )
    return when (response.result) {
      Success -> "Withdrawal successful"
      else -> "Withdrawal failed"
    }
  }
}