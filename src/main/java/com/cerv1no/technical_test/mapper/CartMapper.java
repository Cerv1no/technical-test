package com.cerv1no.technical_test.mapper;

import com.cerv1no.technical_test.dto.CartResponse;
import com.cerv1no.technical_test.dto.ProductDto;
import com.cerv1no.technical_test.model.Cart;
import com.cerv1no.technical_test.model.Product;
import org.springframework.stereotype.Service;

@Service
public class CartMapper {

    public CartResponse toCartResponse(Cart cart) {
        return new CartResponse(
                cart.getId(),
                cart.getProducts().values().stream().map(this::toProductDto).toList()
        );
    }

    public ProductDto toProductDto(Product product) {
        return new ProductDto(
                product.getId(),
                product.getDescription(),
                product.getAmount()
        );
    }

    public Product toProduct(ProductDto productDto) {
        return new Product(
                productDto.id(),
                productDto.description(),
                productDto.amount()
        );
    }
}
