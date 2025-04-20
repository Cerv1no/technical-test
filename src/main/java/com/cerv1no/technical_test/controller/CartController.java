package com.cerv1no.technical_test.controller;

import com.cerv1no.technical_test.dto.AddProductsResponse;
import com.cerv1no.technical_test.dto.CartResponse;
import com.cerv1no.technical_test.dto.ProductDto;
import com.cerv1no.technical_test.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/carts")
@Tag(name = "Cart Controller", description = "Cart management endpoints")
public class CartController {

    private final CartService cartService;

    @Operation(
            summary = "Retrieve a cart by ID",
            description = "Fetches the cart details for the given cart ID.")
    @GetMapping("/{cartId}")
    public ResponseEntity<CartResponse> getCartById(@PathVariable String cartId) {
        return ResponseEntity.ok(cartService.getCartById(cartId));
    }

    @Operation(
            summary = "Create a new cart",
            description = "Initializes a new cart and returns its ID.")
    @PostMapping
    public ResponseEntity<String> createCart() {
        return ResponseEntity.ok(cartService.createCart());
    }

    @Operation(
            summary = "Add products to a cart",
            description = "Adds a list of products to the specified cart.")
    @PutMapping("/{cartId}/products")
    public ResponseEntity<AddProductsResponse> addProducts(@PathVariable String cartId, @RequestBody List<@Valid ProductDto> request) {
        return ResponseEntity.ok(cartService.addProducts(cartId, request));
    }


    @Operation(
            summary = "Delete a cart by ID",
            description = "Removes the cart with the given ID.")
    @DeleteMapping("/{cartId}")
    public ResponseEntity<Void> deleteCartById(@PathVariable String cartId) {
        cartService.deleteCart(cartId);
        return ResponseEntity.noContent().build();
    }
}
