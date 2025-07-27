package com.magalu.wishlist_service.controller;

import com.magalu.wishlist_service.dto.ProductDTO;
import com.magalu.wishlist_service.dto.WishlistResponseDTO;
import com.magalu.wishlist_service.mapper.WishlistMapper;
import com.magalu.wishlist_service.model.Product;
import com.magalu.wishlist_service.model.Wishlist;
import com.magalu.wishlist_service.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wishlist")
public class WishlistController {

    @Autowired
    private WishlistMapper wishlistMapper;

    @Autowired
    private WishlistService wishlistService;
    @PostMapping("/{customerId}")
    public ResponseEntity<WishlistResponseDTO> addProduct(@PathVariable String customerId, @RequestBody ProductDTO productDTO) {
        Product product = wishlistMapper.toEntity(productDTO);
        Wishlist wishlist = wishlistService.addProduct(customerId, product);
        return ResponseEntity.ok(wishlistMapper.toResponseDTO(wishlist));
    }

    @DeleteMapping("/{customerId}/{productId}")
    public ResponseEntity<WishlistResponseDTO> removeProduct(@PathVariable String customerId, @PathVariable String productId) {
        Wishlist wishlist = wishlistService.removeProduct(customerId, productId);
        return ResponseEntity.ok(wishlistMapper.toResponseDTO(wishlist));
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<List<ProductDTO>> getProducts(@PathVariable String customerId) {
        List<Product> products = wishlistService.getProducts(customerId);
        return ResponseEntity.ok(wishlistMapper.toDTOList(products));
    }

    @GetMapping("/{customerId}/{productId}")
    public ResponseEntity<Boolean> hasProduct(@PathVariable String customerId, @PathVariable String productId) {
        return ResponseEntity.ok(wishlistService.hasProduct(customerId, productId));
    }
}