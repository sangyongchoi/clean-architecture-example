package clean.architecture.example.account.domain

import java.time.LocalDateTime


class Account(
    val id: AccountId?,
    val baselineBalance: Money,
    val activityWindow: ActivityWindow,
) {

    fun calculateBalance(): Money {
        return Money.add(
            baselineBalance,
            activityWindow.calculateBalance(id)
        )
    }

    fun withdraw(money: Money, targetAccountId: AccountId?): Boolean {
        if (!mayWithdraw(money)) {
            return false
        }
        val withdrawal = Activity(
            null,
            id,
            id,
            targetAccountId!!,
            LocalDateTime.now(),
            money
        )
        activityWindow.addActivity(withdrawal)
        return true
    }

    private fun mayWithdraw(money: Money): Boolean {
        return Money
            .add(calculateBalance(), money.negate())
            .isPositiveOrZero
    }

    fun deposit(money: Money?, sourceAccountId: AccountId?): Boolean {
        val deposit = Activity(
            null,
            id,
            sourceAccountId!!,
            id,
            LocalDateTime.now(),
            money!!
        )
        activityWindow.addActivity(deposit)

        return true
    }

    data class AccountId(
        val value: Long,
    )

    companion object {
        fun withoutId(
            baselineBalance: Money?,
            activityWindow: ActivityWindow?
        ): Account {
            return Account(null, baselineBalance!!, activityWindow!!)
        }

        fun withId(
            accountId: AccountId?,
            baselineBalance: Money?,
            activityWindow: ActivityWindow?
        ): Account {
            return Account(accountId!!, baselineBalance!!, activityWindow!!)
        }
    }
}
