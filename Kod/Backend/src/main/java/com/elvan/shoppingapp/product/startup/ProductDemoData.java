package com.elvan.shoppingapp.product.startup;

import com.elvan.shoppingapp.product.domain.MoneyTypes;
import com.elvan.shoppingapp.product.model.ProductSaveRequest;
import com.elvan.shoppingapp.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

import static java.util.UUID.randomUUID;

@Component
@RequiredArgsConstructor
public class ProductDemoData {

    private final ProductService productService;


    @EventListener(ApplicationReadyEvent.class)
    public void migrate(){
        Long countOfData = productService.count().block();

        if(countOfData.equals(0L)){
            IntStream.range(0, 20).forEach(item -> {
                productService.save(
                    ProductSaveRequest.builder()
                            .sellerId(randomUUID().toString())
                            .id(randomUUID().toString())
                            .description("Product Description" + item)
                            .money(MoneyTypes.USD)
                            .categoryId(randomUUID().toString())
                            .name("Product Name" + item)
                            .features("<li>Black Color</li> <li>Aluminum Case</li> <li>2 Years Warranty</li> <li>5 ınch</li>")
                            .price(BigDecimal.TEN)
                            .image(List.of("https://productimages.hepsiburada.net/s/27/552/10194862145586.jpg"))
                            .build());
            });
        }
    }
}