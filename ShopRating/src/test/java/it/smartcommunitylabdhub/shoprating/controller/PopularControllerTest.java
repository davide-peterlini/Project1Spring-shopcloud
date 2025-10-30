package it.smartcommunitylabdhub.shoprating.controller;

import it.smartcommunitylabdhub.shoprating.service.RatingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.web.client.RestTemplate;

import java.util.*;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PopularController.class)
public class PopularControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RatingService ratingService;

    @MockBean
    private RestTemplate restTemplate;

    @Test
    void testGetPopularProducts() throws Exception {
        // Mock products from catalog
        Map<String, Object> p1 = new LinkedHashMap<>();
        p1.put("id", "1");
        p1.put("title", "Product A");
        p1.put("price", 10.0);

        Map<String, Object> p2 = new LinkedHashMap<>();
        p2.put("id", "2");
        p2.put("title", "Product B");
        p2.put("price", 5.0);

        Object[] products = new Object[]{p1, p2};
        when(restTemplate.getForObject("http://catalog/api/products", Object[].class)).thenReturn(products);

        // Mock averages: only product 1 has rating
        Map<String, Object> avg1 = new HashMap<>();
        avg1.put("productId", 1L);
        avg1.put("averageRating", 4.5);
        when(ratingService.getPopularProducts()).thenReturn(Collections.singletonList(avg1));

        mockMvc.perform(get("/api/popular?page=0&size=10").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value("1"))
                .andExpect(jsonPath("$.content[0].averageRating").value(4.5))
                .andExpect(jsonPath("$.content[1].id").value("2"))
                .andExpect(jsonPath("$.content[1].averageRating").value(0.0));
    }
}
