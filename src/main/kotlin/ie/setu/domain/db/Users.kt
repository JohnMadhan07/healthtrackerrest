package ie.setu.domain.db
import org.jetbrains.exposed.sql.Table
object Users: Table("Users"){
    val id= integer("id").autoIncrement().primaryKey()
    val name= varchar("name",100)
    val email = varchar("email",255)
}