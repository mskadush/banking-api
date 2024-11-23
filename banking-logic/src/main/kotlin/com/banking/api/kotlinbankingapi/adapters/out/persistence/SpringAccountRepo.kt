package com.banking.api.kotlinbankingapi.adapters.out.persistence

import com.banking.api.kotlinbankingapi.domain.models.Account
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.Repository

@org.springframework.stereotype.Repository
interface SpringAccountRepo: JpaRepository<AccountEntity, Long> {
}