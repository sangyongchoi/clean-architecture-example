package clean.architecture.example.account.application.service

import clean.architecture.example.account.application.port.`in`.SendMoneyCommand
import clean.architecture.example.account.application.port.`in`.SendMoneyUseCase
import clean.architecture.example.account.application.port.out.AccountLock
import clean.architecture.example.account.application.port.out.LoadAccountPort
import clean.architecture.example.account.application.port.out.UpdateAccountStatePort
import org.springframework.stereotype.Service

@Service
class SendMoneyService(
    private val loadAccountPort: LoadAccountPort,
    private val accountLock: AccountLock,
    private val updateAccountStatePort: UpdateAccountStatePort,
) : SendMoneyUseCase {

    override fun sendMoney(
        command: SendMoneyCommand,
    ): Boolean {
        TODO("Not yet implemented")
    }
}
