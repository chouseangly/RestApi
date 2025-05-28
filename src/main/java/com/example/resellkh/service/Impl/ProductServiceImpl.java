package com.example.resellkh.service.Impl;

import com.example.resellkh.model.Product;
import com.example.resellkh.model.dto.ProductRequest;
import com.example.resellkh.model.dto.ProductWithFilesDto;
import com.example.resellkh.model.entity.ProductFile;
import com.example.resellkh.repository.ProductFileRepo;
import com.example.resellkh.repository.ProductRepo;
import com.example.resellkh.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepo;
    private final ProductFileRepo fileRepo;

    @Override
    public ProductWithFilesDto uploadProductWithCategoryName(ProductRequest request, MultipartFile[] files) {
        if (files == null || files.length < 1 || files.length > 5) {
            throw new IllegalArgumentException("You must upload between 1 and 5 files.");
        }

        Long categoryId = Long.valueOf(productRepo.getCategoryIdByName(request.getCategoryName()));
        if (categoryId == null) {
            throw new RuntimeException("Invalid category name: " + request.getCategoryName());
        }

        Product product = new Product();
        BeanUtils.copyProperties(request, product);
        product.setMainCategoryId(categoryId);
        product.setCreatedAt(LocalDateTime.now());
        productRepo.insertProduct(product);

        String uploadDirPath = System.getProperty("user.dir") + "/uploads";
        File uploadDir = new File(uploadDirPath);
        if (!uploadDir.exists()) uploadDir.mkdirs();

        for (MultipartFile file : files) {
            try {
                String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
                File destination = new File(uploadDir, fileName);
                file.transferTo(destination);

                ProductFile productFile = new ProductFile();
                productFile.setProductId(product.getProductId().longValue());
                productFile.setFileUrl("/uploads/" + fileName);
                fileRepo.insertProductFile(productFile);

            } catch (IOException e) {
                throw new RuntimeException("File upload failed: " + file.getOriginalFilename(), e);
            }
        }

        return getProductWithFilesById(product.getProductId().longValue());
    }

    @Override
    public ProductWithFilesDto getProductWithFilesById(Long id) {
        Product product = productRepo.findById(id);
        if (product == null) {
            throw new IllegalArgumentException("Product not found with ID: " + id);
        }
        return mapToDto(product);
    }

    @Override
    public List<ProductWithFilesDto> getAllProductsWithFiles() {
        return productRepo.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private ProductWithFilesDto mapToDto(Product product) {
        ProductWithFilesDto dto = new ProductWithFilesDto();

        dto.setProductId(product.getProductId() != null ? product.getProductId().longValue() : null);
        dto.setProductName(product.getProductName());
        dto.setUserId(product.getUserId() != null ? product.getUserId().longValue() : null);
        dto.setMainCategoryId(product.getMainCategoryId() != null ? product.getMainCategoryId().longValue() : null);
        dto.setProductPrice(product.getProductPrice());
        dto.setProductStatus(product.getProductStatus());
        dto.setDescription(product.getDescription());
        dto.setLocation(product.getLocation());
        dto.setCondition(product.getCondition());
        dto.setCreatedAt(product.getCreatedAt());

        dto.setFileUrls(fileRepo.findByProductId(Long.valueOf(product.getProductId()))
                .stream()
                .map(ProductFile::getFileUrl)
                .collect(Collectors.toList()));

        dto.setCategoryName(productRepo.getCategoryNameById(Math.toIntExact(product.getMainCategoryId())));

        return dto;
    }


}
