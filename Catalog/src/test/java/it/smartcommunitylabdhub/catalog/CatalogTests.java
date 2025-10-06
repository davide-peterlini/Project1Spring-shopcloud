package it.smartcommunitylabdhub.catalog;


import it.smartcommunitylabdhub.catalog.controllers.ProductController;
import it.smartcommunitylabdhub.catalog.services.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@WebMvcTest(ProductController.class)
class CatalogTests {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    ProductService productService;

    @Test
    void testOneProduct() throws Exception {
        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testProductList() throws Exception {
        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json("[]"));
    }
}