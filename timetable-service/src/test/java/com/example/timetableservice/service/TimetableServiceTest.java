package com.example.timetableservice.service;

import com.example.timetableservice.model.Seance;
import com.example.timetableservice.model.Timetable;
import com.example.timetableservice.repository.TimetableRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class TimetableServiceTest {

    private final TimetableRepository repository = Mockito.mock(TimetableRepository.class);
    private final TimetableService service = new TimetableService(repository);

    @Test
    void addSeanceToTimetable() {
        // Create a Timetable
        Timetable timetable = new Timetable();
        timetable.setId(1L);
        timetable.setSeances(new ArrayList<>()); // Initialize the seances list

        // Create a Seance
        Seance seance = new Seance();
        seance.setId(1L);
        seance.setSubject("Math");

        // Mock Repository Behavior
        when(repository.findById(1L)).thenReturn(Mono.just(timetable));
        when(repository.save(any())).thenReturn(Mono.just(timetable));

        // Test the Method
        Mono<Timetable> result = service.addSeanceToTimetable(1L, seance);

        // Verify the Result
        StepVerifier.create(result)
                .expectNextMatches(updatedTimetable ->
                        updatedTimetable.getSeances().size() == 1 &&
                                "Math".equals(updatedTimetable.getSeances().get(0).getSubject()))
                .verifyComplete();
    }
}
