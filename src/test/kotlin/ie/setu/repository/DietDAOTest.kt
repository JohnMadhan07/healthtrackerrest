package ie.setu.repository
import ie.setu.domain.Diet
import ie.setu.domain.db.Diets
import ie.setu.domain.repository.DietDAO
import ie.setu.helpers.diets
import ie.setu.helpers.populateDietTable
import ie.setu.helpers.populateUserTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class DietDAOTest {
    //retrieving some test data from Fixtures
    private val diet1 = diets.get(0)
    private val diet2 = diets.get(1)
    private val diet3 = diets.get(2)

    companion object {
        //Make a connection to a local, in memory H2 database.
        @BeforeAll
        @JvmStatic
        internal fun setupInMemoryDatabaseConnection() {
            Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver", user = "root", password = "")
        }
    }

    @Nested
    inner class CreateDiets {

        @Test
        fun `multiple diets added to table can be retrieved successfully`() {
            transaction {
                //Arrange - create and populate tables with three users and three activities
                val userDAO = populateUserTable()
                val activityDAO = populateDietTable()
                //Act & Assert
                assertEquals(3, activityDAO.getAll().size)
                assertEquals(diet1, activityDAO.findByDietId(diet1.id))
                assertEquals(diet2, activityDAO.findByDietId(diet2.id))
                assertEquals(diet3, activityDAO.findByDietId(diet3.id))
            }
        }
    }

    @Nested
    inner class ReadDiets {

        @Test
        fun `getting all diets from a populated table returns all rows`() {
            transaction {
                //Arrange - create and populate tables with three users and three diets
                val userDAO = populateUserTable()
                val dietDAO = populateDietTable()
                //Act & Assert
                assertEquals(3, dietDAO.getAll().size)
            }
        }

        @Test
        fun `get diet by user id that has no diets, results in no record returned`() {
            transaction {
                //Arrange - create and populate tables with three users and three diets
                val userDAO = populateUserTable()
                val dietDAO = populateDietTable()
                //Act & Assert
                assertEquals(0, dietDAO.findByUserId(3).size)
            }
        }

        @Test
        fun `get diet by user id that exists, results in correct diet(s) returned`() {
            transaction {
                //Arrange - create and populate tables with three users and three diets
                val userDAO = populateUserTable()
                val dietDAO = populateDietTable()
                //Act & Assert
                assertEquals(diet1, dietDAO.findByUserId(1).get(0))
                assertEquals(diet2, dietDAO.findByUserId(1).get(1))
                assertEquals(diet3, dietDAO.findByUserId(2).get(0))
            }
        }

        @Test
        fun `get all diets over empty table returns none`() {
            transaction {
                //Arrange - create and setup dietDAO object
                SchemaUtils.create(Diets)
                val dietDAO = DietDAO()
                //Act & Assert
                assertEquals(0, dietDAO.getAll().size)
            }
        }

        @Test
        fun `get diet by diet id that has no records, results in no record returned`() {
            transaction {
                //Arrange - create and populate tables with three users and three diets
                val userDAO = populateUserTable()
                val dietDAO = populateDietTable()
                //Act & Assert
                assertEquals(null, dietDAO.findByDietId(4))
            }
        }

        @Test
        fun `get diet by diet id that exists, results in a correct diet returned`() {
            transaction {
                //Arrange - create and populate tables with three users and three diets
                val userDAO = populateUserTable()
                val dietDAO = populateDietTable()
                //Act & Assert
                assertEquals(diet1, dietDAO.findByDietId(1))
                assertEquals(diet2, dietDAO.findByDietId(2))
            }
        }
    }
    @Nested
    inner class UpdateDiets {

        @Test
        fun `updating existing diet in table results in successful update`() {
            transaction {

                //Arrange - create and populate tables with three users and three activities
                val userDAO = populateUserTable()
                val dietDAO = populateDietTable()

                //Act & Assert
                val diet3updated = Diet(
                    id = 3, food = "Chicken",
                    calories = 220, userId = 2
                )
                dietDAO.updateByDietId(diet3updated.id, diet3updated)
                assertEquals(diet3updated, dietDAO.findByDietId(3))
            }
        }
    }
    @Nested
    inner class DeleteDiets {

        @Test
        fun `deleting a non-existent diet (by id) in table results in no deletion`() {
            transaction {

                //Arrange - create and populate tables with three users and three diets
                val userDAO = populateUserTable()
                val dietDAO = populateDietTable()

                //Act & Assert
                assertEquals(3, dietDAO.getAll().size)
                dietDAO.deleteByDietId(4)
                assertEquals(3, dietDAO.getAll().size)
            }
        }
        @Test
        fun `deleting an existing diet (by id) in table results in record being deleted`() {
            transaction {

                //Arrange - create and populate tables with three users and three diets
                val userDAO = populateUserTable()
                val dietDAO = populateDietTable()

                //Act & Assert
                assertEquals(3, dietDAO.getAll().size)
                dietDAO.deleteByDietId(diet3.id)
                assertEquals(2, dietDAO.getAll().size)
            }
        }
        @Test
        fun `deleting diets when none exist for user id results in no deletion`() {
            transaction {

                //Arrange - create and populate tables with three users and three diets
                val userDAO = populateUserTable()
                val dietDAO = populateDietTable()

                //Act & Assert
                assertEquals(3, dietDAO.getAll().size)
                dietDAO.deleteByUserId(3)
                assertEquals(3, dietDAO.getAll().size)
            }
        }
        @Test
        fun `deleting diets when 1 or more exist for user id results in deletion`() {
            transaction {

                //Arrange - create and populate tables with three users and three activities
                val userDAO = populateUserTable()
                val dietDAO = populateDietTable()

                //Act & Assert
                assertEquals(3, dietDAO.getAll().size)
                dietDAO.deleteByUserId(1)
                assertEquals(1, dietDAO.getAll().size)
            }
        }
    }
}