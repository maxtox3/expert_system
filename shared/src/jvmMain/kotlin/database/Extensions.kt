package database

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction

fun <T> database(statement: Transaction.() -> T): T {
    Database.connect(
        url = "jdbc:mysql://localhost:3306/expert_system?characterEncoding=utf8&useUnicode=true",
        driver = "com.mysql.jdbc.Driver",
        user = "root",
        password = ""
    )

    return transaction {
//        addLogger(StdOutSqlLogger)
        statement()
    }
}