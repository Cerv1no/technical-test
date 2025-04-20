package com.cerv1no.technical_test.service;

import com.cerv1no.technical_test.dto.AddProductsResponse;
import com.cerv1no.technical_test.dto.CartResponse;
import com.cerv1no.technical_test.dto.FailedProductDto;
import com.cerv1no.technical_test.dto.ProductDto;
import com.cerv1no.technical_test.exception.ResourceNotFoundException;
import com.cerv1no.technical_test.mapper.CartMapper;
import com.cerv1no.technical_test.model.Cart;
import com.cerv1no.technical_test.model.Product;
import com.cerv1no.technical_test.repositories.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Service
public class CartService {

    private final CartRepository cartRepository;
    private final CartMapper cartMapper;

    public CartResponse getCartById(String cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart with id " + cartId + " not found"));
        cart.setLastActivity(Instant.now());
        return cartMapper.toCartResponse(cart);
    }

    public String createCart() {
        return cartRepository.save(new Cart());
    }

    public AddProductsResponse addProducts(String cartId, List<ProductDto> request) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart with id " + cartId + " not found"));

        List<FailedProductDto> failedProducts = new ArrayList<>();

        for(ProductDto newProduct : request) {
            // Check if the product is already in the cart
            if (cart.getProducts().containsKey(newProduct.id())) {
                Product existing = cart.getProducts().get(newProduct.id());

                // If the description is the same, update the amount
                if (existing.getDescription().equals(newProduct.description()))
                    existing.setAmount(existing.getAmount() + newProduct.amount());
                // If the description is different, add the product to invalid products list
                else
                    failedProducts.add(new FailedProductDto(newProduct.id(), newProduct.description()));
            }
            else
                cart.getProducts().put(newProduct.id(), cartMapper.toProduct(newProduct));

        }
        cart.setLastActivity(Instant.now());

        return new AddProductsResponse(cartMapper.toCartResponse(cart), failedProducts);
    }


    public void deleteCart(String cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart with id " + cartId + " not found"));
        cartRepository.deleteById(cart.getId());
    }

    @Scheduled(fixedRate = 30000)
    public void deleteExpiredCarts() {
        cartRepository.findAll().forEach(cart -> {
            if (cart.getLastActivity().isBefore(Instant.now().minusSeconds(600))) {
                cartRepository.deleteById(cart.getId());
            }
        });
    }
}
