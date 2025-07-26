package com.dev.retailcontrol.controller;

import com.dev.retailcontrol.dto.ProductCreateForm;
import com.dev.retailcontrol.dto.ProductCardDto;
import com.dev.retailcontrol.dto.ProductEditForm;
import com.dev.retailcontrol.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/retail-control")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public String retailControl() {
        return "index";
    }

    @GetMapping("/search")
    public String searchProductByCategory(@RequestParam("barcode") String barcode, Model model) {
        if (barcode.isBlank()) {
            model.addAttribute("isBarcodeBlank", true);
            return "index";
        }
        ProductEditForm productEditForm = service.getProductByBarcode(barcode.trim());
        if (productEditForm == null) {
            model.addAttribute("productNotFound", true);
        } else {
            model.addAttribute("productEditForm", productEditForm);
            model.addAttribute("barcode", barcode);
        }
        return "index";
    }

    @GetMapping("/category/{name}")
    public String showProductsByCategory(@PathVariable String name, Model model) {
        List<ProductCardDto> productCards = service.getProductsByCategory(name);
        model.addAttribute("productCards", productCards);
        return "index";
    }

    @PostMapping("/save")
    public String saveProduct(@Valid ProductCreateForm form,
                              BindingResult bindingResult,
                              @RequestParam("imageFile") MultipartFile imageFile,
                              RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "index";
        }
        boolean isSaved = service.saveProduct(form, imageFile);
        if (isSaved) {
            redirectAttributes.addAttribute("saveSuccess", true);
        } else {
            redirectAttributes.addAttribute("saveFailed", true);
        }
        return "redirect:/retail-control";
    }

    @PostMapping("/edit")
    public String editProduct(@RequestParam("barcode") String barcode,
                              @Valid ProductEditForm form,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes,
                              Model model) {
        if (bindingResult.hasErrors()) {
            String imagePath = service.getImagePath(barcode).orElseThrow();
            form.setImagePath(imagePath);
            model.addAttribute("barcode", barcode);
            return "index";
        }
        service.editProduct(form, barcode);
        redirectAttributes.addAttribute("editSuccess", true);
        return "redirect:/retail-control";
    }

    @PostMapping("/delete")
    public String deleteProduct(@RequestParam("barcode") String barcode, RedirectAttributes redirectAttributes) {
        service.deleteProduct(barcode);
        redirectAttributes.addAttribute("deleteSuccess", true);
        return "redirect:/retail-control";
    }

    @ModelAttribute("productCreateForm")
    public ProductCreateForm productCreateForm() {
        return new ProductCreateForm();
    }
}