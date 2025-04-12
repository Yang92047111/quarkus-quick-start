package org.acme.services

import org.acme.models.Note

interface NoteService {
    fun createNote(note: Note): Note
    fun updateNote(noteId: Int, noteToUpdate: Note): Note
    fun getNote(noteId: Int): Note
    fun deleteNote(noteId: Int)
}