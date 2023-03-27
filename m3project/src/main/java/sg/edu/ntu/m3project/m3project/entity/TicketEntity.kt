package sg.edu.ntu.m3project.m3project.entity

import java.sql.Timestamp
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "tickets")
class TicketEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var ticketId: Int? = null

    @ManyToOne
    @JoinColumn(name = "seat_id")
    var seatEntity: SeatEntity? = null

    @ManyToOne
    @JoinColumn(name = "concert_id")
    var concertEntity: ConcertEntity? = null

    @ManyToOne
    @JoinColumn(name = "user_id")
    var userEntity: UserEntity? = null

    @Column(name = "submission_status")
    var submissionStatus = false

    @Column(name = "created_at", updatable = false)
    var createdAt = Timestamp(Date().time)
}