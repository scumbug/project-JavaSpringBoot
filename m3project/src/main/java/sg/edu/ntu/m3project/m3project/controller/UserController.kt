package sg.edu.ntu.m3project.m3project.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import sg.edu.ntu.m3project.m3project.entity.UserEntity
import sg.edu.ntu.m3project.m3project.helper.ResponseMessage
import sg.edu.ntu.m3project.m3project.repository.UserRepository
import sg.edu.ntu.m3project.m3project.service.UserService
import java.sql.Timestamp
import java.util.*

@CrossOrigin
@RestController
@RequestMapping("/users")
class UserController {
    @Autowired
    var userRepo: UserRepository? = null

    @Autowired
    var userService: UserService? = null
    @RequestMapping(method = [RequestMethod.GET])
    fun findAll(): ResponseEntity<*> {
        return try {
            val users = userRepo!!.findAll() as List<UserEntity>
            ResponseEntity.ok().body(users)
        } catch (e: Exception) {
            e.printStackTrace()
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseMessage("Something went wrong. Please try again later."))
        }
    }

    @RequestMapping(value = ["/{id}"], method = [RequestMethod.GET])
    fun findById(@PathVariable id: Int): ResponseEntity<*> {
        return try {
            val optional = userRepo!!.findById(id)
            if (optional.isPresent) {
                val user = optional.get()
                return ResponseEntity.ok().body(user)
            }
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ResponseMessage("User $id not found. Please try a different User ID."))
        } catch (e: Exception) {
            e.printStackTrace()
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseMessage("Something went wrong. Please try again later."))
        }
    }

    @RequestMapping(method = [RequestMethod.POST])
    fun create(@RequestBody user: UserEntity): ResponseEntity<*> {
        return try {
            val createNewUser = userRepo!!.save(user)
            ResponseEntity(userRepo!!.findById(createNewUser.id!!), HttpStatus.CREATED)
        } catch (e: Exception) {
            e.printStackTrace()
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseMessage("Something went wrong. Please try again later."))
        }
    }

    @RequestMapping(value = ["/{id}"], method = [RequestMethod.PUT])
    fun update(@RequestHeader(name = "user-id", required = true) userID: Int,
               @RequestBody user: UserEntity, @PathVariable id: Int): ResponseEntity<*> {
        return try {
            val userToBeUpdated = userRepo!!.findById(id)
            if (userToBeUpdated.isPresent) {
                var editedUser = userToBeUpdated.get()

                // check if userInHeader exists
                val userInHeader = userRepo!!.findById(userID)
                if (userInHeader.isPresent) {
                    val headerUser = userInHeader.get()

                    // check if match - editedUser.getId() == userID
                    // check if admin - headerUser.isAdminStatus()
                    if (userID == editedUser.id || headerUser.isAdminStatus) {
                        val updatedAt = Timestamp(Date().time)
                        editedUser.name = user.name
                        editedUser.phone = user.phone
                        editedUser.email = user.email
                        editedUser.password = user.password
                        editedUser.isAdminStatus = user.isAdminStatus
                        editedUser.updatedAt = updatedAt
                        editedUser = userRepo!!.save(editedUser)
                        return ResponseEntity.ok().body(editedUser)
                    }
                    return ResponseEntity.status(HttpStatus.FORBIDDEN)
                            .body(ResponseMessage("Sorry, you are not allowed to update this user."))
                }
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(ResponseMessage("Invalid credentials."))
            }
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ResponseMessage("User $id not found. Please try a different User ID."))
        } catch (e: Exception) {
            e.printStackTrace()
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseMessage("Something went wrong. Please try again later."))
        }
    }
}