package com.elvan.shoppingapp.product.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ProductDeliveryService {

    public String getDeliveryInfo(String productId){
        return "Tomorrow";
    }

    public Boolean freeDeliveryCheck(String productId, BigDecimal price){
        return price.compareTo(BigDecimal.ONE) >= 0;
    }
}