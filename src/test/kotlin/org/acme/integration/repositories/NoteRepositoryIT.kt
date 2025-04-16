package org.acme.integration.repository

import io.quarkus.test.junit.QuarkusTest
import jakarta.inject.Inject
import jakarta.transaction.Transactional
import org.acme.BaseTest
import org.acme.models.Note
import org.acme.repositories.NoteRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

@QuarkusTest
@Transactional
class NoteRepositoryIT: BaseTest() {

    @Inject
    lateinit var noteRepository: NoteRepository

    @Test
    fun `should persist and retrieve note`() {
        val note = Note(title = "Repo Note", content = "Repository test")
        noteRepository.persist(note)

        assertNotNull(note.id)
        val found = noteRepository.findById(note.id!!)
        assertNotNull(found)
        assertEquals("Repo Note", found?.title)
    }

    @Test
    fun `should delete note`() {
        val note = Note(title = "Delete Me", content = "to be deleted")
        noteRepository.persist(note)

        val id = note.id!!
        noteRepository.deleteById(id)

        val deleted = noteRepository.findById(id)
        assertNull(deleted)
    }
}
