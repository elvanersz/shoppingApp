package com.elvan.shoppingapp.product.api;

import com.elvan.shoppingapp.product.model.category.CategoryResponse;
import com.elvan.shoppingapp.product.service.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
@CrossOrigin("*")
public class CategoryApi {

    private final CategoryService categoryService;

    @GetMapping
    public Flux<CategoryResponse> getAll() {
        return categoryService.getAll();
    }
}
