package com.mitrais.inventoryservice.service;

import com.mitrais.inventoryservice.model.InventoryItem;
import com.mitrais.inventoryservice.repository.InventoryItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryItemService {

    private InventoryItemRepository inventoryItemRepository;

    public InventoryItemService(InventoryItemRepository inventoryItemRepository) {
        this.inventoryItemRepository = inventoryItemRepository;
    }

    public List<InventoryItem> getAllInventoryItem() {
        return inventoryItemRepository.findAll();
    }

    public Optional<InventoryItem> getInventoryItemByCode(String code) {
        return inventoryItemRepository.findByProductCode(code);
    }
}
