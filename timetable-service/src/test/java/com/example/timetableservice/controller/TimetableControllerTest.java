package com.example.timetableservice.controller;



import com.example.timetableservice.model.Timetable;
import com.example.timetableservice.service.TimetableService;
import org.testng.annotations.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebFluxTest(TimetableController.class)
class TimetableControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private TimetableService timetableService;

    @Test
    void createTimetable() {
        Timetable timetable = new Timetable();
        timetable.setId(1L);

        when(timetableService.createTimetable(any())).thenReturn(Mono.just(timetable));

        webTestClient.post()
                .uri("/timetables")
                .bodyValue(timetable)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(1L);
    }
}
