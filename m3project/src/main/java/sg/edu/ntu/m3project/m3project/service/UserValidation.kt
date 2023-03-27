package sg.edu.ntu.m3project.m3project.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import sg.edu.ntu.m3project.m3project.repository.UserRepository
import java.nio.file.AccessDeniedException

@Service
class UserValidation {
    // this could be combined with userservice
    @Autowired
    var userRepo: UserRepository? = null
    @Throws(AccessDeniedException::class)
    fun checkUser(userId: Int) {
        val optionalUser = userRepo!!.findById(userId)

        /*
         * if requester has admin access, it will not throw exception & process
         * continues
         */if (optionalUser.isPresent) {
            val user = optionalUser.get()
            if (!user.isAdminStatus) throw AccessDeniedException("User is not an administrator")
        } else {
            // not a user
            throw AccessDeniedException("Please login to continue.")
        }
    }
}