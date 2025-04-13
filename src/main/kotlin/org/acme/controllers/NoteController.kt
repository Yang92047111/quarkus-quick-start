package org.acme.controllers

import org.acme.models.Note
import org.acme.services.NoteService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import jakarta.ws.rs.core.Response.Status

@Path("/api")
class AppResource @Inject constructor(
    private val noteService: NoteService
) {

    @Path("/notes")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun createNote(note: Note): Response {
        val createdNote = noteService.createNote(note)
        return Response.status(Status.CREATED).entity(createdNote).build()
    }

    @Path("/notes/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun updateNote(@PathParam("id") noteId: Int, noteToUpdate: Note): Response {
        val updatedNote = noteService.updateNote(noteId, noteToUpdate)
        return Response.status(Status.OK).entity(updatedNote).build()
    }

    @Path("/notes/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun getNote(@PathParam("id") noteId: Int): Response {
        val note = noteService.getNote(noteId)
        return Response.status(Status.OK).entity(note).build()
    }

    @Path("/notes/{id}")
    @DELETE
    @Produces(MediaType.TEXT_PLAIN)
    fun deleteNote(@PathParam("id") noteId: Int): Response {
        noteService.deleteNote(noteId)
        return Response.status(Status.NO_CONTENT).build()
    }
}