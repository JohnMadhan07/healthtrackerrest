package ie.setu.controllers
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import ie.setu.domain.Supplement
import ie.setu.domain.repository.SupplementDAO
import ie.setu.domain.repository.UserDAO
import io.javalin.http.Context

object SupplementsController {
    private val userDao = UserDAO()
    private val supplementDAO = SupplementDAO()
    fun getAllSupplement(ctx: Context) {
        ctx.json(supplementDAO.getAll())
    }

    fun getSupplementsByUserId(ctx: Context) {
        if (userDao.findById(ctx.pathParam("user-id").toInt()) != null) {
            val supplements = supplementDAO.findByUserId(ctx.pathParam("user-id").toInt())
            if (supplements.isNotEmpty()) {
                ctx.json(supplements)
            }
        }
    }

    fun findSupplementsBySupplementId(ctx: Context) {
        val supplement = supplementDAO.findBySupplementId((ctx.pathParam("supplement-id").toInt()))
        if (supplement != null) {
            ctx.json((supplement))
        }
    }

    fun addSupplement(ctx: Context) {
        val mapper = jacksonObjectMapper()
        val supplement = mapper.readValue<Supplement>(ctx.body())
        supplementDAO.save(supplement)
        ctx.json(supplement)
    }
    fun updateSupplement(ctx: Context) {
        val mapper = jacksonObjectMapper()
        val supplement = mapper.readValue<Supplement>(ctx.body())
        supplementDAO.updateBySupplementId(
            supplementId = ctx.pathParam("supplement-id").toInt(),
            supplementDTO = supplement
        )
    }
    fun deleteSupplementBySupplementId(ctx: Context) {
        supplementDAO.deleteBySupplementId(ctx.pathParam("supplement-id").toInt())

    }
    fun deleteSupplementByUserId(ctx: Context) {
        supplementDAO.deleteByUserId(ctx.pathParam("user-id").toInt())
    }

}
