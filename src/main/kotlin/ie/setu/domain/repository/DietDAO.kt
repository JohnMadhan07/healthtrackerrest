package ie.setu.domain.repository

import ie.setu.domain.Diet
import ie.setu.domain.db.Diets
import ie.setu.utils.mapToDiet
import org.jetbrains.exposed.sql.*
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
    //Find specific Diet with UserID
    fun findByUserId(userId: Int): List<Diet>{
        return transaction {
            Diets
                .select { Diets.userId eq userId}
                .map { mapToDiet(it) }
        }
    }
    fun findByDietId(id: Int): Diet?{
        return transaction {
            Diets
                .select() { Diets.id eq id}
                .map{ mapToDiet(it) }
                .firstOrNull()
        }
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
    fun updateByDietId(dietId: Int, dietDTO: Diet){
        transaction {
            Diets.update ({
                Diets.id eq dietId}) {
                it[food] = dietDTO.food
                it[calories] = dietDTO.calories
                it[userId] = dietDTO.userId
            }
        }
    }
    fun deleteByDietId (dietId: Int): Int{
        return transaction{
            Diets.deleteWhere { Diets.id eq dietId }
        }
    }  fun deleteByUserId (userId: Int): Int{
        return transaction{
            Diets.deleteWhere { Diets.userId eq userId }
        }
    }

}