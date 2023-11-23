package com.example.demo.controller;

import com.example.demo.entity.Price;
import com.example.demo.service.IPriceService;
import com.example.demo.service.service.impl.PriceServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PriceControllerTest {

    private PriceServiceImpl priceService;
    @Autowired
    private PriceController priceController;

    List<Price> expectedPrices;
    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        priceService = Mockito.mock(PriceServiceImpl.class);
        priceController = new PriceController(priceService);
    }

    @BeforeAll
    public void load() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File jsonFile = new File(Objects.requireNonNull(classLoader.getResource("data.json")).getFile());

        expectedPrices = objectMapper.readValue(jsonFile, objectMapper.getTypeFactory().constructCollectionType(List.class, Price.class));
    }

    @Test
    void whenRetrievingTheCorrectPricesShouldReturn200() {
        when(priceService.findPrices(eq(1L), eq(35455L), eq("2020-06-14 10:00:00"))).thenReturn(List.of(expectedPrices.get(0)));

        ResponseEntity<List<Price>> response = priceController.getPrices( 1L, 35455L,"2020-06-14 10:00:00");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(List.of(expectedPrices.get(0)), response.getBody());
    }

    @Test
    void whenRetrievingTheWrongPricesShouldReturn404() {
        when(priceService.findPrices(any(), any(), any())).thenReturn(null);

        ResponseEntity<List<Price>> response = priceController.getPrices( 1L, 1L,"2028-06-15 16:00:00");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
