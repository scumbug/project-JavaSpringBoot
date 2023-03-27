package sg.edu.ntu.m3project.m3project.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "seats")
class SeatEntity {
    @Id
    var seatId: String? = null

    @Column(name = "seat_category")
    var seatCategory: String? = null

    @Column(name = "venue_hall")
    var venueHall: String? = null

    @Column(name = "ticket_price")
    var ticketPrice = 0f

    @Column(name = "concert_type")
    var concertType: String? = null
}