package com.cerv1no.technical_test.repositories;

import com.cerv1no.technical_test.model.Cart;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;


@Repository
public class CartRepository {

    private final Map<String, Cart> carts = new ConcurrentHashMap<>();

    public Optional<Cart> findById(String cartId) {
        return Optional.ofNullable(carts.get(cartId));
    }

    public String save(Cart cart) {
        carts.putIfAbsent(cart.getId(), cart);
        return cart.getId();
    }

    public void deleteById(String id) {
        carts.remove(id);
    }

    public List<Cart> findAll() {
        return carts.values().stream().toList();
    }
}
