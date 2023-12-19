package ie.setu.config

import ie.setu.controllers.HealthTrackerController
import ie.setu.controllers.UserController
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.*
import io.javalin.vue.VueComponent

class JavalinConfig {

    fun startJavalinService(): Javalin {

        val app = Javalin.create{
            //added this jsonMapper for our integration tests - serialise objects to json
       //     it.jsonMapper(JavalinJackson(jsonObjectMapper()))
            it.staticFiles.enableWebjars()
            it.vue.vueAppName = "app" // only required for Vue 3, is defined in layout.html
        }.apply {
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
            // The @routeComponent that we added in layout.html earlier will be replaced
// by the String inside the VueComponent. This means a call to / will load
// the layout and display our <home-page> component.
            get("/", VueComponent("<home-page></home-page>"))
            get("/users", VueComponent("<user-overview></user-overview>"))
            get("/users/{user-id}", VueComponent("<user-profile></user-profile>"))
            get("/users/{user-id}/activities", VueComponent("<user-activity-overview></user-activity-overview>"))
            path("/api/users") {
                get(UserController::getAllUsers)
                post(UserController::addUser)
                path("{user-id}"){
                    get(UserController::getUserByUserId)
                    delete(UserController::deleteUser)
                    patch(UserController::updateUser)
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
                    get(UserController::getUserByEmail)
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