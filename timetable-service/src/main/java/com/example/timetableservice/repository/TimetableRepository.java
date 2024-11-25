package com.example.timetableservice.repository;

import com.example.timetableservice.model.Timetable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimetableRepository extends ReactiveCrudRepository<Timetable, Long> {
}
