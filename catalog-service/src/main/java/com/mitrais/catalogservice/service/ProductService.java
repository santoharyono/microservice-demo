package com.mitrais.catalogservice.service;

import com.mitrais.catalogservice.model.Product;
import com.mitrais.catalogservice.repository.ProductRepository;
import com.mitrais.catalogservice.response.ProductInventoryResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

import static com.mitrais.catalogservice.util.Constants.INVENTORY_API;

@Service
@Transactional
@Slf4j
public class ProductService {

    private ProductRepository productRepository;
    private RestTemplate restTemplate;
    private InventoryServiceClient inventoryServiceClient;
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);


    public ProductService(ProductRepository productRepository, RestTemplate restTemplate, InventoryServiceClient inventoryServiceClient) {
        this.productRepository = productRepository;
        this.restTemplate = restTemplate;
        this.inventoryServiceClient = inventoryServiceClient;
    }

    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductByCode(String code) {

        Optional<Product> optionalProduct = productRepository.findByCode(code);
        if (optionalProduct.isPresent()) {
            LOGGER.info("fetching inventory level for product code " + code);
            StringBuilder inventoryItemApi = new StringBuilder(INVENTORY_API);
            String url = "/inventory/{code}";
            inventoryItemApi.append(url);

//            TODO: change using circuit breaker pattern
//            ResponseEntity<ProductInventoryResponse> responseEntity = restTemplate.getForEntity(inventoryItemApi.toString(), ProductInventoryResponse.class, code);
            Optional<ProductInventoryResponse> responseEntity = this.inventoryServiceClient.getProductInventoryByCode(code);

//            if (responseEntity.getStatusCode() == HttpStatus.OK)
            if (responseEntity.isPresent()) {
//                Integer qty = responseEntity.getBody().getAvailableQty();
                Integer qty = responseEntity.get().getAvailableQty();
                LOGGER.info("available qty " + qty);
                optionalProduct.get().setInStock(qty > 0);
//            } else {
//                LOGGER.error("unable to get inventory level for product code " + code + " status code : " + responseEntity.getStatusCode());
//            }
            }
        }
        return optionalProduct;
    }
}
