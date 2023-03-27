package sg.edu.ntu.m3project.m3project.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import sg.edu.ntu.m3project.m3project.entity.ConcertEntity
import sg.edu.ntu.m3project.m3project.helper.ResponseMessage
import sg.edu.ntu.m3project.m3project.repository.ConcertRepository
import sg.edu.ntu.m3project.m3project.repository.UserRepository
import java.nio.file.AccessDeniedException
import java.sql.Timestamp
import java.util.*

@Service
class ConcertService {
    @Autowired
    var concertRepo: ConcertRepository? = null

    @Autowired
    var userRepo: UserRepository? = null

    @Autowired
    var userValidation: UserValidation? = null

    @Autowired
    var userService: UserService? = null
    fun find(findBy: String?, searchParam: String?): ResponseEntity<*> {
        return try {
            var currentConcertList: List<ConcertEntity?>
            when (findBy) {
                "upcoming" -> {
                    // find all upcoming
                    val currentDatenTime = Timestamp(Date().time)
                    currentConcertList = concertRepo
                            ?.findByConcertDateAfter(currentDatenTime) as List<ConcertEntity?>
                }

                "artist" -> {
                    // find by artist
                    currentConcertList = concertRepo!!.findByArtist(searchParam) as List<ConcertEntity?>
                    if (currentConcertList.size == 0) {
                        currentConcertList = concertRepo!!.findByArtistContaining(searchParam) as List<ConcertEntity?>
                    }
                }

                "history" -> currentConcertList = concertRepo!!.findAll() as List<ConcertEntity?>
                else -> currentConcertList = concertRepo!!.findAll() as List<ConcertEntity?>
            }
            if (currentConcertList.size > 0) {
                ResponseEntity.ok().body(currentConcertList)
            } else ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseMessage("No upcoming concerts"))
        } catch (e: Exception) {
            e.printStackTrace()
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseMessage("Something went wrong. Please try again later."))
        }
    }

    fun findbyConcertId(concertId: Int): ResponseEntity<*> {
        return try {
            val optionalConcert = concertRepo!!.findById(concertId)
            if (optionalConcert.isPresent) {
                val selectedConcert = optionalConcert.get()
                return ResponseEntity(selectedConcert, HttpStatus.OK)
            }
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseMessage("Invalid concert id."))
        } catch (e: Exception) {
            e.printStackTrace()
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseMessage("Something went wrong. Please try again later."))
        }
    }

    fun create(token: String?, concert: ConcertEntity): ResponseEntity<*> {
        return try {
            // userValidation.checkUser(userId);
            userService!!.checkToken(token)
            val newConcert = concertRepo!!.save(concert)
            ResponseEntity<Any?>(concertRepo!!.findById(newConcert.id!!), HttpStatus.CREATED)
        } catch (ade: AccessDeniedException) {
            ade.printStackTrace()
            ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(ResponseMessage(ade.message.toString()))
        } catch (iae: IllegalArgumentException) {
            iae.printStackTrace()
            ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseMessage("Invalid inputs received from user."))
        } catch (e: Exception) {
            e.printStackTrace()
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseMessage("Something went wrong. Please try again later."))
        }
    }

    fun update(userId: Int, concert: ConcertEntity, concertId: Int): ResponseEntity<*> {
        return try {
            userValidation!!.checkUser(userId)
            val optionalConcert = concertRepo!!.findById(concertId)
            if (optionalConcert.isPresent) {
                val selectedConcert = optionalConcert.get()
                val updatedAt = Timestamp(Date().time)
                selectedConcert.artist = concert.artist
                selectedConcert.concertDate = concert.concertDate
                selectedConcert.ticketPrice = concert.ticketPrice
                selectedConcert.ticketsAvailable = concert.ticketsAvailable
                selectedConcert.updatedAt = updatedAt
                concertRepo!!.save(selectedConcert)
                return ResponseEntity.ok().body(selectedConcert)
            }
            ResponseEntity.notFound().build<Any>()
        } catch (ade: AccessDeniedException) {
            ade.printStackTrace()
            ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(ResponseMessage(ade.message.toString()))
        } catch (iae: IllegalArgumentException) {
            iae.printStackTrace()
            ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseMessage("Invalid inputs received from user."))
        } catch (e: Exception) {
            e.printStackTrace()
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseMessage("Something went wrong. Please try again later."))
        }
    } // public void userValidation(int userId) throws AccessDeniedException {
    // Optional<UserEntity> optionalUser = userRepo.findById(userId);
    // if (optionalUser.isPresent()) {
    // UserEntity user = optionalUser.get();
    // if (!user.isAdminStatus())
    // throw new AccessDeniedException("User is not an administrator");
    // } else {
    // throw new AccessDeniedException("Please login to continue.");
    // }
    // }
}