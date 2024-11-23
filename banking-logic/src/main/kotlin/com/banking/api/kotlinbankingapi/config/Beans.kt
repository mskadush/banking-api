package com.banking.api.kotlinbankingapi.config

import com.banking.api.kotlinbankingapi.domain.port.out.AccountRepo
import com.banking.api.kotlinbankingapi.domain.port.out.GetProfilePort
import com.banking.api.kotlinbankingapi.domain.port.out.Notifier
import com.banking.api.kotlinbankingapi.domain.services.BankingService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Beans(
    @Value("\${bankingapi.sns.accessKeyId}")
    private val accessKeyId: String,
    @Value("\${bankingapi.sns.secretAccessKey}")
    private val secretAccessKey: String,
    @Value("\${bankingapi.sns.region}")
    private val region: String,
    @Value("\${bankingapi.sns.topic}")
    private val topic: String,
    @Value("\${bankingapi.sns.accountId}")
    private val accountId: String,
    private val notifier: Notifier,
    private val accountRepo: AccountRepo,
    private val getProfilePort: GetProfilePort,
    private val notificationScope: CoroutineScope = CoroutineScope(Dispatchers.Default),
) {


  @Bean
  fun awsSnsConfig(): AwsSnsConfig {
    return AwsSnsConfig(accessKeyId, secretAccessKey, region, topic, accountId)
  }

  @Bean
  fun bankingService() = BankingService(
       notifier = notifier,
       accountRepo = accountRepo,
       getProfilePort = getProfilePort,
       notificationScope = notificationScope,
  )
}
