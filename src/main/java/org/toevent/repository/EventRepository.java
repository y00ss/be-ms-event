package org.toevent.repository;

import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.toevent.entities.Event;

@ApplicationScoped
public class EventRepository  implements PanacheRepository<Event> {
    /**/
}
