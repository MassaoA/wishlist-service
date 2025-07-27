package com.magalu.wishlist_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WishlistResponseDTO {
    private String customerId;
    private List<ProductDTO> products;
}