package clean.architecture.example.account.application.port.out

import clean.architecture.example.account.domain.Account

interface UpdateAccountStatePort {

    fun updateActivities(account: Account)
}
