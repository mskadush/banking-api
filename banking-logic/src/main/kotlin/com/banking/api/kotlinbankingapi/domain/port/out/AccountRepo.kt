package com.banking.api.kotlinbankingapi.domain.port.out

import com.banking.api.kotlinbankingapi.domain.models.Account

interface AccountRepo {
  // TODO: 
  // fun <T> withLock(accoutId: String, block: (Account) -> T): T 
  fun getAccount(accountId: String): Account?
  fun saveBalance(accountId: String, balance: Account): Account
}
