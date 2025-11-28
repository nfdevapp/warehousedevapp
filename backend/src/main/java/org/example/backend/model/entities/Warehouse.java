package org.example.backend.model.entities;

import lombok.With;
import org.springframework.data.annotation.Id;

/**
 * Lager
 */
@With
public record Warehouse(
        @Id String id,
        String name,
        String city,
        String street,
        String houseNumber,
        String zipCode
        ) {
}
