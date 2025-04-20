package com.cerv1no.technical_test.dto;

import java.util.List;

public record AddProductsResponse(
        CartResponse cart,
        List<FailedProductDto> failedProducts
) {
}
