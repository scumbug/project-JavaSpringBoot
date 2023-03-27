package sg.edu.ntu.m3project.m3project.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import sg.edu.ntu.m3project.m3project.entity.TicketEntity
import java.util.*

@Repository
interface TicketRepository : CrudRepository<TicketEntity?, Int?> {
    fun findByUserEntityId(userId: Int?): List<TicketEntity?>?
    fun findBySeatEntitySeatIdAndConcertEntityIdAndSubmissionStatus(seatId: String?, concertId: Int?,
                                                                    submissionStatus: Boolean): Optional<TicketEntity?>?

    fun findByTicketIdAndUserEntityId(ticketId: Int?, userId: Int?): Optional<TicketEntity?>?
}