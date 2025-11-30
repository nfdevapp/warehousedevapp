package org.example.backend.model.entities;

import lombok.Builder;
import lombok.With;
import org.example.backend.utils.enums.Category;
import org.example.backend.utils.enums.Status;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Produkt/Ware
 */
@With
@Builder
@Document(collection = "product")
public record Product(
        @Id String id,
        String name,
        String barcode,
        String description,
        int quantity,
        String warehouseId) {
}
