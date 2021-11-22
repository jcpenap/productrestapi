package com.bharath.springweb.controllers;

import com.bharath.springweb.entities.Product;
import com.bharath.springweb.repositories.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductRepository repository;

    @Test
    void getProducts() throws Exception {

        Product product = new Product();
        product.setId(1);
        product.setName("MacBook");
        product.setDescription("Its Awesome");
        product.setPrice(5000);

        List<Product> products = Arrays.asList(product);
        when(repository.findAll()).thenReturn(products);

        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();

        mockMvc.perform(get("/productapi/products/").contextPath("/productapi"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectWriter.writeValueAsString(products)));
    }

    @Test
    void createProducts() throws Exception {

        Product product = new Product();
        product.setId(1);
        product.setName("MacBook");
        product.setDescription("Its Awesome");
        product.setPrice(5000);

        when(repository.save(any())).thenReturn(product);

        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();

        mockMvc.perform(post("/productapi/products/").contextPath("/productapi")
                .contentType(MediaType.APPLICATION_JSON).content(objectWriter.writeValueAsString(product)))
                .andExpect(status().isOk());

    }

    @Test
    void updateProduct() throws Exception {

        Product product = new Product();
        product.setId(1);
        product.setName("MacBook");
        product.setDescription("Its Awesome");
        product.setPrice(3000);

        when(repository.save(any())).thenReturn(product);

        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();

        mockMvc.perform(put("/productapi/products/").contextPath("/productapi")
                        .contentType(MediaType.APPLICATION_JSON).content(objectWriter.writeValueAsString(product)))
                .andExpect(status().isOk());

    }

    @Test
    void deleteProduct() throws Exception {
        doNothing().when(repository).deleteById(1);
        mockMvc.perform(delete("/productapi/products/1").contextPath("/productapi"))
                .andExpect(status().isOk());
    }


}