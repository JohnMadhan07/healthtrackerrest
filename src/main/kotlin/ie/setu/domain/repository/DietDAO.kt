package ie.setu.domain.repository

import ie.setu.domain.Diet
import ie.setu.domain.db.Diets
import ie.setu.utils.mapToDiet
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class DietDAO {
        fun getAll(): ArrayList<Diet> {
            val DietList: ArrayList<Diet> = arrayListOf()
            transaction {
                Diets.selectAll().map {
                    DietList.add(mapToDiet(it)) }
            }
            return DietList
        }
    fun save(diet: Diet){
        transaction {
            Diets.insert {
                it[food] = diet.food
                it[calories] = diet.calories
                it[userId] = diet.userId
            }
        }
    }

}