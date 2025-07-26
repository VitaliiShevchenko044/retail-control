package com.dev.retailcontrol.service;

import com.dev.retailcontrol.dto.ProductCreateForm;
import com.dev.retailcontrol.dto.ProductCardDto;
import com.dev.retailcontrol.dto.ProductEditForm;
import com.dev.retailcontrol.model.Product;
import com.dev.retailcontrol.repository.ProductRepository;
import com.dev.retailcontrol.util.ImageCompressor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public Optional<String> getImagePath(String barcode) {
        return repository.findImagePathByBarcode(barcode);
    }

    public List<ProductCardDto> getProductsByCategory(String category) {
        List<Product> products = repository.getProductsByCategory(category);
        return products.stream()
                .map(product -> new ProductCardDto(
                        product.getName(),
                        product.getManufacturer(),
                        product.getBarcode(),
                        product.getPrice(),
                        product.getQuantity(),
                        product.getUnit(),
                        product.getImagePath()))
                .toList();
    }

    public ProductEditForm getProductByBarcode(String barcode) {
        Optional<Product> optional = repository.getProductByBarcode(barcode);
        if (optional.isPresent()) {
            Product product = optional.get();
            return new ProductEditForm(
                    product.getName(),
                    product.getManufacturer(),
                    product.getPrice(),
                    product.getQuantity(),
                    product.getImagePath());
        }
        return null;
    }

    @Transactional
    public void editProduct(ProductEditForm form, String barcode) {
        Product product = repository.getProductByBarcode(barcode)
                .orElseThrow(() -> new RuntimeException("Товар, за вказаним штрихкодом, не знайдено!"));
        product.setName(form.getName().trim());
        product.setManufacturer(form.getManufacturer().trim());
        product.setPrice(form.getPrice());
        product.setQuantity(form.getQuantity());
    }

    @Transactional
    public void deleteProduct(String barcode) {
        Optional<String> optionalImagePath = repository.findImagePathByBarcode(barcode);
        if (optionalImagePath.isPresent()) {
            try {
                if (!optionalImagePath.get().equals("default-photo.png")) {
                    Files.delete(Path.of(optionalImagePath.get()));
                }
            } catch (IOException e) {
                log.error("Помилка під час видалення зображення: {}", e.getMessage());
            }
        }
        repository.deleteByBarcode(barcode);
    }

    public boolean saveProduct(ProductCreateForm form, MultipartFile imageFile) {
        if (repository.existsByBarcode(form.getBarcode().trim())) return false;

        Path uploadPath = Path.of("product-images");
        try {
            if (Files.notExists(uploadPath))
                Files.createDirectory(uploadPath);

            String originalFileName = imageFile.getOriginalFilename();
            Path filePath;

            if (originalFileName != null && (originalFileName.toLowerCase().endsWith("jpg") ||
                    originalFileName.toLowerCase().endsWith("jpeg") || originalFileName.toLowerCase().endsWith("png") ||
                    originalFileName.toLowerCase().endsWith("webp") || originalFileName.toLowerCase().endsWith("gif") ||
                    originalFileName.toLowerCase().endsWith("wbmp") || originalFileName.toLowerCase().endsWith("bmp"))) {

                String newFileName = UUID.randomUUID() + "_" + originalFileName;
                filePath = uploadPath.resolve(newFileName);

                if (imageFile.getSize() > 50 * 1024) {
                    byte[] compressedImage = ImageCompressor.compress(imageFile, 300, 300, 1.0f);
                    Files.write(filePath, compressedImage);
                } else {
                    Files.write(filePath, imageFile.getBytes());
                }
            } else {
                filePath = Path.of("default-photo.png");
            }

            repository.save(Product.builder()
                    .barcode(form.getBarcode().trim())
                    .name(form.getName().trim())
                    .category(form.getCategory().trim())
                    .manufacturer(form.getManufacturer().trim())
                    .unit(form.getUnit().trim())
                    .price(form.getPrice())
                    .quantity(form.getQuantity())
                    .imagePath(filePath.toString().replace("\\", "/"))
                    .build());
        } catch (IOException e) {
            log.error("Помилка під час збереження товару: {}", e.getMessage());
        }
        return true;
    }
}