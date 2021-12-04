package clean.architecture.example.account.domain

import java.time.LocalDateTime


class Account(
    private val id: AccountId?,
    private val baselineBalance: Money,
    private val activityWindow: ActivityWindow,
) {
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

    fun getId() = id

    private fun calculateBalance(): Money {
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
        val value: String,
    )
}