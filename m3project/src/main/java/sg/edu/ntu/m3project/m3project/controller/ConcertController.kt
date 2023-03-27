package sg.edu.ntu.m3project.m3project.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import sg.edu.ntu.m3project.m3project.entity.ConcertEntity
import sg.edu.ntu.m3project.m3project.service.ConcertService
import java.util.*

@CrossOrigin
@RestController
@RequestMapping("/concerts")
class ConcertController {
    @Autowired
    var concertService: ConcertService? = null
    @RequestMapping(method = [RequestMethod.GET])
    fun findAllAvailable(): ResponseEntity<*> {
        return concertService!!.find("upcoming", "")
    }

    @RequestMapping(value = ["/history"], method = [RequestMethod.GET])
    fun findAll(): ResponseEntity<*> {
        return concertService!!.find("history", "")
    }

    @RequestMapping(value = ["/search"], method = [RequestMethod.GET])
    fun search(@RequestParam artist: String): ResponseEntity<*> {
        return concertService!!.find("artist", artist.uppercase(Locale.getDefault()))
    }

    @RequestMapping(value = ["/{concertId}"], method = [RequestMethod.GET])
    fun findById(@PathVariable concertId: Int): ResponseEntity<*> {
        return concertService!!.findbyConcertId(concertId)
    }

    @RequestMapping(method = [RequestMethod.POST])
    fun create(@RequestHeader("token") token: String?, @RequestBody concert: ConcertEntity): ResponseEntity<*> {
        return concertService!!.create(token, concert)
    }

    @RequestMapping(value = ["/{concertId}"], method = [RequestMethod.PUT])
    fun update(@RequestHeader("user-id") userId: Int, @RequestBody concert: ConcertEntity,
               @PathVariable concertId: Int): ResponseEntity<*> {
        return concertService!!.update(userId, concert, concertId)
    }
}