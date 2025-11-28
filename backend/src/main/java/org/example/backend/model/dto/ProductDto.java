package org.example.backend.model.dto;

import org.example.backend.utils.enums.Category;

public record ProductDto(String name,
                         String barcode,
                         String description,
                         int quantity,
                         Category category,
                         int warehouseId) {
}
