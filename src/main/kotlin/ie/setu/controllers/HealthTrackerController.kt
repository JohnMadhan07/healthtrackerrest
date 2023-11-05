package ie.setu.controllers

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.joda.JodaModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import ie.setu.domain.Activity
import ie.setu.domain.Diet
import ie.setu.domain.User
import ie.setu.domain.repository.ActivityDAO
import ie.setu.domain.repository.DietDAO
import ie.setu.domain.repository.UserDAO
import io.javalin.http.Context

object HealthTrackerController {

    private val userDao = UserDAO()
    private val activityDAO = ActivityDAO()
    private val dietDAO = DietDAO()

    fun getAllUsers(ctx: Context) {
        ctx.json(userDao.getAll())
    }

    fun getUserByUserId(ctx: Context) {
        val user = userDao.findById(ctx.pathParam("user-id").toInt())
        if (user != null) {
            ctx.json(user)
        }
    }

    fun addUser(ctx: Context) {
        val mapper = jacksonObjectMapper()
        val user = mapper.readValue<User>(ctx.body())
        userDao.save(user)
        ctx.json(user)
    }

    fun getUserByEmail(ctx: Context) {
        val user = userDao.findByEmail(ctx.pathParam("email"))
        if (user != null) {
            ctx.json(user)
        }
    }

    fun deleteUser(ctx: Context) {
        userDao.delete(ctx.pathParam("user-id").toInt())
    }

    fun updateUser(ctx: Context) {
        val mapper = jacksonObjectMapper()
        val userUpdates = mapper.readValue<User>(ctx.body())
        userDao.update(
            id = ctx.pathParam("user-id").toInt(),
            user = userUpdates
        )
    }

    //--------------------------------------------------------------
    // ActivityDAOI specifics
    //-------------------------------------------------------------

    fun getAllActivities(ctx: Context) {
        //mapper handles the deserialization of Joda date into a String.
        val mapper = jacksonObjectMapper()
            .registerModule(JodaModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        ctx.json(mapper.writeValueAsString(activityDAO.getAll()))
    }

    fun getActivitiesByUserId(ctx: Context) {
        if (userDao.findById(ctx.pathParam("user-id").toInt()) != null) {
            val activities = activityDAO.findByUserId(ctx.pathParam("user-id").toInt())
            if (activities.isNotEmpty()) {
                //mapper handles the deserialization of Joda date into a String.
                val mapper = jacksonObjectMapper()
                    .registerModule(JodaModule())
                    .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                ctx.json(mapper.writeValueAsString(activities))
            }
        }
    }

    fun addActivity(ctx: Context) {
        //mapper handles the serialisation of Joda date into a String.
        val mapper = jacksonObjectMapper()
            .registerModule(JodaModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        val activity = mapper.readValue<Activity>(ctx.body())
        activityDAO.save(activity)
        ctx.json(activity)
    }

    fun getActivitiesByActivityId(ctx: Context) {
        val activity = activityDAO.findByActivityId((ctx.pathParam("activity-id").toInt()))
        if (activity != null){
            val mapper = jacksonObjectMapper()
                .registerModule(JodaModule())
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            ctx.json(mapper.writeValueAsString(activity))
        }
    }

    fun deleteActivityByActivityId(ctx: Context){
        activityDAO.deleteByActivityId(ctx.pathParam("activity-id").toInt())
    }

    fun deleteActivityByUserId(ctx: Context){
        activityDAO.deleteByUserId(ctx.pathParam("user-id").toInt())
    }

    fun updateActivity(ctx: Context){
        val mapper = jacksonObjectMapper()
            .registerModule(JodaModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        val activity = mapper.readValue<Activity>(ctx.body())
        activityDAO.updateByActivityId(
            activityId = ctx.pathParam("activity-id").toInt(),
            activityDTO=activity)
    }

//--------------------------------------------------------------
// DietDAOI specifics
//-------------------------------------------------------------
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
        if (diet != null){
            ctx.json((diet))
        }
    }
    fun updateDiet(ctx: Context){
        val mapper = jacksonObjectMapper()
        val diet = mapper.readValue<Diet>(ctx.body())
        dietDAO.updateByDietId(
            dietId = ctx.pathParam("diet-id").toInt(),
            dietDTO= diet)
    }
    fun deleteDietByDietId(ctx: Context){
        dietDAO.deleteByDietId(ctx.pathParam("diet-id").toInt())
    }   fun deleteDietByUserId(ctx: Context){
        dietDAO.deleteByUserId(ctx.pathParam("user-id").toInt())
    }

}
