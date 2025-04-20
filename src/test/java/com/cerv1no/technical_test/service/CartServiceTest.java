package com.cerv1no.technical_test.service;

import com.cerv1no.technical_test.dto.AddProductsResponse;
import com.cerv1no.technical_test.dto.CartResponse;
import com.cerv1no.technical_test.dto.ProductDto;
import com.cerv1no.technical_test.exception.ResourceNotFoundException;
import com.cerv1no.technical_test.mapper.CartMapper;
import com.cerv1no.technical_test.model.Cart;
import com.cerv1no.technical_test.model.Product;
import com.cerv1no.technical_test.repositories.CartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private CartMapper cartMapper;

    @InjectMocks
    private CartService cartService;

    private Cart cart;
    private CartResponse cartResponse;

    @BeforeEach
    void setUp() {
        cart = new Cart();
        cart.setId("test-cartId");
        cart.setLastActivity(Instant.now());
        cart.setProducts(new ConcurrentHashMap<>());
    }

    @Test
    void shouldGetCartByIdSuccessfully() {
        when(cartRepository.findById("test-cartId")).thenReturn(Optional.of(cart));
        when(cartMapper.toCartResponse(cart)).thenReturn(cartResponse);

        CartResponse result = cartService.getCartById("test-cartId");

        assertEquals(cartResponse, result);
        verify(cartRepository).findById("test-cartId");
        verify(cartMapper).toCartResponse(cart);
    }

    @Test
    void shouldThrowExceptionWhenCartNotFound() {
        when(cartRepository.findById("inexistentId")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> cartService.getCartById("inexistentId"));
        verify(cartRepository).findById("inexistentId");
    }

    @Test
    void shouldCreateCartSuccessfully() {
        when(cartRepository.save(any(Cart.class))).thenReturn("new-cartId");

        String result = cartService.createCart();

        assertEquals("new-cartId", result);
        verify(cartRepository).save(any(Cart.class));
    }

    @Test
    void shouldAddProductsSuccessfully() {
        ProductDto productDto = new ProductDto(1L, "Product 1", 2);
        Product product = new Product();
        product.setId(1L);
        product.setDescription("Product 1");
        product.setAmount(2);

        when(cartRepository.findById("test-cartId")).thenReturn(Optional.of(cart));
        when(cartMapper.toProduct(productDto)).thenReturn(product);
        when(cartMapper.toCartResponse(cart)).thenReturn(cartResponse);

        AddProductsResponse response = cartService.addProducts("test-cartId", List.of(productDto));

        assertEquals(cartResponse, response.cart());
        assertTrue(response.failedProducts().isEmpty());
        verify(cartRepository).findById("test-cartId");
        verify(cartMapper).toProduct(productDto);
        verify(cartMapper).toCartResponse(cart);
    }

    @Test
    void shouldNotAddDuplicateProductsSuccessfully() {
        ProductDto productDto = new ProductDto(1L, "Product 1", 2);
        Product existingProduct = new Product(1L, "Product 1", 3);
        cart.getProducts().put(1L, existingProduct);
        Product product = new Product();
        product.setId(1L);
        product.setDescription("Product 1");
        product.setAmount(2);


        when(cartRepository.findById("test-cartId")).thenReturn(Optional.of(cart));
        when(cartMapper.toCartResponse(cart)).thenReturn(cartResponse);

        AddProductsResponse response = cartService.addProducts("test-cartId", List.of(productDto));

        assertEquals(5, cart.getProducts().get(1L).getAmount());
        assertTrue(response.failedProducts().isEmpty());
        verify(cartRepository).findById("test-cartId");
        verify(cartMapper).toCartResponse(cart);
    }

    @Test
    void shouldDeleteCartSuccessfully() {
        when(cartRepository.findById("test-cartId")).thenReturn(Optional.of(cart));

        cartService.deleteCart("test-cartId");

        verify(cartRepository).findById("test-cartId");
        verify(cartRepository).deleteById("test-cartId");
    }

    @Test
    void shouldDeleteExpiredCartsSuccessfully() {
        Cart expiredCart = new Cart();
        expiredCart.setId("expired-cartId");
        expiredCart.setLastActivity(Instant.now().minusSeconds(601));

        Cart activeCart = new Cart();
        activeCart.setId("active-cartId");
        activeCart.setLastActivity(Instant.now());

        when(cartRepository.findAll()).thenReturn(List.of(expiredCart, activeCart));

        cartService.deleteExpiredCarts();

        verify(cartRepository).deleteById("expired-cartId");
        verify(cartRepository, never()).deleteById("active-cartId");
    }
}
