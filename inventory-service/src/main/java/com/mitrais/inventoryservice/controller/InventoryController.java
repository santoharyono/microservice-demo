package com.mitrais.inventoryservice.controller;

import com.mitrais.inventoryservice.model.InventoryItem;
import com.mitrais.inventoryservice.service.InventoryItemService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("/api/inventory")
public class InventoryController {

    private InventoryItemService inventoryItemService;
    private static final Logger LOGGER = LoggerFactory.getLogger(InventoryController.class);

    public InventoryController(InventoryItemService inventoryItemService) {
        this.inventoryItemService = inventoryItemService;
    }

    @GetMapping("")
    public List<InventoryItem> getInventoryItem() {
        return inventoryItemService.getAllInventoryItem();
    }

    @GetMapping("/{productcode}")
    public ResponseEntity<InventoryItem> getInventoryItemByCode(@PathVariable("productcode") String productCode) {
        LOGGER.info("finding item for code " + productCode);
        Optional<InventoryItem> inventoryItemByCode = inventoryItemService.getInventoryItemByCode(productCode);

        if (inventoryItemByCode.isPresent()) return new ResponseEntity(inventoryItemByCode, HttpStatus.OK);
        else return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
}
