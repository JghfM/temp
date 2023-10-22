package com.example.demo.service;

import com.example.demo.entity.Price;

import java.util.List;

public interface IPriceService {

    public Price findById(Long id);

    public Price save(Price price);

    public void delete(Long id);

    public List<Price> findPrices(Long brandId, Long productId, String date);
}
