package clean.architecture.example.account.domain

import java.time.LocalDateTime
import java.util.*

class ActivityWindow(
    private var activities: MutableList<Activity>
) {
    val startTimestamp: LocalDateTime
        get() = activities.stream()
            .min { activity, activity2 -> activity.timestamp.compareTo(activity2.timestamp) }
            .get().timestamp


    val endTimestamp: LocalDateTime
        get() {
            return activities.stream()
                .max { activity, activity2 -> activity.timestamp.compareTo(activity2.timestamp) }
                .get().timestamp
        }

    fun calculateBalance(accountId: Account.AccountId?): Money {
        val depositBalance: Money = activities.stream()
            .filter { it.targetAccountId == accountId }
            .map { it.money }
            .reduce(Money.ZERO, Money::add)
        val withdrawalBalance: Money = activities.stream()
            .filter { it.sourceAccountId == accountId }
            .map { it.money }
            .reduce(Money.ZERO, Money::add)

        return Money.add(depositBalance, withdrawalBalance.negate())
    }

    constructor(vararg activities: Activity) : this(activities.map { it }.toMutableList())

    fun getActivities(): List<Activity> {
        return Collections.unmodifiableList(activities)
    }

    fun addActivity(activity: Activity) {
        activities.add(activity)
    }
}
