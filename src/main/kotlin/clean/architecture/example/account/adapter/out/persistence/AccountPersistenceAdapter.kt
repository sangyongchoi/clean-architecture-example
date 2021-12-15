package clean.architecture.example.account.adapter.out.persistence

import clean.architecture.example.account.application.port.out.LoadAccountPort
import clean.architecture.example.account.application.port.out.UpdateAccountStatePort
import clean.architecture.example.account.domain.Account
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import javax.persistence.EntityNotFoundException

@Component
class AccountPersistenceAdapter(
    private val accountRepository: AccountRepository,
    private val activityRepository: ActivityRepository,
    private val accountMapper: AccountMapper,
) : LoadAccountPort, UpdateAccountStatePort {

    override fun loadAccount(
        accountId: Account.AccountId,
        baselineDate: LocalDateTime
    ): Account? {
        val account = accountRepository.findByIdOrNull(accountId.value) ?: throw EntityNotFoundException()

        val activities = activityRepository.findByOwnerSince(
            accountId.value,
            baselineDate,
        )

        val withdrawalBalance = orZero(activityRepository.getWithdrawalBalanceUntil(
            accountId.value,
            baselineDate
        ))

        val depositBalance = orZero(
            activityRepository.getDepositBalanceUntil(
                accountId.value,
                baselineDate
            )
        )

        return accountMapper.mapToDomainEntity(
            account,
            activities,
            withdrawalBalance,
            depositBalance
        )
    }

    override fun updateActivities(account: Account) {
        account.activityWindow
            .getActivities()
            .forEach { activity ->
                if (activity.id == null) {
                    activityRepository.save(accountMapper.mapToJpaEntity(activity))
                }
            }
    }

    private fun orZero(value: Long?): Long {
        return value ?: 0
    }
}