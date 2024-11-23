package com.banking.api.kotlinbankingapi.domain.port.out

import com.banking.api.kotlinbankingapi.domain.models.Account

interface AccountRepo {
  fun getAccount(accountId: String): Account?
  fun saveBalance(accountId: String, balance: Account): Account
}
