package org.acme

import io.quarkus.test.junit.QuarkusTest
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.containers.wait.strategy.Wait
import java.time.Duration

@Testcontainers
open class BaseTest {

    companion object {
        @Container
        @JvmStatic
        val mysql = MySQLContainer("mysql:8.4").apply {
            withDatabaseName("container")
            withUsername("root")
            withPassword("root")
            withStartupTimeout(Duration.ofSeconds(60)) // Increase timeout if necessary
            waitingFor(Wait.forListeningPort()) // Wait until port is listening
            waitingFor(Wait.forLogMessage(".*ready for connections.*\\s", 1)) //Wait for log Message.
        }
    
        @BeforeAll
        @JvmStatic
        fun startContainer() {
            mysql.start()
            System.setProperty("quarkus.datasource.jdbc.url", mysql.jdbcUrl)
            System.setProperty("quarkus.datasource.username", mysql.username)
            System.setProperty("quarkus.datasource.password", mysql.password)
            System.setProperty("quarkus.datasource.db-kind", "mysql")
            System.setProperty("quarkus.hibernate-orm.database.generation", "drop-and-create")
            println("✅ MySQL Container Started at: ${mysql.jdbcUrl}")
        }
        
        @AfterAll
        @JvmStatic
        fun stopContainer() {
            mysql.stop()
            println("❌ MySQL Container Stopped")
        }
    }
}