package com.magalu.wishlist_service.controller;

import com.magalu.wishlist_service.model.Product;
import com.magalu.wishlist_service.model.Wishlist;
import com.magalu.wishlist_service.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/wishlist")
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;

    @PostMapping("/{customerId}")
    public ResponseEntity<Wishlist> addProduct(@PathVariable String customerId, @RequestBody Product product) {
        return ResponseEntity.ok(wishlistService.addProduct(customerId, product));
    }

    @DeleteMapping("/{customerId}/{productId}")
    public ResponseEntity<Wishlist> removeProduct(@PathVariable String customerId, @PathVariable String productId) {
        return ResponseEntity.ok(wishlistService.removeProduct(customerId, productId));
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<List<Product>> getProducts(@PathVariable String customerId) {
        return ResponseEntity.ok(wishlistService.getProducts(customerId));
    }

    @GetMapping("/{customerId}/{productId}")
    public ResponseEntity<Boolean> hasProduct(@PathVariable String customerId, @PathVariable String productId) {
        return ResponseEntity.ok(wishlistService.hasProduct(customerId, productId));
    }
}