package clean.architecture.example.account.adapter.out.persistence

import clean.architecture.example.account.domain.Account
import clean.architecture.example.account.domain.Account.AccountId
import clean.architecture.example.account.domain.Activity
import clean.architecture.example.account.domain.Activity.ActivityId
import clean.architecture.example.account.domain.ActivityWindow
import clean.architecture.example.account.domain.Money
import org.springframework.stereotype.Component

@Component
class AccountMapper {

    fun mapToDomainEntity(
        account: AccountJpaEntity,
        activities: List<ActivityJpaEntity>,
        withdrawalBalance: Long?,
        depositBalance: Long?
    ): Account? {
        val baselineBalance = Money.subtract(
            Money.of(depositBalance!!),
            Money.of(withdrawalBalance!!)
        )
        return Account.withId(
            AccountId(account.id!!),
            baselineBalance,
            mapToActivityWindow(activities)
        )
    }

    fun mapToActivityWindow(activities: List<ActivityJpaEntity>): ActivityWindow? {
        val mappedActivities: MutableList<Activity> = ArrayList()
        for (activity in activities) {
            mappedActivities.add(
                Activity(
                    ActivityId(activity.id),
                    AccountId(activity.ownerAccountId),
                    AccountId(activity.sourceAccountId),
                    AccountId(activity.targetAccountId),
                    activity.timeStamp,
                    Money.of(activity.amount)
                )
            )
        }
        return ActivityWindow(mappedActivities)
    }

    fun mapToJpaEntity(activity: Activity): ActivityJpaEntity {
        return ActivityJpaEntity(
            activity.id?.value,
            activity.timestamp,
            activity.ownerAccountId!!.value,
            activity.sourceAccountId!!.value,
            activity.targetAccountId!!.value,
            activity.money.amount.toLong(),
        )
    }
}