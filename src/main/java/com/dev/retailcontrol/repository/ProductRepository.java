package com.dev.retailcontrol.repository;

import com.dev.retailcontrol.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> getProductsByCategory (String category);
    Optional<Product> getProductByBarcode(String barcode);
    boolean existsByBarcode(String barcode);
    void deleteByBarcode(String barcode);

    @Query("SELECT product.imagePath FROM Product product WHERE product.barcode = :barcode")
    Optional<String> findImagePathByBarcode (String barcode);
}