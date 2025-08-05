package com.magalu.wishlist_service.service;

import com.magalu.wishlist_service.exception.WishlistLimitExceededException;
import com.magalu.wishlist_service.exception.WishlistNotFoundException;
import com.magalu.wishlist_service.model.Product;
import com.magalu.wishlist_service.model.Wishlist;
import com.magalu.wishlist_service.repository.WishlistRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class WishlistService {

    private static final int MAX_PRODUCTS = 20;

    @Autowired
    private WishlistRepository wishlistRepository;

    public Wishlist addProduct(String customerId, Product product) {
        log.info("Adicionando produto na Wishlist {}", product.getName());
        Wishlist wishlist = wishlistRepository.findById(customerId)
                .orElse(new Wishlist(customerId, new ArrayList<>()));
        Wishlist wishlist = wishlistRepository.findById(customerId).orElse(new Wishlist(customerId, new ArrayList<>()));

        if (wishlist.getProducts().size() >= MAX_PRODUCTS) {
            throw new WishlistLimitExceededException();
        }

        boolean alreadyInWishlist = wishlist.getProducts()
                .stream()
                .anyMatch(p -> p.getId().equals(product.getId()));

        if (!alreadyInWishlist) {
            wishlist.getProducts().add(product);
            return wishlistRepository.save(wishlist);
        }

        return wishlist;

    }

    public Wishlist removeProduct(String customerId, String productId) {
        Wishlist wishlist = getWishlistOrThrow(customerId);
        wishlist.getProducts().removeIf(p -> p.getId().equals(productId));
        return wishlistRepository.save(wishlist);
    }

    public List<Product> getProducts(String customerId) {
        Wishlist wishlist = getWishlistOrThrow(customerId);
        return wishlist.getProducts();
    }

    public boolean hasProduct(String customerId, String productId) {
        Wishlist wishlist = getWishlistOrThrow(customerId);
        return wishlist.getProducts().stream().anyMatch(p -> p.getId().equals(productId));
    }

    private Wishlist getWishlistOrThrow(String customerId) {
        return wishlistRepository.findById(customerId)
                .orElseThrow(() -> new WishlistNotFoundException("Wishlist não encontrada para o cliente: " + customerId));
    }
}
