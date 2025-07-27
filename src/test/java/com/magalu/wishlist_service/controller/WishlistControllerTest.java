package com.magalu.wishlist_service.controller;


import com.fasterxml.jackson.databind.ObjectMapper;

import com.magalu.wishlist_service.dto.ProductDTO;
import com.magalu.wishlist_service.model.Product;
import com.magalu.wishlist_service.model.Wishlist;
import com.magalu.wishlist_service.service.WishlistService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WishlistController.class)
class WishlistControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private WishlistService wishlistService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveAdicionarProdutoNaWishlist() throws Exception {
        String customerId = "c123";
        ProductDTO productDTO = new ProductDTO("p1", "Produto 1");
        Product product = new Product("p1", "Produto 1");
        Wishlist wishlist = new Wishlist(customerId, List.of(product));

        when(wishlistService.addProduct(customerId, product)).thenReturn(wishlist);

        mockMvc.perform(post("/wishlist/{customerId}", customerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value("c123"))
                .andExpect(jsonPath("$.products[0].id").value("p1"))
                .andExpect(jsonPath("$.products[0].name").value("Produto 1"));
    }

    @Test
    void deveRemoverProdutoDaWishlist() throws Exception {
        String customerId = "c123";
        String productId = "p1";
        Wishlist wishlist = new Wishlist(customerId, new ArrayList<>());

        when(wishlistService.removeProduct(customerId, productId)).thenReturn(wishlist);

        mockMvc.perform(delete("/wishlist/{customerId}/{productId}", customerId, productId))
                .andExpect(status().isOk());
    }

    @Test
    void deveListarProdutosDaWishlist() throws Exception {
        String customerId = "c123";
        List<Product> products = List.of(new Product("p1", "Produto 1"), new Product("p2", "Produto 2"));

        when(wishlistService.getProducts(customerId)).thenReturn(products);

        mockMvc.perform(get("/wishlist/{customerId}", customerId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value("p1"))
                .andExpect(jsonPath("$[1].id").value("p2"));
    }

    @Test
    void deveVerificarSeProdutoExisteNaWishlist() throws Exception {
        String customerId = "c123";
        String productId = "p1";

        when(wishlistService.hasProduct(customerId, productId)).thenReturn(true);

        mockMvc.perform(get("/wishlist/{customerId}/{productId}", customerId, productId))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }
}
