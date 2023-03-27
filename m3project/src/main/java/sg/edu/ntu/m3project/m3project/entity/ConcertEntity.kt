package sg.edu.ntu.m3project.m3project.entity

import java.sql.Timestamp
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "concerts")
class ConcertEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null
    var artist: String? = null

    @Column(name = "concert_date")
    var concertDate: Timestamp? = null

    @Column(name = "tickets_available")
    var ticketsAvailable: Int? = null

    @Column(name = "ticket_price")
    var ticketPrice = 0f

    @Column(name = "updated_at")
    var updatedAt = Timestamp(Date().time)

    @Column(name = "created_at", updatable = false)
    var createdAt = Timestamp(Date().time)
}