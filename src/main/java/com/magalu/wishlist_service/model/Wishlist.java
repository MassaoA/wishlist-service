package com.magalu.wishlist_service.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@Document(collection = "wishlists")
public class Wishlist {

    @Id
    private String customerId;
    private List<Product> products;
}
