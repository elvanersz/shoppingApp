package com.elvan.shoppingapp.product.service;

import com.elvan.shoppingapp.product.domain.MoneyTypes;
import com.elvan.shoppingapp.product.domain.Product;
import com.elvan.shoppingapp.product.domain.ProductImage;
import com.elvan.shoppingapp.product.domain.es.ProductEs;
import com.elvan.shoppingapp.product.model.ProductResponse;
import com.elvan.shoppingapp.product.model.ProductSaveRequest;
import com.elvan.shoppingapp.product.model.ProductSellerResponse;
import com.elvan.shoppingapp.product.repository.es.ProductEsRepository;
import com.elvan.shoppingapp.product.repository.mongo.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductPriceService productPriceService;
    private final ProductDeliveryService productDeliveryService;
    private final ProductAmountService productAmountService;
    private final ProductImageService productImageService;
    private final ProductEsService productEsService;


    public Flux<ProductResponse> getAll() {
        return productEsService.findAll().map(this::mapToDto);
    }

    public ProductResponse save(ProductSaveRequest request) {
        Product product = Product.builder()
                .active(Boolean.TRUE)
                .code("PR001")
                .categoryId(request.getCategoryId())
                .companyId(request.getSellerId())
                .description(request.getDescription())
                .features(request.getFeatures())
                .name(request.getName())
                .productImage(request.getImage().stream().map(item -> new ProductImage(ProductImage.ImageType.FEATURE, item)).collect(Collectors.toList()))
                .build();

        product = productRepository.save(product).block();

        return this.mapToDto(productEsService.saveNewProduct(product).block());
    }

    private ProductResponse mapToDto(ProductEs item) {

        if (item == null) {
            return null;
        }

        BigDecimal productPrice = productPriceService.getByMoneyType(item.getId(), MoneyTypes.USD);

        return ProductResponse.builder()
                .price(productPrice)
                .name(item.getName())
                .features(item.getFeatures())
                .id(item.getId())
                .description(item.getDescription())
                .deliveryIn(productDeliveryService.getDeliveryInfo(item.getId()))
                .categoryId(item.getCategory().getId())
                .available(productAmountService.getByProductId(item.getId()))
                .freeDelivery(productDeliveryService.freeDeliveryCheck(item.getId(), productPrice))
                .moneyType(MoneyTypes.USD)
                .image(productImageService.getProductMainImage(item.getId()))
                .seller(ProductSellerResponse.builder().id(item.getSeller().getId()).name(item.getSeller().getName()).build())
                .build();
    }

    public Mono<Long> count() {
        return productRepository.count();
    }
}