package ie.setu.repository

import ie.setu.domain.Supplement
import ie.setu.domain.db.Supplements
import ie.setu.domain.repository.SupplementDAO
import ie.setu.helpers.populateSupplementTable
import ie.setu.helpers.populateUserTable
import ie.setu.helpers.supplements
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class SupplementDAOTest {
    //retrieving some test data from Fixtures
    private val sup1 = supplements.get(0)
    private val sup2 = supplements.get(1)
    private val sup3 = supplements.get(2)

    companion object {
        //Make a connection to a local, in memory H2 database.
        @BeforeAll
        @JvmStatic
        internal fun setupInMemoryDatabaseConnection() {
            Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver", user = "root", password = "")
        }
    }

    @Nested
    inner class CreateSupplements {
        @Test
        fun `multiple supplements added to table can be retrieved successfully`() {
            transaction {
                //Arrange - create and populate tables with three users and three supplements
                val userDAO = populateUserTable()
                val supplementDAO = populateSupplementTable()
                //Act & Assert
                assertEquals(3, supplementDAO.getAll().size)
                assertEquals(sup1, supplementDAO.findBySupplementId(sup1.id))
                assertEquals(sup2, supplementDAO.findBySupplementId(sup2.id))
                assertEquals(sup3, supplementDAO.findBySupplementId(sup3.id))
            }
        }
    }

    @Nested
    inner class ReadSupplements {

        @Test
        fun `getting all supplements from a populated table returns all rows`() {
            transaction {
                //Arrange - create and populate tables with three users and three supplements
                val userDAO = populateUserTable()
                val supplementDAO = populateSupplementTable()
                //Act & Assert
                assertEquals(3, supplementDAO.getAll().size)
            }
        }
        @Test
        fun `get supplement by user id that has no supplements, results in no record returned`() {
            transaction {
                //Arrange - create and populate tables with three users and three diets
                val userDAO = populateUserTable()
                val supplementDAO = populateSupplementTable()
                //Act & Assert
                assertEquals(0, supplementDAO.findByUserId(3).size)
            }
        }
        @Test
        fun `get diet by user id that exists, results in correct diet(s) returned`() {
            transaction {
                //Arrange - create and populate tables with three users and three diets
                val userDAO = populateUserTable()
                val supplementDAO = populateSupplementTable()
                //Act & Assert
                assertEquals(sup1, supplementDAO.findByUserId(1).get(0))
                assertEquals(sup2, supplementDAO.findByUserId(1).get(1))
                assertEquals(sup3, supplementDAO.findByUserId(2).get(0))
            }
        }
        @Test
        fun `get all supplements over empty table returns none`() {
            transaction {
                //Arrange - create and setup dietDAO object
                SchemaUtils.create(Supplements)
                val supplementDAO = SupplementDAO()
                //Act & Assert
                assertEquals(0, supplementDAO.getAll().size)
            }
        }
        @Test
        fun `get supplement by supplement id that has no records, results in no record returned`() {
            transaction {
                //Arrange - create and populate tables with three users and three diets
                val userDAO = populateUserTable()
                val supplementDAO = populateSupplementTable()
                //Act & Assert
                assertEquals(null, supplementDAO.findBySupplementId(4))
            }
        }
        @Test
        fun `get supplement by supplement id that exists, results in a correct supplement returned`() {
            transaction {
                //Arrange - create and populate tables with three users and three diets
                val userDAO = populateUserTable()
                val supplementDAO = populateSupplementTable()
                //Act & Assert
                assertEquals(sup1, supplementDAO.findBySupplementId(1))
                assertEquals(sup2, supplementDAO.findBySupplementId(2))
            }
        }
    }
    @Nested
    inner class UpdateSupplements {

        @Test
        fun `updating existing supplement in table results in successful update`() {
            transaction {

                //Arrange - create and populate tables with three users and three activities
                val userDAO = populateUserTable()
                val supplementDAO = populateSupplementTable()

                //Act & Assert
                val sub3updated = Supplement(
                    id = 3, name = "PreWorkout",
                    about = "Pump Muscles", userId = 2
                )
                supplementDAO.updateBySupplementId(sub3updated.id, sub3updated)
                assertEquals(sub3updated, supplementDAO.findBySupplementId(3))
            }
        }
    }
    @Nested
    inner class DeleteSupplements {

        @Test
        fun `deleting a non-existent supplement (by id) in table results in no deletion`() {
            transaction {

                //Arrange - create and populate tables with three users and three diets
                val userDAO = populateUserTable()
                val supplementDAO = populateSupplementTable()

                //Act & Assert
                assertEquals(3, supplementDAO.getAll().size)
                supplementDAO.deleteBySupplementId(4)
                assertEquals(3, supplementDAO.getAll().size)
            }
        }
        @Test
        fun `deleting an existing supplement (by id) in table results in record being deleted`() {
            transaction {

                //Arrange - create and populate tables with three users and three diets
                val userDAO = populateUserTable()
                val supplementDAO = populateSupplementTable()

                //Act & Assert
                assertEquals(3, supplementDAO.getAll().size)
                supplementDAO.deleteBySupplementId(sup3.id)
                assertEquals(2, supplementDAO.getAll().size)
            }
        }
        @Test
        fun `deleting supplements when none exist for user id results in no deletion`() {
            transaction {

                //Arrange - create and populate tables with three users and three diets
                val userDAO = populateUserTable()
                val supplementDAO = populateSupplementTable()

                //Act & Assert
                assertEquals(3, supplementDAO.getAll().size)
                supplementDAO.deleteByUserId(3)
                assertEquals(3, supplementDAO.getAll().size)
            }
        }
        @Test
        fun `deleting supplements when 1 or more exist for user id results in deletion`() {
            transaction {

                //Arrange - create and populate tables with three users and three activities
                val userDAO = populateUserTable()
                val supplementDAO = populateSupplementTable()

                //Act & Assert
                assertEquals(3, supplementDAO.getAll().size)
                supplementDAO.deleteByUserId(1)
                assertEquals(1, supplementDAO.getAll().size)
            }
        }
    }

}
