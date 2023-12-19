package ie.setu.config

import ie.setu.controllers.ActivitiesController
import ie.setu.controllers.DietsController
import ie.setu.controllers.SupplementsController
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
                        get(ActivitiesController::getActivitiesByUserId)
                        delete(ActivitiesController::deleteActivityByUserId)
                    }
                    //The overall path is: "/api/users/:user-id/diets"
                    path("diets"){
                        get(DietsController::getDietsByUserId)
                        delete(DietsController::deleteDietByUserId)
                    }
                    //The overall path is: "/api/users/:user-id/supplements"
                    path("supplements"){
                        get(SupplementsController::getSupplementsByUserId)
                        delete(SupplementsController::deleteSupplementByUserId)
                    }
                }
                path("/email/{email}"){
                    get(UserController::getUserByEmail)
                }
            }
            path("/api/activities") {
                get(ActivitiesController::getAllActivities)
                post(ActivitiesController::addActivity)
                path("{activity-id}") {
                    get(ActivitiesController::getActivitiesByActivityId)
                    delete(ActivitiesController::deleteActivityByActivityId)
                    patch(ActivitiesController::updateActivity)
                }
            }
            path("/api/diets"){
                get(DietsController::getAllDiet)
                post(DietsController::addDiet)
                path("{diet-id}"){
                    get(DietsController::getDietsByDietId)
                    delete(DietsController::deleteDietByDietId)
                    patch(DietsController::updateDiet)
                }
            }
            path("/api/supplements"){
                get(SupplementsController::getAllSupplement)
                post(SupplementsController::addSupplement)
                path("{supplement-id}") {
                    get(SupplementsController::findSupplementsBySupplementId)
                    delete(SupplementsController::deleteSupplementBySupplementId)
                    patch(SupplementsController::updateSupplement)
                }
            }
        }
    }
}