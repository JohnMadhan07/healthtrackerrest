package ie.setu.controllers
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import ie.setu.domain.Diet
import ie.setu.domain.repository.DietDAO
import ie.setu.domain.repository.UserDAO
import io.javalin.http.Context

object DietsController {
    private val userDao = UserDAO()
    private val dietDAO = DietDAO()
    fun getAllDiet(ctx: Context) {
        ctx.json(dietDAO.getAll())
    }

    fun getDietsByUserId(ctx: Context) {
        if (userDao.findById(ctx.pathParam("user-id").toInt()) != null) {
            val diets = dietDAO.findByUserId(ctx.pathParam("user-id").toInt())
            if (diets.isNotEmpty()) {
                ctx.json(diets)
            }
        }
    }
    fun addDiet(ctx: Context) {
        val mapper = jacksonObjectMapper()
        val diet = mapper.readValue<Diet>(ctx.body())
        dietDAO.save(diet)
        ctx.json(diet)
    }
    fun getDietsByDietId(ctx: Context) {
        val diet = dietDAO.findByDietId((ctx.pathParam("diet-id").toInt()))
        if (diet != null) {
            ctx.json((diet))
        }
    }
    fun updateDiet(ctx: Context) {
        val mapper = jacksonObjectMapper()
        val diet = mapper.readValue<Diet>(ctx.body())
        dietDAO.updateByDietId(
            dietId = ctx.pathParam("diet-id").toInt(),
            dietDTO = diet
        )
    }
    fun deleteDietByDietId(ctx: Context) {
        dietDAO.deleteByDietId(ctx.pathParam("diet-id").toInt())
    }

    fun deleteDietByUserId(ctx: Context) {
        dietDAO.deleteByUserId(ctx.pathParam("user-id").toInt())
    }
}