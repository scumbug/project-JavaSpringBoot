package sg.edu.ntu.m3project.m3project.service

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import sg.edu.ntu.m3project.m3project.entity.UserEntity
import sg.edu.ntu.m3project.m3project.helper.ResponseMessage
import sg.edu.ntu.m3project.m3project.repository.UserRepository
import java.nio.file.AccessDeniedException
import java.security.SecureRandom
import java.util.*

@Service
class UserService {
    @Autowired
    var userRepo: UserRepository? = null

    @Value("test")
    var secret: String? = null
    final var bCryptSR = SecureRandom()
    var bCryptPasswordEncoder = BCryptPasswordEncoder(10, bCryptSR)
    fun generateToken(user: UserEntity): Map<String, String> {
        val jwtToken = Jwts.builder()
                .setSubject(user.email)
                .setIssuedAt(Date())
                .setExpiration(Date(System.currentTimeMillis() + 864000000)) // 10 days
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact()
        val jwtTokenGen: MutableMap<String, String> = HashMap()
        jwtTokenGen["token"] = jwtToken
        return jwtTokenGen
    }

    @Throws(AccessDeniedException::class)
    fun getUserForAuth(email: String?, password: String?): UserEntity {
        val optionalUser = userRepo!!.findByEmail(email)
        if (!optionalUser!!.isPresent) {
            throw AccessDeniedException("Invalid email.")
        }
        val foundUser = optionalUser.get()
        if (!bCryptPasswordEncoder.matches(password, foundUser.password)) {
            throw AccessDeniedException("Invalid password.")
        }
        return foundUser
    }

    @Throws(AccessDeniedException::class)
    fun checkToken(token: String?): String {
        return try {
            Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .body
                    .subject
        } catch (e: Exception) {
            throw AccessDeniedException("Invalid token")
        }
    }

    fun hashPassword(user: UserEntity): UserEntity {
        val hashPassword = bCryptPasswordEncoder.encode(user.password)
        user.password = hashPassword
        return user
    }

    fun login(user: UserEntity): ResponseEntity<*> {
        return try {
            if (user.email == null || user.password == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ResponseMessage("Email or password is empty."))
            }
            val selectedUser = getUserForAuth(user.email, user.password)
            ResponseEntity(generateToken(selectedUser), HttpStatus.OK)
        } catch (ade: AccessDeniedException) {
            ade.printStackTrace()
            ResponseEntity.status(HttpStatus.FORBIDDEN).body(ResponseMessage(ade.message.toString()))
        } catch (e: Exception) {
            e.printStackTrace()
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseMessage("Something went wrong. Please try again later."))
        }
    }

    fun register(user: UserEntity): ResponseEntity<*> {
        val newUser = userRepo!!.save(hashPassword(user))
        return ResponseEntity(userRepo!!.findById(newUser.id!!), HttpStatus.CREATED)
    }
}