package sg.edu.ntu.m3project.m3project.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import sg.edu.ntu.m3project.m3project.entity.UserEntity
import sg.edu.ntu.m3project.m3project.service.UserService

@CrossOrigin
@RestController
@RequestMapping
class AuthenticationController {
    @Autowired
    var userService: UserService? = null
    @RequestMapping(value = ["/login"], method = [RequestMethod.POST])
    fun login(@RequestBody user: UserEntity): ResponseEntity<*> {
        return userService!!.login(user)
    }

    @RequestMapping(value = ["/register"], method = [RequestMethod.POST])
    fun register(@RequestBody user: UserEntity): ResponseEntity<*> {
        return userService!!.register(user)
    }
}