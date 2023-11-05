package ie.setu.domain.repository

import ie.setu.domain.Supplement
import ie.setu.domain.db.Supplements
import ie.setu.utils.mapToSupplement
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class SupplementDAO {
    fun getAll(): ArrayList<Supplement> {
        val supplementsList: ArrayList<Supplement> = arrayListOf()
        transaction {
            Supplements.selectAll().map {
                supplementsList.add(mapToSupplement(it)) }
        }
        return supplementsList
    }
    fun save(supplement: Supplement){
        transaction {
            Supplements.insert {
                it[name] = supplement.name
                it[about] = supplement.about
                it[userId] = supplement.userId
            }
        }
    }
    fun findByUserId(userId: Int): List<Supplement>{
        return transaction {
            Supplements
                .select { Supplements.userId eq userId}
                .map { mapToSupplement(it) }
        }
    }
    fun findBySupplementId(id: Int): Supplement?{
        return transaction {
            Supplements
                .select() { Supplements.id eq id}
                .map{ mapToSupplement(it) }
                .firstOrNull()
        }
    }
}