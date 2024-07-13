package com.jithinj.spring_reactive_mongo_crud.repository;

import com.jithinj.spring_reactive_mongo_crud.dto.ProductDto;
import com.jithinj.spring_reactive_mongo_crud.entity.Product;
import org.springframework.data.domain.Range;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ProductRepository extends ReactiveMongoRepository<Product, String> {
    Flux<ProductDto> findByPriceBetween(Range<Double> priceRange);
}
