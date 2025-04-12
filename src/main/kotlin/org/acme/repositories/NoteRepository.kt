package org.acme.repositories

import org.acme.models.Note
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class NoteRepository : PanacheRepositoryBase<Note, Long>