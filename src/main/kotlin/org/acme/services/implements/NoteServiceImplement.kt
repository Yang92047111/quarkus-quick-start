package org.acme.services.implements

import org.acme.exceptions.ResourceNotFound
import org.acme.models.Note
import org.acme.repositories.NoteRepository
import org.acme.services.NoteService
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.transaction.Transactional

@ApplicationScoped
class NoteServiceImpl @Inject constructor(
    private val noteRepo: NoteRepository
) : NoteService {

    @Transactional
    override fun createNote(note: Note): Note {
        noteRepo.persist(note)
        return note
    }

    @Transactional
    override fun updateNote(noteId: Int, noteToUpdate: Note): Note {
        val note = findNoteById(noteId.toLong())
        note.content = noteToUpdate.content
        note.title = noteToUpdate.title
        noteRepo.persistAndFlush(note)
        return note
    }

    override fun getNote(noteId: Int): Note {
        return findNoteById(noteId.toLong())
    }

    @Transactional
    override fun deleteNote(noteId: Int) {
        val note = findNoteById(noteId.toLong())
        noteRepo.delete(note)
    }

    private fun findNoteById(noteId: Long): Note {
        return noteRepo.findByIdOptional(noteId)
            .orElseThrow { ResourceNotFound("Note", "note id: $noteId") }
    }
}