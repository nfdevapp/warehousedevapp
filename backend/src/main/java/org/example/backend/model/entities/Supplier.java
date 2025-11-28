package org.example.backend.model.entities;

import lombok.Builder;
import lombok.With;
import org.springframework.data.annotation.Id;

/**
 * Lieferant
 */
@With
@Builder
public record Supplier(
        @Id String id,
        String name,
        String city,
        String street,
        String houseNumber,
        String zipCode) {
}
