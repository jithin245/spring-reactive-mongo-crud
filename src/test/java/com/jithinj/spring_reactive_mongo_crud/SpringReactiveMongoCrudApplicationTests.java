package com.jithinj.spring_reactive_mongo_crud;

import com.jithinj.spring_reactive_mongo_crud.controller.ProductController;
import com.jithinj.spring_reactive_mongo_crud.dto.ProductDto;
import com.jithinj.spring_reactive_mongo_crud.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebFluxTest(ProductController.class)
class SpringReactiveMongoCrudApplicationTests {

	@Autowired
	private WebTestClient webTestClient;

	@MockBean
	private ProductService service;

	@Test
	public void addProductTest() {
		Mono<ProductDto> productDtoMono = Mono.just(new ProductDto("102", "Mobile", 1, 100.0));
		when(service.saveProduct(productDtoMono))
				.thenReturn(productDtoMono);
		webTestClient.post()
				.uri("/products")
				.body(Mono.just(productDtoMono), ProductDto.class)
				.exchange()
				.expectStatus()
				.isOk();
	}

	@Test
	public void getProductsTest() {
		Flux<ProductDto> productDtoFlux = Flux.just(new ProductDto("102", "Mobile", 1, 100.0),
				new ProductDto("103", "tv", 1, 200.0));

		when(service.getAllProducts())
				.thenReturn(productDtoFlux);

		Flux<ProductDto> responseBody = webTestClient.get()
				.uri("/products")
				.exchange()
				.expectStatus()
				.isOk()
				.returnResult(ProductDto.class)
				.getResponseBody();
		StepVerifier.create(responseBody)
				.expectSubscription()
				.expectNext(new ProductDto("102", "Mobile", 1, 100.0))
				.expectNext(new ProductDto("103", "tv", 1, 200.0))
				.verifyComplete();
	}

	@Test
	public void getProductTest() {
		Mono<ProductDto> productDtoMono = Mono.just(new ProductDto("102", "Mobile", 1, 100.0));
		when(service.getProduct(any()))
				.thenReturn(productDtoMono);
		Flux<ProductDto> responseBody = webTestClient.get()
				.uri("/products/66915f39ba0c980b33b806de")
				.exchange()
				.expectStatus()
				.isOk()
				.returnResult(ProductDto.class)
				.getResponseBody();
		StepVerifier.create(responseBody)
				.expectSubscription()
				.expectNext(new ProductDto("102", "Mobile", 1, 100.0))
				.verifyComplete();
	}

	@Test
	public void updateProductTest() {
		Mono<ProductDto> productDtoMono = Mono.just(new ProductDto("102", "Mobile", 1, 100.0));
		when(service.updateProduct(productDtoMono, "66915f39ba0c980b33b806de"))
				.thenReturn(productDtoMono);
		webTestClient.put()
				.uri("/products/update/66915f39ba0c980b33b806de")
				.body(productDtoMono, ProductDto.class)
				.exchange()
				.expectStatus()
				.isOk();
	}

	@Test
	public void deleteProductTest() {
		when(service.deleteProduct(any()))
				.thenReturn(Mono.empty());
		webTestClient.delete()
				.uri("/products/delete/66915f39ba0c980b33b806de")
				.exchange()
				.expectStatus()
				.isOk();
	}
}
