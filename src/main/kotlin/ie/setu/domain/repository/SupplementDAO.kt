package ie.setu.domain.repository

import ie.setu.domain.Supplement
import ie.setu.domain.db.Supplements
import ie.setu.utils.mapToSupplement
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class SupplementDAO {
    fun getAll(): ArrayList<Supplement> {
        val supplementsList: ArrayList<Supplement> = arrayListOf()
        transaction {
            Supplements.selectAll().map {
                supplementsList.add(mapToSupplement(it))
            }
        }
        return supplementsList
    }

    fun save(supplement: Supplement) {
        transaction {
            Supplements.insert {
                it[name] = supplement.name
                it[about] = supplement.about
                it[userId] = supplement.userId
            }
        }
    }

    fun findByUserId(userId: Int): List<Supplement> {
        return transaction {
            Supplements
                .select { Supplements.userId eq userId }
                .map { mapToSupplement(it) }
        }
    }

    fun findBySupplementId(id: Int): Supplement? {
        return transaction {
            Supplements
                .select() { Supplements.id eq id }
                .map { mapToSupplement(it) }
                .firstOrNull()
        }
    }
    fun updateBySupplementId(supplementId: Int, supplementDTO: Supplement){
        transaction {
            Supplements.update ({
                Supplements.id eq supplementId}) {
                it[name] = supplementDTO.name
                it[about] = supplementDTO.about
                it[userId] = supplementDTO.userId
            }
        }
    }

    fun deleteBySupplementId(supplementId: Int): Int {
        return transaction {
            Supplements.deleteWhere { Supplements.id eq supplementId }
        }
    }
    fun deleteByUserId (userId: Int): Int{
        return transaction{
            Supplements.deleteWhere { Supplements.userId eq userId }
        }
    }
}

