package org.acme.integration.controllers

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.hamcrest.CoreMatchers.equalTo
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.acme.BaseTest

@QuarkusTest
@TestMethodOrder(OrderAnnotation::class)
class NoteResourceIT : BaseTest() {

    private val noteJson = """
        {
            "title": "Test Note",
            "content": "This is a test note"
        }
    """.trimIndent()

    @Test
    @Order(1)
    fun `should create a new note`() {
        given()
            .contentType(ContentType.JSON)
            .body(noteJson)
            .post("/api/notes")
            .then()
            .statusCode(201)
            .body("title", equalTo("Test Note"))
            .body("content", equalTo("This is a test note"))
    }

    @Test
    @Order(2)
    fun `should get a note by id`() {
        given()
            .get("/api/notes/1")
            .then()
            .statusCode(200)
            .body("title", equalTo("Test Note"))
    }

    @Test
    @Order(3)
    fun `should update a note`() {
        val updatedJson = """
            {
                "title": "Updated Note",
                "content": "Updated content"
            }
        """.trimIndent()

        given()
            .contentType(ContentType.JSON)
            .body(updatedJson)
            .put("/api/notes/1")
            .then()
            .statusCode(200)
            .body("title", equalTo("Updated Note"))
    }

    @Test
    @Order(4)
    fun `should delete a note`() {
        given()
            .delete("/api/notes/1")
            .then()
            .statusCode(204)
    }
}
