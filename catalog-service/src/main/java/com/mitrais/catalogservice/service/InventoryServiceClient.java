package com.mitrais.catalogservice.service;

import com.mitrais.catalogservice.response.ProductInventoryResponse;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static com.mitrais.catalogservice.util.Constants.INVENTORY_API;

@Service
@Slf4j
public class InventoryServiceClient {

    private RestTemplate restTemplate;
    private static final Logger LOGGER = LoggerFactory.getLogger(InventoryServiceClient.class);

    public InventoryServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    StringBuilder urlBuilder = new StringBuilder(INVENTORY_API);

    @HystrixCommand(fallbackMethod = "getDefaultProductInventoryByCode",commandKey = "inventory-by-productcode")
    public Optional<ProductInventoryResponse> getProductInventoryByCode(String productCode) {
        urlBuilder.append("/inventory/{code}");
        ResponseEntity<ProductInventoryResponse> responseEntity =
                restTemplate.getForEntity(urlBuilder.toString(), ProductInventoryResponse.class, productCode);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return Optional.ofNullable(responseEntity.getBody());
        } else {
            LOGGER.error("unable to get inventory level for product code " + productCode);
            return Optional.empty();
        }
    }

    @SuppressWarnings("unused")
    Optional<ProductInventoryResponse> getDefaultProductInventoryByCode(String productCode) {
        LOGGER.info("Return default qty for product code " + productCode);
        ProductInventoryResponse response = new ProductInventoryResponse();
        response.setProductCode(productCode);
        response.setAvailableQty(0);

        return Optional.ofNullable(response);
    }
}
