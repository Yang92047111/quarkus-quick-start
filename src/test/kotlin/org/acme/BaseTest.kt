package org.acme

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager
import io.quarkus.test.junit.QuarkusTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.AfterAll
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.containers.wait.strategy.Wait
import org.testcontainers.junit.jupiter.Testcontainers
import java.time.Duration

@QuarkusTest
@Testcontainers
open class BaseTest: QuarkusTestResourceLifecycleManager {

    companion object {
        @Container
        val mysql = MySQLContainer("mysql:8.4").apply {
            withDatabaseName("quarkus_crud")
            withUsername("root")
            withPassword("root")
            withStartupTimeout(Duration.ofSeconds(60)) // Increase timeout if necessary
            waitingFor(Wait.forListeningPort()) // Wait until port is listening
            waitingFor(Wait.forLogMessage(".*ready for connections.*\\s", 1)) //Wait for log Message.
        }
    }

    
    override fun start(): Map<String, String> {
        mysql.start()
        return mapOf(
            "quarkus.datasource.db-kind" to "mysql",
            "quarkus.datasource.jdbc.url" to mysql.getJdbcUrl(),
            "quarkus.datasource.username" to mysql.username,
            "quarkus.datasource.password" to mysql.password,
            "quarkus.hibernate-orm.database.generation" to "drop-and-create"
        )
    }

    
    override fun stop() {
        mysql.stop()
        println("❌ MySQL Container Stopped")
    }
}