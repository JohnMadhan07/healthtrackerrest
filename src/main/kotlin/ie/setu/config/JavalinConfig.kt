package ie.setu.config

import ie.setu.controllers.HealthTrackerController
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.*

class JavalinConfig {

    fun startJavalinService(): Javalin {

        val app = Javalin.create().apply {
            exception(Exception::class.java) { e, _ -> e.printStackTrace() }
            error(404) { ctx -> ctx.json("404 : Not Found") }
        }.start(getRemoteAssignedPort())

        registerRoutes(app)
        return app
    }

    private fun getRemoteAssignedPort(): Int {
        val remotePort = System.getenv("PORT")
        return if (remotePort != null) {
            Integer.parseInt(remotePort)
        } else 7000
    }

    private fun registerRoutes(app: Javalin) {
        app.routes {
            path("/api/users") {
                get(HealthTrackerController::getAllUsers)
                post(HealthTrackerController::addUser)
                path("{user-id}"){
                    get(HealthTrackerController::getUserByUserId)
                    delete(HealthTrackerController::deleteUser)
                    patch(HealthTrackerController::updateUser)
                    //The overall path is: "/api/users/:user-id/activities"
                    path("activities"){
                        get(HealthTrackerController::getActivitiesByUserId)
                        delete(HealthTrackerController::deleteActivityByUserId)
                    }
                    //The overall path is: "/api/users/:user-id/diets"
                    path("diets"){
                        get(HealthTrackerController::getDietsByUserId)
                        delete(HealthTrackerController::deleteDietByUserId)
                    }
                    //The overall path is: "/api/users/:user-id/supplements"
                    path("supplements"){
                        get(HealthTrackerController::getSupplementsByUserId)
                        delete(HealthTrackerController::deleteSupplementByUserId)
                    }
                }
                path("/email/{email}"){
                    get(HealthTrackerController::getUserByEmail)
                }
            }
            path("/api/activities") {
                get(HealthTrackerController::getAllActivities)
                post(HealthTrackerController::addActivity)
                path("{activity-id}") {
                    get(HealthTrackerController::getActivitiesByActivityId)
                    delete(HealthTrackerController::deleteActivityByActivityId)
                    patch(HealthTrackerController::updateActivity)
                }
            }
            path("/api/diets"){
                get(HealthTrackerController::getAllDiet)
                post(HealthTrackerController::addDiet)
                path("{diet-id}"){
                    get(HealthTrackerController::getDietsByDietId)
                    delete(HealthTrackerController::deleteDietByDietId)
                    patch(HealthTrackerController::updateDiet)
                }
            }
            path("/api/supplements"){
                get(HealthTrackerController::getAllSupplement)
                post(HealthTrackerController::addSupplement)
                path("{supplement-id}") {
                    get(HealthTrackerController::findSupplementsBySupplementId)
                    delete(HealthTrackerController::deleteSupplementBySupplementId)
                    patch(HealthTrackerController::updateSupplement)
                }
            }
        }
    }
}