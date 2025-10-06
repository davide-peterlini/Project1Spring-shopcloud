package com.shopcloud.rating.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RatingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testAddAndGetRating() throws Exception {
        String json = """
            {"voto":5,"commento":"Ottimo prodotto!"}
        """;

        mockMvc.perform(post("/api/ratings/1/10")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.voto").value(5))
                .andExpect(jsonPath("$.commento").value("Ottimo prodotto!"));

        mockMvc.perform(get("/api/ratings/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].voto").value(5));
    }

    @Test
    void testGetPopularProducts() throws Exception {
        mockMvc.perform(get("/api/popular"))
                .andExpect(status().isOk());
    }
}
