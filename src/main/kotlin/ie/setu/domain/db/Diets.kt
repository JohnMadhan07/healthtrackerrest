package ie.setu.domain.db

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

// SRP - Responsibility is to manage one user.
//       Database wise, this is the table object.

object Diets : Table("diets") {
    val id = integer("id").autoIncrement().primaryKey()
    val food = varchar("food", 100)
    val calories = integer("calories")
    val userId = integer("user_id").references(Users.id, onDelete = ReferenceOption.CASCADE)
}