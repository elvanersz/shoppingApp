package com.elvan.shoppingapp.product.repository.mongo;

import com.elvan.shoppingapp.product.domain.Product;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ProductRepository extends ReactiveMongoRepository<Product, String> {
}
