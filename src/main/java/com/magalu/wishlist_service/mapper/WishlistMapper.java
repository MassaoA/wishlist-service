package com.magalu.wishlist_service.mapper;

import com.magalu.wishlist_service.dto.ProductDTO;
import com.magalu.wishlist_service.dto.WishlistResponseDTO;
import com.magalu.wishlist_service.model.Product;
import com.magalu.wishlist_service.model.Wishlist;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WishlistMapper {

    WishlistMapper INSTANCE = Mappers.getMapper(WishlistMapper.class);

    Product toEntity(ProductDTO dto);

    ProductDTO toDTO(Product product);

    List<ProductDTO> toDTOList(List<Product> products);

    WishlistResponseDTO toResponseDTO(Wishlist wishlist);
}