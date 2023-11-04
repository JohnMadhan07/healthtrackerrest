package ie.setu.utils

import ie.setu.domain.Activity
import ie.setu.domain.Diet
import ie.setu.domain.User
import ie.setu.domain.db.Activities
import ie.setu.domain.db.Diets
import ie.setu.domain.db.Users
import org.jetbrains.exposed.sql.ResultRow

fun mapToUser(it: ResultRow) = User(
    id = it[Users.id],
    name = it[Users.name],
    email = it[Users.email]
)

fun mapToActivity(it: ResultRow) = Activity(
    id = it[Activities.id],
    description = it[Activities.description],
    duration = it[Activities.duration],
    started = it[Activities.started],
    calories = it[Activities.calories],
    userId = it[Activities.userId]
)
fun mapToDiet(it: ResultRow) = Diet(
    id = it[Diets.id],
    food = it[Diets.food],
    calories = it[Diets.calories],
    userId = it[Diets.userId]
)