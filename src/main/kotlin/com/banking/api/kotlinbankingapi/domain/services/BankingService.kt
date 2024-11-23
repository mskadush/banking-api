package com.banking.api.kotlinbankingapi.domain.services

import com.banking.api.kotlinbankingapi.domain.port.out.AccountRepo
import com.banking.api.kotlinbankingapi.domain.port.out.GetProfilePort
import com.banking.api.kotlinbankingapi.domain.models.WithdrawalEvent
import com.banking.api.kotlinbankingapi.domain.models.WithdrawalResponse
import com.banking.api.kotlinbankingapi.domain.models.WithdrawalResult.Failed
import com.banking.api.kotlinbankingapi.domain.models.WithdrawalResult.Success
import com.banking.api.kotlinbankingapi.domain.port.`in`.WithdrawalUseCase
import com.banking.api.kotlinbankingapi.domain.port.out.Notifier
import mu.KotlinLogging
import org.springframework.stereotype.Component

@Component
class BankingService(
    private val notifier: Notifier,
    private val accountRepo: AccountRepo,
    private val getProfilePort: GetProfilePort,
) : WithdrawalUseCase {

  private val logger = KotlinLogging.logger {}
  override fun withdraw(event: WithdrawalEvent): WithdrawalResponse {
    // get account
    logger.debug { "Getting account with id ${event.accountId}" }
    val account = accountRepo.getAccount(event.accountId)
        ?: return WithdrawalResponse(null, Failed("Account not found"))
    logger.debug { "Account found. Getting profile for account" }

    val profile = getProfilePort.getProfile(event.accountId) ?: return WithdrawalResponse(
        null, Failed("No account found")
    )

    logger.debug { "Profile found. Checking balance." }
    // validate owner
    // validate balance and amount
    if (account.balance - event.amount < 0) { // assume overdraft not allowed
      return WithdrawalResponse(account, Failed("Invalid balance for transaction"))
    }
    logger.debug { "Balance valid. Updating balance." }
    // validate update balance
    val result = accountRepo.saveBalance(
        event.accountId, account.copy(balance = account.balance - event.amount)
    )
    logger.debug { "Balance updated. Notifying user." }
    // notify users async
    notifier.notify(profile.phoneNumber, "Withdrawal successful")
    logger.debug { "User notified. Returning" }
    // return result
    return WithdrawalResponse(result, Success)
  }
}
