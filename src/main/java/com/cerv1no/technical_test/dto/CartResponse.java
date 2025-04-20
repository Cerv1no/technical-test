package com.cerv1no.technical_test.dto;

import java.util.List;

public record CartResponse (
    String id,
    List<ProductDto> products
) {
}
