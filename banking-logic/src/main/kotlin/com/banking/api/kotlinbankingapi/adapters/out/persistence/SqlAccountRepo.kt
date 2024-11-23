package com.banking.api.kotlinbankingapi.adapters.out.persistence

import com.banking.api.kotlinbankingapi.adapters.out.persistence.AccountEntity.Companion.toEntity
import com.banking.api.kotlinbankingapi.domain.models.Account
import com.banking.api.kotlinbankingapi.domain.port.out.AccountRepo
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class SqlAccountRepo(
    private val springAccountRepo: SpringAccountRepo,
) : AccountRepo {
  @Autowired
  private lateinit var request: HttpServletRequest
  override fun getAccount(accountId: String): Account? {
    // error if not found
    // TODO: handle these errors better
   return springAccountRepo.findById(accountId.toLong()).get().toAccount()
  }

  override fun saveBalance(accountId: String, balance: Account): Account {
    return springAccountRepo.save(balance.copy(accountId = accountId).toEntity(request.getHeader("x-user-id"))).toAccount()
  }
}