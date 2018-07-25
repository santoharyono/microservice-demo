package com.mitrais.catalogservice.response;

import lombok.Data;

@Data
public class ProductInventoryResponse {
    private String productCode;
    private int availableQty;
}
