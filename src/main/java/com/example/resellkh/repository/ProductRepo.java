package com.example.resellkh.repository;

import com.example.resellkh.model.Product;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProductRepo {

    @Insert("""
        INSERT INTO products (
            product_name, user_id, main_category_id, product_price,
            product_status, description, location, condition, created_at
        ) VALUES (
            #{productName}, #{userId}, #{mainCategoryId}, #{productPrice},
            #{productStatus}, #{description}, #{location}, #{condition}, #{createdAt}
        )
    """)
    @Options(useGeneratedKeys = true, keyProperty = "productId", keyColumn = "product_id")
    void insertProduct(Product product);

    @Select("SELECT main_category_id FROM main_category WHERE name = #{name}")
    Integer getCategoryIdByName(String name);

    @Results(id = "ProductResult", value = {
            @Result(property = "productId", column = "product_id"),
            @Result(property = "productName", column = "product_name"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "mainCategoryId", column = "main_category_id"),
            @Result(property = "productPrice", column = "product_price"),
            @Result(property = "productStatus", column = "product_status"),
            @Result(property = "description", column = "description"),
            @Result(property = "location", column = "location"),
            @Result(property = "condition", column = "condition"),
            @Result(property = "createdAt", column = "created_at")
    })
    @Select("SELECT * FROM products WHERE product_id = #{id}")
    Product findById(Long id);
    @Select("SELECT name FROM main_category WHERE main_category_id = #{id}")
    String getCategoryNameById(Integer id);

    @Select("SELECT * FROM products")
    @ResultMap("ProductResult")
    List<Product> findAll();
}