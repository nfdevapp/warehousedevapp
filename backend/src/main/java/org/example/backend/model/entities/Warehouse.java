package org.example.backend.model.entities;

import lombok.Builder;
import lombok.With;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Lager
 */
@With
@Builder
public record Warehouse(
        @Id String id,
        String name,
        String city,
        String street,
        String houseNumber,
        String zipCode
        ) {
}
