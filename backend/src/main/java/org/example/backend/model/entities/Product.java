package org.example.backend.model.entities;

import lombok.With;
import org.example.backend.utils.enums.Category;
import org.springframework.data.annotation.Id;

/**
 * Produkt/Ware
 */
@With
public record Product(
        @Id String id,
        String name,
        String barcode,
        String description,
        int quantity,
        Category category,
        String warehouseId) {
}
