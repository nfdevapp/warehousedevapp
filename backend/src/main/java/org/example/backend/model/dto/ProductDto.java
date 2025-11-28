package org.example.backend.model.dto;

import lombok.Builder;
import lombok.With;
import org.example.backend.utils.enums.Category;

@With
@Builder
public record ProductDto(String name,
                         String barcode,
                         String description,
                         int quantity,
                         String customerName,
                         String customerCity,
                         String customerStreet,
                         String customerHouseNumber,
                         String customerZipCode,
                         String supplierName,
                         String supplierCity,
                         String supplierStreet,
                         String supplierHouseNumber,
                         String supplierZipCode,
                         String warehouseId
                         ) {
}
