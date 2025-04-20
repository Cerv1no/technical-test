package com.cerv1no.technical_test.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class Product {

    private Long id;
    private String description;
    private int amount;
}
