package com.elvan.shoppingapp.product.repository.mongo;

import com.elvan.shoppingapp.product.domain.category.Category;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends ReactiveMongoRepository<Category, String> {
}
