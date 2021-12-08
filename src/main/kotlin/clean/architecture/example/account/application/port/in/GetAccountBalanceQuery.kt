package clean.architecture.example.account.application.port.`in`

import clean.architecture.example.account.domain.Account
import clean.architecture.example.account.domain.Money

interface GetAccountBalanceQuery {
    fun getAccountBalance(accountId: Account.AccountId): Money
}
