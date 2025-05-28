package com.example.resellkh.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductWithFilesDto {
    private Long productId;
    private String productName;
    private Long userId;
    private Long mainCategoryId;
    private String categoryName;
    private Double productPrice;
    private String productStatus;
    private String description;
    private String location;
    private String condition;
    private LocalDateTime createdAt;
    private List<String> fileUrls;
}
