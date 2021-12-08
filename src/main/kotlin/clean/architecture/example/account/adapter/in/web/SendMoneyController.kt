package clean.architecture.example.account.adapter.`in`.web

import clean.architecture.example.account.application.port.`in`.SendMoneyCommand
import clean.architecture.example.account.application.port.`in`.SendMoneyUseCase
import clean.architecture.example.account.domain.Account
import clean.architecture.example.account.domain.Money
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class SendMoneyController(
    private val sendMoneyUseCase: SendMoneyUseCase,
) {

    @PostMapping("/accounts/send/{sourceAccountId}/{targetAccountId}/{amount}")
    fun sendMoney(
        @PathVariable("sourceAccountId") sourceAccountId: Long,
        @PathVariable("targetAccountId") targetAccountId: Long,
        @PathVariable("amount") amount: Long,
    ) {
       val sendMoneyCommand = SendMoneyCommand(
           sourceAccountId = Account.AccountId(sourceAccountId),
           targetAccountId = Account.AccountId(targetAccountId),
           money = Money(amount.toBigInteger()),
       )

        sendMoneyUseCase.sendMoney(sendMoneyCommand)
    }
}
