package com.mitrais.catalogservice.controller;

import com.mitrais.catalogservice.exception.ProductNotFoundException;
import com.mitrais.catalogservice.model.Product;
import com.mitrais.catalogservice.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/products")
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("")
    public List<Product> getAll() {
        return productService.getAllProduct();
    }

    @GetMapping("/{code}")
    public Product getProductByCode(@PathVariable String code) {
        return productService.getProductByCode(code).orElseThrow(() -> new ProductNotFoundException("Product " + code + " not found"));
    }
}
