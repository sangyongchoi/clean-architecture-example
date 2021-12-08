package clean.architecture.example.account.application.port.`in`

import clean.architecture.example.account.domain.Account
import clean.architecture.example.account.domain.Money

data class SendMoneyCommand(
    val sourceAccountId: Account.AccountId,
    val targetAccountId: Account.AccountId,
    val money: Money,
) {
    init {
        require(money.isPositive) { "Money is greater than zero" }
    }
}
