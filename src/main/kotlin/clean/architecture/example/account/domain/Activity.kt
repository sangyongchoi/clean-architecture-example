package clean.architecture.example.account.domain

import java.time.LocalDateTime

class Activity(
    var id: ActivityId? = null,
    val ownerAccountId: Account.AccountId?,
    val sourceAccountId: Account.AccountId?,
    val targetAccountId: Account.AccountId?,
    val timestamp: LocalDateTime,
    val money: Money,
) {


    data class ActivityId(
        val value: Long? = null
    )
}