package com.banking.api.kotlinbankingapi.adapters.out

import com.banking.api.kotlinbankingapi.config.AwsSnsConfig
import com.banking.api.kotlinbankingapi.domain.port.out.Notifier
import org.springframework.stereotype.Component
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.sns.SnsClient
import software.amazon.awssdk.services.sns.model.PublishRequest
import software.amazon.awssdk.services.sns.model.PublishRequest.Builder

@Component
class SnsSmsNotifier(
    awsSnsConfig: AwsSnsConfig,
) : Notifier {

  private val publishRequest: Builder = PublishRequest.builder()
      .topicArn(
          "arn:aws:sns:${awsSnsConfig.region}:${awsSnsConfig.accountId}:${awsSnsConfig.topic}"
      )
  private val snsClient: SnsClient = SnsClient.builder()
      .region(Region.of(awsSnsConfig.region))
      .credentialsProvider(
          StaticCredentialsProvider.create(
              AwsBasicCredentials.create(awsSnsConfig.accessKeyId, awsSnsConfig.secretAccessKey)
          )
      )
      .build()


  override fun notify(phoneNumber: String, message: String): Boolean {
    snsClient.publish(publishRequest.message(message).build())
    return true
  }
}