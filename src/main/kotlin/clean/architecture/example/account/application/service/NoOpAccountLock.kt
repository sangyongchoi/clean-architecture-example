package clean.architecture.example.account.application.service

import clean.architecture.example.account.application.port.out.AccountLock
import clean.architecture.example.account.domain.Account
import org.springframework.stereotype.Component

@Component
class NoOpAccountLock : AccountLock {
    override fun lockAccount(accountId: Account.AccountId) {
        // do noting
    }

    override fun releaseAccount(accountId: Account.AccountId) {
        // do noting
    }
}