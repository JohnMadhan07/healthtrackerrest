package ie.setu.config
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.name
class DbConfig{
    fun getDbConnection() :Database{

        val PGHOST = "rain.db.elephantsql.com"
        val PGPORT = "5432"
        val PGUSER = "bxenlqpi"
        val PGPASSWORD = "O1FMtiGZtkjfPFJdbYcAKm2_rbbjxOyY"
        val PGDATABASE = "bxenlqpi"

        //url format should be jdbc:postgresql://host:port/database
        val dbUrl = "jdbc:postgresql://$PGHOST:$PGPORT/$PGDATABASE"

        val dbConfig = Database.connect(
            url = dbUrl,
            driver="org.postgresql.Driver",
            user = PGUSER,
            password = PGPASSWORD
        )
        //logger.info{"DbConfig name = " + dbConfig.name}
        //logger.info{"DbConfig url = " + dbConfig.url}
        return dbConfig
    }
}