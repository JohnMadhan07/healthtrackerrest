package ie.setu.helpers

import ie.setu.domain.Activity
import ie.setu.domain.Diet
import ie.setu.domain.Supplement
import ie.setu.domain.User
import ie.setu.domain.db.Activities
import ie.setu.domain.db.Diets
import ie.setu.domain.db.Supplements
import ie.setu.domain.db.Users
import ie.setu.domain.repository.ActivityDAO
import ie.setu.domain.repository.DietDAO
import ie.setu.domain.repository.SupplementDAO
import ie.setu.domain.repository.UserDAO
import org.jetbrains.exposed.sql.SchemaUtils
import org.joda.time.DateTime

val nonExistingEmail = "112233445566778testUser@xxxxx.xx"
val validName = "Test User 1"
val validEmail = "testuser1@test.com"

val users = arrayListOf<User>(
    User(name = "Alice Wonderland", email = "alice@wonderland.com", id = 1),
    User(name = "Bob Cat", email = "bob@cat.ie", id = 2),
    User(name = "Mary Contrary", email = "mary@contrary.com", id = 3),
    User(name = "Carol Singer", email = "carol@singer.com", id = 4)
)


val activities = arrayListOf<Activity>(
    Activity(id = 1, description = "Running", duration = 22.0, calories = 230, started = DateTime.now(), userId = 1),
    Activity(id = 2, description = "Hopping", duration = 10.5, calories = 80, started = DateTime.now(), userId = 1),
    Activity(id = 3, description = "Walking", duration = 12.0, calories = 120, started = DateTime.now(), userId = 2)
)
val diets = arrayListOf<Diet>(
    Diet(id = 1, food = "Chicken", calories = 230,  userId = 1),
    Diet(id = 2, food = "Mutton",  calories = 300,  userId = 1),
    Diet(id = 3, food = "Egg",  calories = 30,  userId = 2)
)
val supplements = arrayListOf<Supplement>(
    Supplement(id = 1, name = "Creatine", about = "Increase in ATP",  userId = 1),
    Supplement(id = 2, name = "MultiVitaminTab",  about = "Vitamins for Muscles",  userId = 1),
    Supplement(id = 3, name = "AminoAcids",  about = "Repair muscle tears",  userId = 2)
)

fun populateUserTable(): UserDAO {
    SchemaUtils.create(Users)
    val userDAO = UserDAO()
    userDAO.save(users.get(0))
    userDAO.save(users.get(1))
    userDAO.save(users.get(2))
    return userDAO
}
fun populateActivityTable(): ActivityDAO {
    SchemaUtils.create(Activities)
    val activityDAO = ActivityDAO()
    activityDAO.save(activities.get(0))
    activityDAO.save(activities.get(1))
    activityDAO.save(activities.get(2))
    return activityDAO
}

fun populateDietTable(): DietDAO {
    SchemaUtils.create(Diets)
    val dietDAO = DietDAO()
    dietDAO.save(diets.get(0))
    dietDAO.save(diets.get(1))
    dietDAO.save(diets.get(2))
    return dietDAO
}
fun populateSupplementTable(): SupplementDAO {
    SchemaUtils.create(Supplements)
    val supplementDAO = SupplementDAO()
    supplementDAO.save(supplements.get(0))
    supplementDAO.save(supplements.get(1))
    supplementDAO.save(supplements.get(2))
    return supplementDAO
}