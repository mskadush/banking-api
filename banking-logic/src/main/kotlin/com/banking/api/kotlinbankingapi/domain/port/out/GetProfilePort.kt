package com.banking.api.kotlinbankingapi.domain.port.out

import com.banking.api.kotlinbankingapi.domain.models.UserProfile

interface GetProfilePort {
  fun getProfile(username: String): UserProfile?
}
