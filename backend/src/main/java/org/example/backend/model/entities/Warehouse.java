package org.example.backend.model.entities;

import lombok.Builder;
import lombok.With;
import org.springframework.data.annotation.Id;

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
