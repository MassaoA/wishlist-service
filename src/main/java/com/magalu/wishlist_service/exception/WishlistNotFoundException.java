package com.magalu.wishlist_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class WishlistNotFoundException extends RuntimeException {
    public WishlistNotFoundException(String customerId) {
        super("Wishlist não encontrada para o cliente: " + customerId);
    }
}