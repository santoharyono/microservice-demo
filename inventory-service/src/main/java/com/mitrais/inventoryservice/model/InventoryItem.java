package com.mitrais.inventoryservice.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "inventory_item")
public class InventoryItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_code", nullable = false, unique = true)
    private String productCode;

    @Column(name = "quantity")
    private Integer availableQty;
}
