package org.acme.exceptions.handles

import org.acme.exceptions.ResourceNotFound
import jakarta.ws.rs.core.Response
import jakarta.ws.rs.core.Response.Status
import jakarta.ws.rs.ext.ExceptionMapper
import jakarta.ws.rs.ext.Provider

@Provider
class ResourceNotFoundHandle : ExceptionMapper<ResourceNotFound> {

    /*
     * You can customize the response.
     */
    override fun toResponse(exception: ResourceNotFound): Response {
        return Response.status(Status.INTERNAL_SERVER_ERROR)
            .entity("Sorry, something went wrong")
            .build()
    }
}