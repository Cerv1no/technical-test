package com.cerv1no.technical_test.model;

import lombok.Data;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class Cart {

    private String id;
    private Map<Long, Product> products;
    private Instant lastActivity;

    public Cart() {
        this.id = UUID.randomUUID().toString();
        this.products = new ConcurrentHashMap<>();
        this.lastActivity = Instant.now();
    }
}
