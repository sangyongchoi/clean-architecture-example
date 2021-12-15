package clean.architecture.example.account.application.service

import clean.architecture.example.account.application.port.`in`.SendMoneyCommand
import clean.architecture.example.account.application.port.`in`.SendMoneyUseCase
import clean.architecture.example.account.application.port.out.AccountLock
import clean.architecture.example.account.application.port.out.LoadAccountPort
import clean.architecture.example.account.application.port.out.UpdateAccountStatePort
import clean.architecture.example.account.domain.Account
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class SendMoneyService(
    private val loadAccountPort: LoadAccountPort,
    private val accountLock: AccountLock,
    private val updateAccountStatePort: UpdateAccountStatePort,
) : SendMoneyUseCase {

    override fun sendMoney(
        command: SendMoneyCommand,
    ): Boolean {
        val baselineDate = LocalDateTime.now().minusDays(10)

        val sourceAccount: Account? = loadAccountPort.loadAccount(
            command.sourceAccountId,
            baselineDate
        )

        val targetAccount: Account? = loadAccountPort.loadAccount(
            command.targetAccountId,
            baselineDate
        )

        val sourceAccountId: Account.AccountId = sourceAccount?.id ?: throw IllegalStateException("expected source account ID not to be empty")
        val targetAccountId: Account.AccountId = targetAccount?.id ?: throw IllegalStateException("expected target account ID not to be empty")

        accountLock.lockAccount(sourceAccountId)
        if (!sourceAccount.withdraw(command.money, targetAccountId)) {
            accountLock.releaseAccount(sourceAccountId)
            return false
        }

        accountLock.lockAccount(targetAccountId)
        if (!targetAccount.deposit(command.money, sourceAccountId)) {
            accountLock.releaseAccount(sourceAccountId)
            accountLock.releaseAccount(targetAccountId)
            return false
        }

        updateAccountStatePort.updateActivities(sourceAccount)
        updateAccountStatePort.updateActivities(targetAccount)

        accountLock.releaseAccount(sourceAccountId)
        accountLock.releaseAccount(targetAccountId)
        return true
    }
}
