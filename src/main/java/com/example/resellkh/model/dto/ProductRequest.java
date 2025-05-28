package com.example.resellkh.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ProductRequest {
    private String productName;
    private Long userId;
    private String categoryName;
    private Double productPrice;
    private String productStatus;
    private String description;
    private String location;
    private String condition;
}
