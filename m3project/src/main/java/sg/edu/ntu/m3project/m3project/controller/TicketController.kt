package sg.edu.ntu.m3project.m3project.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import sg.edu.ntu.m3project.m3project.helper.NewTicket
import sg.edu.ntu.m3project.m3project.helper.ResponseMessage
import sg.edu.ntu.m3project.m3project.service.TicketService

@CrossOrigin
@RestController
@RequestMapping("/tickets")
class TicketController {
    @Autowired
    var ticketService: TicketService? = null

    // Get Tickets by All/User ID (to refactor to ticketService)
    @GetMapping
    fun findAllById(@RequestHeader(value = "user_id", required = false) userId: Int?): ResponseEntity<*> {
        return try {
            ticketService!!.find(userId)
        } catch (iae: IllegalArgumentException) {
            iae.printStackTrace()
            ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseMessage("Bad request."))
        } catch (e: Exception) {
            e.printStackTrace()
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseMessage("Something went wrong. Please try again later."))
        }
    }

    // Create Ticket (to update to array input for multiple tickets)
    @PostMapping
    fun createTicket(
            @RequestHeader(value = "user_id") userId: Int,
            @RequestBody newTickets: List<NewTicket>): ResponseEntity<*> {
        return try {
            ticketService!!.add(userId, newTickets)
        } catch (e: Exception) {
            e.printStackTrace()
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseMessage("Something went wrong. Please try again later."))
        }
    }

    @PutMapping("/{ticket_id}") // change selectedSeatId to RequestParam?
    fun changeSeat(
            @RequestHeader(value = "user_id") userId: Int,
            @PathVariable ticket_id: Int,
            @RequestBody selectedSeatId: String): ResponseEntity<*> {
        return try {
            ticketService!!.changeSeat(userId, ticket_id, selectedSeatId)
        } catch (e: Exception) {
            e.printStackTrace()
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseMessage("Something went wrong. Please try again later."))
        }
    }

    @PostMapping("/{ticket_id}/delete")
    fun deleteTicket(
            @RequestHeader(value = "user_id") userId: Int,
            @PathVariable ticket_id: Int): ResponseEntity<*> {
        return try {
            ticketService!!.delete(userId, ticket_id)
        } catch (e: Exception) {
            e.printStackTrace()
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseMessage("Something went wrong. Please try again later."))
        }
    }
}