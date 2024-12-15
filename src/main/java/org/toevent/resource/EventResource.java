package org.toevent.resource;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.toevent.entities.Event;
import org.toevent.service.EventService;

import java.util.List;

@Path("/events")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EventResource {

    private final EventService eventService;

    public EventResource(EventService eventService) {
        this.eventService = eventService;
    }

    /**
     * Create a new event
     */
    @POST
    public Uni<Response> createEvent(Event event) {
        return eventService.createEvent(event)
                .onItem().transform(createdEvent -> Response.ok(createdEvent).status(Response.Status.CREATED).build());
    }

    /**
     * Get all events
     */
    @GET
    public Uni<List<Event>> getAllEvents() {
        return eventService.getAllEvents();
    }

    /**
     * Get an event by ID
     */
    @GET
    @Path("/{id}")
    public Uni<Response> getEventById(@PathParam("id") String id) {
        return eventService.getEventById(id)
                .onItem().transform(event -> event != null ? Response.ok(event).build() : Response.status(Response.Status.NOT_FOUND).build());
    }

    /**
     * Update an existing event
     */
    @PUT
    @Path("/{id}")
    public Uni<Response> updateEvent(@PathParam("id") String id, Event event) {
        return eventService.updateEvent(id, event)
                .onItem().transform(updated -> updated ? Response.ok().build() : Response.status(Response.Status.NOT_FOUND).build());
    }

    /**
     * Delete an event by ID
     */
    @DELETE
    @Path("/{id}")
    public Uni<Response> deleteEvent(@PathParam("id") String id) {
        return eventService.deleteEvent(id)
                .onItem().transform(deleted -> deleted ? Response.noContent().build() : Response.status(Response.Status.NOT_FOUND).build());
    }
}