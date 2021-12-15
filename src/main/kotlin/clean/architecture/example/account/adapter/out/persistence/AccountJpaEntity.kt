package clean.architecture.example.account.adapter.out.persistence

import javax.persistence.*

@Entity
@Table(name = "account")
class AccountJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}