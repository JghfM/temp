package com.example.demo.controller;

import com.example.demo.entity.Price;
import com.example.demo.service.service.impl.PriceServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/prices")
public class PriceController {

    @Autowired
    private final PriceServiceImpl priceService;

    @PostMapping
    public ResponseEntity<Price> save(@RequestBody final Price price) {
        return ResponseEntity.of(Optional.of(priceService.save(price)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Price> findById(@PathVariable("id") final Long id) {
        Price price = priceService.findById(id);
        return price == null ? ResponseEntity.notFound().build():ResponseEntity.ok(price);
    }

    @Operation(summary = "Endpoint to resolve the given task")
    @GetMapping("/resolveTask")
    public ResponseEntity<List<Price>> getPrices(@RequestParam("brandId") final Long brandId,
                                 @RequestParam("productId") final Long productId,
                                 @Parameter(description = "date.description")
                                 @RequestParam("date") final String date) {
        List<Price> prices = priceService.findPrices(brandId, productId, date);
        return (prices == null || prices.isEmpty())?ResponseEntity.notFound().build(): ResponseEntity.ok(prices);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") final Long id) {
        priceService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
