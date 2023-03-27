package sg.edu.ntu.m3project.m3project.entity

import java.sql.Timestamp
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "users")
class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null

    @Column(name = "name")
    var name: String? = null

    @Column(name = "phone")
    var phone: String? = null

    @Column(name = "email")
    var email: String? = null

    @Column(name = "password")
    var password: String? = null

    @Column(name = "admin_status")
    var isAdminStatus = false

    @Column(name = "updated_at")
    var updatedAt = Timestamp(Date().time)

    @Column(name = "created_at", updatable = false)
    var createdAt = Timestamp(Date().time)
}