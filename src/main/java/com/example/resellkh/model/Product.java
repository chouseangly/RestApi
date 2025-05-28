package com.example.resellkh.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Product {
    private Long productId;
    private String productName;
    private Long userId;
    private Long mainCategoryId;
    private Double productPrice;
    private String productStatus;
    private String description;
    private String location;
    private String condition;
    private String productImageUrl;
    private LocalDateTime createdAt;
}
