package ie.setu.domain.db

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

// SRP - Responsibility is to manage one Supplement.
//       Database wise, this is the table object.

object Supplements : Table("supplements") {
    val id = integer("id").autoIncrement().primaryKey()
    val name = varchar("name", 100)
    val about = varchar("about",100)
    val userId = integer("user_id").references(Users.id, onDelete = ReferenceOption.CASCADE)
}