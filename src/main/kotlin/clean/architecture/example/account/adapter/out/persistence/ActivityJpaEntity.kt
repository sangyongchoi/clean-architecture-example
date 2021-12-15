package clean.architecture.example.account.adapter.out.persistence

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "activity")
class ActivityJpaEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var timeStamp: LocalDateTime,
    var ownerAccountId: Long,
    var sourceAccountId: Long,
    var targetAccountId: Long,
    var amount: Long,
)
