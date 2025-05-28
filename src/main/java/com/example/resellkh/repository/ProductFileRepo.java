package com.example.resellkh.repository;

import com.example.resellkh.model.entity.ProductFile;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProductFileRepo {

    @Insert("INSERT INTO product_images(product_id, url) VALUES (#{productId}, #{fileUrl})")
    void insertProductFile(ProductFile file);

    @Select("SELECT product_id, url FROM product_images WHERE product_id = #{productId}")
    @Results({
            @Result(property = "productId", column = "product_id"),
            @Result(property = "fileUrl", column = "url")
    })
    List<ProductFile> findByProductId(Long productId);
}