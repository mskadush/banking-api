package com.banking.api.kotlinbankingapi.domain.services

import com.banking.api.kotlinbankingapi.domain.port.out.AccountRepo
import com.banking.api.kotlinbankingapi.domain.models.AuthenticationResult
import com.banking.api.kotlinbankingapi.domain.models.AuthenticationResult.Allowed
import com.banking.api.kotlinbankingapi.domain.models.AuthenticationResult.NotAuthorised
import com.banking.api.kotlinbankingapi.domain.port.`in`.AuthenticationUseCase
import com.banking.api.kotlinbankingapi.domain.port.out.GetProfilePort
import mu.KotlinLogging
import org.springframework.stereotype.Component

@Component
class AuthenticationService(
    private val accountRepo: AccountRepo,
    private val getProfilePort: GetProfilePort,
) : AuthenticationUseCase {
  private val logger = KotlinLogging.logger {}
  override fun authenticate(accountId: String, password: String): AuthenticationResult {
    logger.debug { "Getting account with id: $accountId" }
    // pull account
    val account = accountRepo.getAccount(accountId) ?: return NotAuthorised("Account not found")
    logger.debug { "Account found. Getting profile" }
    // confirm password
    val profile = getProfilePort.getProfile(account.ownerId) ?: return NotAuthorised("Profile not found")
    logger.debug { "Profile found." }
    // assume password allows withdrawal if matched
    val userOwnsAccount = profile.accountIds.contains(accountId)
    logger.debug { "User owns account: $userOwnsAccount" }
    val passwordValid = profile.password == password
    logger.debug { "Password valid: $passwordValid" }
    if (userOwnsAccount && passwordValid) {
      return NotAuthorised("Not authorised")
    }
    return Allowed
  }
}