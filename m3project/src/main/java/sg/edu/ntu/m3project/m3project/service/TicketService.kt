package sg.edu.ntu.m3project.m3project.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import sg.edu.ntu.m3project.m3project.entity.ConcertEntity
import sg.edu.ntu.m3project.m3project.entity.SeatEntity
import sg.edu.ntu.m3project.m3project.entity.TicketEntity
import sg.edu.ntu.m3project.m3project.entity.UserEntity
import sg.edu.ntu.m3project.m3project.helper.NewTicket
import sg.edu.ntu.m3project.m3project.helper.ResponseMessage
import sg.edu.ntu.m3project.m3project.repository.ConcertRepository
import sg.edu.ntu.m3project.m3project.repository.SeatRepository
import sg.edu.ntu.m3project.m3project.repository.TicketRepository
import sg.edu.ntu.m3project.m3project.repository.UserRepository
import java.util.*

@Service
class TicketService {
    @Autowired
    var ticketRepo: TicketRepository? = null

    @Autowired
    var userRepo: UserRepository? = null

    @Autowired
    var concertRepo: ConcertRepository? = null

    @Autowired
    var seatRepo: SeatRepository? = null
    fun find(userId: Int?): ResponseEntity<*> {
        return if (userId == null) {
            val tickets = ticketRepo!!.findAll() as List<*>
            if (tickets.isEmpty()) {
                ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ResponseMessage("No ticket history."))
            } else ResponseEntity.ok().body(tickets)
        } else {
            val user = userRepo!!.findById(userId) as Optional<UserEntity>
            if (user.isPresent) {
                val tickets = ticketRepo!!.findByUserEntityId(userId) as List<TicketEntity>
                if (tickets.isEmpty()) {
                    ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(ResponseMessage("No ticket history for user: $userId"))
                } else ResponseEntity.ok().body(tickets)
            } else ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ResponseMessage("User not found."))
        }
    }

    // add check for concert ticket quantity
    fun add(userId: Int, newTickets: List<NewTicket>): ResponseEntity<*> {
        val user = userRepo!!.findById(userId) as Optional<UserEntity>
        return if (user.isPresent) {
            var validSeats = true
            for (newTicket in newTickets) {
                val selectedConcertId = newTicket.concertId
                val selectedSeatId = newTicket.seatId
                val selectedConcert = concertRepo
                        ?.findById(selectedConcertId) as Optional<ConcertEntity>
                val selectedSeat = seatRepo
                        ?.findById(selectedSeatId) as Optional<SeatEntity>
                val selectedConcertSeat = ticketRepo
                        ?.findBySeatEntitySeatIdAndConcertEntityIdAndSubmissionStatus(
                                selectedSeatId,
                                selectedConcertId,
                                true) as Optional<TicketEntity>
                if (selectedConcert.isPresent && selectedSeat.isPresent && !selectedConcertSeat.isPresent) {
                    continue
                } else {
                    validSeats = false
                    break
                }
            }
            if (validSeats) {
                val createdTickets: MutableList<TicketEntity> = ArrayList()
                for (newTicket in newTickets) {
                    val selectedConcertId = newTicket.concertId
                    val selectedSeatId = newTicket.seatId
                    val newTicketEntity = TicketEntity()
                    newTicketEntity.submissionStatus = true
                    newTicketEntity.concertEntity = concertRepo!!.findById(selectedConcertId).get()
                    newTicketEntity.userEntity = userRepo!!.findById(userId).get()
                    newTicketEntity.seatEntity = seatRepo!!.findById(selectedSeatId).get()
                    ticketRepo!!.save(newTicketEntity)
                    createdTickets.add(ticketRepo!!.findById(newTicketEntity.ticketId!!).get())
                }
                ResponseEntity.status(HttpStatus.CREATED).body<List<TicketEntity>>(createdTickets)
            } else ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseMessage("Selected concert/seat(s) not available. Please try again."))
        } else ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ResponseMessage("User not found."))
    }

    fun changeSeat(userId: Int, ticketId: Int, selectedSeatId: String): ResponseEntity<*> {
        val user = userRepo!!.findById(userId) as Optional<UserEntity>
        return if (user.isPresent) {
            val ticket = ticketRepo!!.findByTicketIdAndUserEntityId(ticketId,
                    userId) as Optional<TicketEntity>
            if (ticket.isPresent) {
                val concertId = ticket.get().concertEntity?.id
                val selectedSeat = seatRepo
                        ?.findById(selectedSeatId) as Optional<SeatEntity>
                val selectedConcertSeat = ticketRepo!!
                        .findBySeatEntitySeatIdAndConcertEntityIdAndSubmissionStatus(
                                selectedSeatId,
                                concertId,
                                true) as Optional<TicketEntity>
                if (selectedSeat.isPresent && !selectedConcertSeat.isPresent) {
                    ticket.get().seatEntity = seatRepo!!.findById(selectedSeatId).get()
                    ticketRepo!!.save(ticket.get())
                    ResponseEntity.ok().body(ticket.get())
                } else ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ResponseMessage("Seat is unavailable"))
            } else ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseMessage("Ticket ID not found."))
        } else ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ResponseMessage("User not found."))
    }

    fun delete(userId: Int, ticketId: Int): ResponseEntity<*> {
        val user = userRepo!!.findById(userId) as Optional<UserEntity>
        return if (user.isPresent) {
            val ticket = ticketRepo!!.findByTicketIdAndUserEntityId(ticketId,
                    userId) as Optional<TicketEntity>
            if (ticket.isPresent) {
                if (ticket.get().submissionStatus) {
                    ticket.get().submissionStatus = false
                    ticketRepo!!.save(ticket.get())
                    ResponseEntity.ok().body(ResponseMessage("Ticket deleted."))
                } else ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ResponseMessage("Ticket is already deleted."))
            } else ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseMessage("Ticket ID not found."))
        } else ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ResponseMessage("User not found."))
    }
}