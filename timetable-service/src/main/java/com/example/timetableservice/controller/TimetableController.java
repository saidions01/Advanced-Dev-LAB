package com.example.timetableservice.controller;


import com.example.timetableservice.model.Seance;
import com.example.timetableservice.model.Timetable;
import com.example.timetableservice.service.TimetableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/timetable")
public class TimetableController {

    private final TimetableService timetableService;

    @Autowired
    public TimetableController(TimetableService timetableService) {
        this.timetableService = timetableService;
    }

    // Create a new timetable
    @PostMapping
    public Mono<Timetable> createTimetable(@RequestBody Timetable timetable) {
        return timetableService.createTimetable(timetable);
    }

    // Get all timetables
    @GetMapping
    public Flux<Timetable> getAllTimetables() {
        return timetableService.getAllTimetables();
    }

    // Get a timetable by ID
    @GetMapping("/{id}")
    public Mono<Timetable> getTimetableById(@PathVariable Long id) {
        return timetableService.getTimetable(id);
    }

    // Add a new Séance to a Timetable
    @PostMapping("/{timetableId}/seances")
    public Mono<Timetable> addSeanceToTimetable(@PathVariable Long timetableId, @RequestBody Seance seance) {
        return timetableService.addSeanceToTimetable(timetableId, seance);
    }

    // Get all Séances for a specific Timetable
    @GetMapping("/{timetableId}/seances")
    public Flux<Seance> getSeancesForTimetable(@PathVariable Long timetableId) {
        return timetableService.getSeancesForTimetable(timetableId);
    }

    // Update a Séance in a Timetable
    @PutMapping("/{timetableId}/seances/{seanceId}")
    public Mono<Timetable> updateSeanceInTimetable(@PathVariable Long timetableId, @PathVariable Long seanceId, @RequestBody Seance updatedSeance) {
        return timetableService.updateSeanceInTimetable(timetableId, seanceId, updatedSeance);
    }

    // Delete a Séance from a Timetable
    @DeleteMapping("/{timetableId}/seances/{seanceId}")
    public Mono<Void> deleteSeanceFromTimetable(@PathVariable Long timetableId, @PathVariable Long seanceId) {
        return timetableService.deleteSeanceFromTimetable(timetableId, seanceId);
    }

}
