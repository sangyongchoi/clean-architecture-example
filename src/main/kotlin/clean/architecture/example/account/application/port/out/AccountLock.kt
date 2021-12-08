package clean.architecture.example.account.application.port.out

import clean.architecture.example.account.domain.Account

interface AccountLock {
    fun lockAccount(accountId: Account.AccountId)

    fun releaseAccount(accountId: Account.AccountId)
}
