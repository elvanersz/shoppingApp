package com.elvan.shoppingapp.product.service;

import org.springframework.stereotype.Service;

@Service
public class ProductImageService {

    public String getProductMainImage(String productId){
        return "https://productimages.hepsiburada.net/s/27/552/10194862145586.jpg";
    }
}