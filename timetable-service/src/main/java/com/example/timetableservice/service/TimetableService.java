package com.example.timetableservice.service;
import com.example.timetableservice.model.Seance;
import com.example.timetableservice.model.Timetable;
import com.example.timetableservice.repository.TimetableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TimetableService {

    private final TimetableRepository timetableRepository;

    @Autowired
    public TimetableService(TimetableRepository timetableRepository) {
        this.timetableRepository = timetableRepository;
    }

    // Create a new timetable
    public Mono<Timetable> createTimetable(Timetable timetable) {
        return timetableRepository.save(timetable);
    }

    // Get all timetables
    public Flux<Timetable> getAllTimetables() {
        return timetableRepository.findAll();
    }

    // Get a timetable by ID
    public Mono<Timetable> getTimetable(Long timetableId) {
        return timetableRepository.findById(timetableId);
    }

    // Add a new Séance to a Timetable
    public Mono<Timetable> addSeanceToTimetable(Long timetableId, Seance seance) {
        return timetableRepository.findById(timetableId)
                .flatMap(timetable -> {
                    timetable.getSeances().add(seance);
                    return timetableRepository.save(timetable);
                });
    }

    // Get all Séances for a specific Timetable
    public Flux<Seance> getSeancesForTimetable(Long timetableId) {
        return timetableRepository.findById(timetableId)
                .flatMapMany(timetable -> Flux.fromIterable(timetable.getSeances()));
    }

    // Update a Séance in a Timetable
    public Mono<Timetable> updateSeanceInTimetable(Long timetableId, Long seanceId, Seance updatedSeance) {
        return timetableRepository.findById(timetableId)
                .flatMap(timetable -> {
                    timetable.getSeances().stream()
                            .filter(seance -> seance.getId().equals(seanceId))
                            .findFirst()
                            .ifPresent(seance -> {
                                seance.setSubject(updatedSeance.getSubject());
                                seance.setDay(updatedSeance.getDay());
                                seance.setStartTime(updatedSeance.getStartTime());
                                seance.setEndTime(updatedSeance.getEndTime());
                            });
                    return timetableRepository.save(timetable);
                });
    }

    // Delete a Séance from a Timetable
    public Mono<Void> deleteSeanceFromTimetable(Long timetableId, Long seanceId) {
        return timetableRepository.findById(timetableId)
                .flatMap(timetable -> {
                    timetable.getSeances().removeIf(seance -> seance.getId().equals(seanceId));
                    return timetableRepository.save(timetable).then();
                });
    }
}
