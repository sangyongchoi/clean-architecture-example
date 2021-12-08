package clean.architecture.example.account.application.service

import clean.architecture.example.account.application.port.`in`.GetAccountBalanceQuery
import clean.architecture.example.account.application.port.out.LoadAccountPort
import clean.architecture.example.account.domain.Account
import clean.architecture.example.account.domain.Money
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class GetAccountBalanceService(
    private val loadAccountPort: LoadAccountPort,
) : GetAccountBalanceQuery {

    override fun getAccountBalance(accountId: Account.AccountId): Money {
        return loadAccountPort
            .loadAccount(accountId, LocalDateTime.now())
            .calculateBalance()
    }
}
