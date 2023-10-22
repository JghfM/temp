package com.example.demo.service.service.impl;

import com.example.demo.commons.I18Constants;
import com.example.demo.entity.Price;
import com.example.demo.exception.ElementNotFoundException;
import com.example.demo.exception.ElementParsingException;
import com.example.demo.repository.IPriceRepo;
import com.example.demo.service.IPriceService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Service
@AllArgsConstructor
public class PriceServiceImpl implements IPriceService {

    @Autowired
    private final IPriceRepo priceRepo;

    @Autowired
    private final MessageProvider messageProvider;

    @Override
    public Price findById(Long id) {
        return priceRepo.findById(id).orElseThrow(()->
                new ElementNotFoundException(messageProvider.getLocalMessage(I18Constants.ITEM_NOT_FOUND.getKey(), String.valueOf(id))));
    }

    public Price save(Price price) {
        return priceRepo.save(price);

    }

    @Override
    public void delete(Long id) {
        priceRepo.deleteById(id);
    }


    public List<Price> findPrices(Long brandId, Long productId, String date) {
        LocalDateTime dateTime;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
            dateTime = LocalDateTime.parse(date, formatter);
        } catch (Exception e) {
            throw new ElementParsingException(messageProvider.getLocalMessage(I18Constants.DATE_PARSE_ERROR.getKey()));
        }
        List<Price> price = priceRepo.findByCriteria(brandId, productId, dateTime);
        if (price == null) {
            throw new ElementNotFoundException(messageProvider.getLocalMessage(I18Constants.LIST_NOT_FOUND.getKey()));
        }
        return price;
    }
}

