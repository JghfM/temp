package com.example.demo.commons;

import lombok.Getter;

@Getter
public enum I18Constants {

    ITEM_NOT_FOUND("item.absent"),
    LIST_NOT_FOUND("list.absent"),
    DATE_DESCRIPTION("date.description"),
    DATE_PARSE_ERROR("date.parse.error");

    private String key;
    I18Constants(String key) {
        this.key = key;
    }

}
