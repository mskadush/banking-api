package com.banking.api.kotlinbankingapi.domain.port.out

interface Notifier {
  fun notify(phoneNumber: String, message: String): Boolean
}
