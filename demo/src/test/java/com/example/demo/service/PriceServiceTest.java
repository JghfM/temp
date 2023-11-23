package com.example.demo.service;

import com.example.demo.exception.ElementNotFoundException;
import com.example.demo.exception.ElementParsingException;
import com.example.demo.repository.IPriceRepo;
import com.example.demo.service.service.impl.MessageProvider;
import com.example.demo.service.service.impl.PriceServiceImpl;
import com.example.demo.entity.Price;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PriceServiceTest {

    private IPriceRepo priceRepository;
    private IPriceService priceService;

    List<Price> expectedPrices;
    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        priceRepository = Mockito.mock(IPriceRepo.class);
        MessageProvider messageProvider = Mockito.mock(MessageProvider.class);
        priceService = new PriceServiceImpl(priceRepository, messageProvider);
    }

    @BeforeAll
    public void load() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File jsonFile = new File(Objects.requireNonNull(classLoader.getResource("data.json")).getFile());

        expectedPrices = objectMapper.readValue(jsonFile, objectMapper.getTypeFactory().constructCollectionType(List.class, Price.class));
    }

    @Test
    void whenPriceExistsShouldBeAbleToFindByCriteria_Test1() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        LocalDateTime date = LocalDateTime.parse("2020-06-14 10:00:00", formatter);
        when(priceRepository.findByCriteria(eq(1L), eq(35455L), eq(date))).thenReturn(List.of(expectedPrices.get(0)));

        List<Price> result = priceService.findPrices(1L, 35455L, "2020-06-14 10:00:00");

        assertEquals(List.of(expectedPrices.get(0)), result);
    }

    @Test
    void whenPriceExistsShouldBeAbleToFindByCriteria_Test2() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        LocalDateTime date = LocalDateTime.parse("2020-06-14 16:00:00", formatter);
        when(priceRepository.findByCriteria(eq(1L), eq(35455L), eq(date))).thenReturn(List.of(expectedPrices.get(0), expectedPrices.get(1)));

        List<Price> result = priceService.findPrices(1L, 35455L, "2020-06-14 16:00:00");

        assertEquals(List.of(expectedPrices.get(0), expectedPrices.get(1)), result);
    }

    @Test
    void whenPriceExistsShouldBeAbleToFindByCriteria_Test3() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        LocalDateTime date = LocalDateTime.parse("2020-06-14 21:00:00", formatter);
        when(priceRepository.findByCriteria(eq(1L), eq(35455L), eq(date))).thenReturn(List.of(expectedPrices.get(0)));

        List<Price> result = priceService.findPrices(1L, 35455L, "2020-06-14 21:00:00");

        assertEquals(List.of(expectedPrices.get(0)), result);
    }

    @Test
    void whenPriceExistsShouldBeAbleToFindByCriteria_Test4() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        LocalDateTime date = LocalDateTime.parse("2020-06-15 10:00:00", formatter);
        when(priceRepository.findByCriteria(eq(1L), eq(35455L), eq(date))).thenReturn(List.of(expectedPrices.get(0), expectedPrices.get(2)));

        List<Price> result = priceService.findPrices(1L, 35455L, "2020-06-15 10:00:00");

        assertEquals(List.of(expectedPrices.get(0), expectedPrices.get(2)), result);
    }

    @Test
    void whenPriceExistsShouldBeAbleToFindByCriteria_Test5() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        LocalDateTime date = LocalDateTime.parse("2020-06-16 21:00:00", formatter);
        when(priceRepository.findByCriteria(eq(1L), eq(35455L), eq(date))).thenReturn(List.of(expectedPrices.get(0), expectedPrices.get(3)));

        List<Price> result = priceService.findPrices(1L, 35455L, "2020-06-16 21:00:00");

        assertEquals(List.of(expectedPrices.get(0), expectedPrices.get(3)), result);
    }

    @Test
    void whenPriceDoesntExistsShouldThrowException() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        LocalDateTime date = LocalDateTime.parse("2028-06-15 16:00:00", formatter);

        when(priceRepository.findByCriteria(any(), any(), eq(date))).thenReturn(null);

        assertThrows(ElementNotFoundException.class, () -> {
            priceService.findPrices(1L, 1L, "2028-06-15 16:00:00");
        });
    }


    @Test
    void whenTheDateFormatIsInvalidShouldThrowException() {
        assertThrows(ElementParsingException.class, () -> {
            priceService.findPrices(1L, 1L, "Invalid Date Format");
        });
    }

}