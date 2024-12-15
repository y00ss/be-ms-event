package org.toevent.service;// Event Service

import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

import jakarta.inject.Inject;
import org.toevent.entities.Event;
import org.toevent.repository.EventRepository;

import java.util.List;

@ApplicationScoped
public class EventService {

    @Inject
    EventRepository eventRepository;

    @WithTransaction
    public Uni<Event> createEvent(Event event) {
        return eventRepository.persist(event)
                .replaceWith(event); // Restituisce l'evento dopo averlo salvato
    }

    @WithSession
    public Uni<List<Event>> getAllEvents() {
        return eventRepository.listAll();
    }

    @WithSession
    public Uni<Event> getEventById(String id) {
        return  eventRepository.findById(Long.valueOf(id));
    }

    @WithTransaction
    public Uni<Boolean> updateEvent(String id, Event updatedEvent) {

        return eventRepository.findById(Long.valueOf(id))
                .onItem().ifNotNull().transformToUni(event -> {
                    event.setName(updatedEvent.getName());
                    event.setLocation(updatedEvent.getLocation());
                    event.setDateTime(updatedEvent.getDateTime());
                    event.setDescription(updatedEvent.getDescription());
                    return eventRepository.persist(event).replaceWith(true);
                })
                .onItem().ifNull().continueWith(false);

    }

    @WithTransaction
    public Uni<Boolean> deleteEvent(String id) {
        return eventRepository.deleteById(Long.valueOf(id));

    }
}
