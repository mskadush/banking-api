package com.banking.api.kotlinbankingapi.domain.models

data class UserProfile(val username: String, val password: String, val phoneNumber: String, val accountIds: List<String>)