package com.enigma.spring_fikri_demo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommonResponse <T>{
    private String message;
    private Integer statusCode;
    private T data;
    private PaginationResponse pagination;

    // Constructor tanpa pagination
    public CommonResponse(String message, Integer statusCode, T data) {
        this.message = message;
        this.statusCode = statusCode;
        this.data = data;
    }

    // Constructor dengan pagination
    public CommonResponse(String message, Integer statusCode, T data, PaginationResponse pagination) {
        this.message = message;
        this.statusCode = statusCode;
        this.data = data;
        this.pagination = pagination;
    }
}