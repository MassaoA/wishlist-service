package com.magalu.wishlist_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class WishlistLimitExceededException extends RuntimeException {
    public WishlistLimitExceededException() {
        super("Wishlist atingiu o limite de 20 produtos.");
    }
}
