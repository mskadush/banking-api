package com.banking.api.kotlinbankingapi.domain.services

import com.banking.api.kotlinbankingapi.domain.models.WithdrawalEvent
import com.banking.api.kotlinbankingapi.domain.models.WithdrawalResponse
import com.banking.api.kotlinbankingapi.domain.models.WithdrawalResult.Failed
import com.banking.api.kotlinbankingapi.domain.models.WithdrawalResult.Success
import com.banking.api.kotlinbankingapi.domain.port.`in`.WithdrawalUseCase
import com.banking.api.kotlinbankingapi.domain.port.out.AccountRepo
import com.banking.api.kotlinbankingapi.domain.port.out.GetProfilePort
import com.banking.api.kotlinbankingapi.domain.port.out.Notifier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import mu.KotlinLogging

class BankingService(
    private val notifier: Notifier,
    private val accountRepo: AccountRepo,
    private val getProfilePort: GetProfilePort,
    private val notificationScope: CoroutineScope = CoroutineScope(Dispatchers.Default),
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
    val result = runWithBackoff(undo = {
      logger.error(it) { "Error while updating account" }
      accountRepo.saveBalance(
          event.accountId, account.copy(balance = account.balance)
      )
      WithdrawalResponse(account, Failed("Error while updating account"))
    }) {

      WithdrawalResponse(accountRepo.saveBalance(
          event.accountId, account.copy(balance = account.balance - event.amount)
      ), Success)
    }
    logger.debug { "Balance updated. Notifying user." }
    // notify users async
    notificationScope.launch {
      // TODO: implement
      notifier.notify(profile.phoneNumber, event.toJson())
      logger.debug { "User notification sent." }
    }
    logger.debug { "User notification triggered. Returning" }
    return result
  }

  private inline  fun <T> runWithBackoff(retries: Int = 3, crossinline undo: (Throwable) -> T, block: () -> T): T {
    return try { // TODO: fancy backoff strategies before running undo action
      // for (i in 0..retries) {
        return block()
      // }
    } catch (ex: Throwable) {
      undo(ex)
    }
  }
}
