package clean.architecture.example.account.application.port.out

import clean.architecture.example.account.domain.Account
import java.time.LocalDateTime

interface LoadAccountPort {
    fun loadAccount(accountId: Account.AccountId, baselineDate: LocalDateTime): Account
}
