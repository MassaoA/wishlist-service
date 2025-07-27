package com.magalu.wishlist_service.service;

import com.magalu.wishlist_service.exception.WishlistLimitExceededException;
import com.magalu.wishlist_service.exception.WishlistNotFoundException;
import com.magalu.wishlist_service.model.Product;
import com.magalu.wishlist_service.model.Wishlist;
import com.magalu.wishlist_service.repository.WishlistRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


class WishlistServiceTest {

	@InjectMocks
	private WishlistService wishlistService;

	@Mock
	private WishlistRepository wishlistRepository;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void deveAdicionarProdutoQuandoMenorQue20() {
		String customerId = "c123";
		Product product = new Product("p1", "Produto 1");
		Wishlist wishlist = new Wishlist(customerId, new ArrayList<>());

		when(wishlistRepository.findById(customerId)).thenReturn(Optional.of(wishlist));
		when(wishlistRepository.save(any())).thenAnswer(i -> i.getArgument(0));

		Wishlist result = wishlistService.addProduct(customerId, product);

		assertThat(result.getProducts()).containsExactly(product);
	}

	@Test
	void naoDeveAdicionarProdutoSeJaExistirNaWishlist() {
		String customerId = "c123";
		Product product = new Product("p1", "Produto 1");
		Wishlist wishlist = new Wishlist(customerId, new ArrayList<>(List.of(product)));

		when(wishlistRepository.findById(customerId)).thenReturn(Optional.of(wishlist));

		Wishlist result = wishlistService.addProduct(customerId, product);

		assertThat(result.getProducts()).hasSize(1); // não duplicou
		verify(wishlistRepository, never()).save(any()); // nem chamou save
	}

	@Test
	void deveLancarExcecaoQuandoWishlistAtingeLimite() {
		String customerId = "c123";
		List<Product> products = new ArrayList<>();
		for (int i = 0; i < 20; i++) {
			products.add(new Product("p" + i, "Produto " + i));
		}

		Wishlist wishlist = new Wishlist(customerId, products);
		Product newProduct = new Product("p21", "Produto 21");

		when(wishlistRepository.findById(customerId)).thenReturn(Optional.of(wishlist));

		assertThatThrownBy(() -> wishlistService.addProduct(customerId, newProduct))
				.isInstanceOf(WishlistLimitExceededException.class);
	}

	@Test
	void deveRemoverProdutoDaWishlist() {
		String customerId = "c123";
		Product product = new Product("p1", "Produto 1");
		Wishlist wishlist = new Wishlist(customerId, new ArrayList<>(List.of(product)));

		when(wishlistRepository.findById(customerId)).thenReturn(Optional.of(wishlist));
		when(wishlistRepository.save(any())).thenAnswer(i -> i.getArgument(0));

		Wishlist result = wishlistService.removeProduct(customerId, product.getId());

		assertThat(result.getProducts()).isEmpty();
	}

	@Test
	void deveRetornarExceptionSeWishlistNaoEncontrada() {
		String customerId = "inexistente";
		when(wishlistRepository.findById(customerId)).thenReturn(Optional.empty());

		assertThatThrownBy(() -> wishlistService.getProducts(customerId))
				.isInstanceOf(WishlistNotFoundException.class);
	}

	@Test
	void deveVerificarSeProdutoExisteNaWishlist() {
		String customerId = "c123";
		Product product = new Product("p1", "Produto 1");
		Wishlist wishlist = new Wishlist(customerId, List.of(product));

		when(wishlistRepository.findById(customerId)).thenReturn(Optional.of(wishlist));

		boolean result = wishlistService.hasProduct(customerId, "p1");

		assertThat(result).isTrue();
	}
}