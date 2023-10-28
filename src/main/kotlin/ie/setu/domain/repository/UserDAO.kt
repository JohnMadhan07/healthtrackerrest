package ie.setu.domain.repository
import ie.setu.domain.User
import ie.setu.domain.db.Users
import ie.setu.domain.utils.mapToUser
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class UserDAO {
    fun getAll() : ArrayList<User> {
        val userList: ArrayList<User> = arrayListOf()
        transaction {
            Users.selectAll().map {
                userList.add(mapToUser(it))
            }

        }
        return userList
    }
    fun findById(id: Int): User?{
        return null
    }
    fun save(user: User){

  }
}