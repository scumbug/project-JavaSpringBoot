package sg.edu.ntu.m3project.m3project.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import sg.edu.ntu.m3project.m3project.helper.ResponseMessage
import sg.edu.ntu.m3project.m3project.repository.SeatRepository

@CrossOrigin
@RestController
@RequestMapping("/seats")
class SeatController {
    @Autowired
    var seatRepo: SeatRepository? = null
    @GetMapping
    fun findAll(): ResponseEntity<*> {
        return try {
            ResponseEntity.ok().body(seatRepo!!.findAll())
        } catch (e: Exception) {
            e.printStackTrace()
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseMessage("Something went wrong. Please try again later."))
        }
    } // add create, put, delete
}