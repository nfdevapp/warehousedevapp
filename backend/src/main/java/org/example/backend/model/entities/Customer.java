package org.example.backend.model.entities;

import lombok.Builder;
import lombok.With;
import org.springframework.data.annotation.Id;

/**
 * Kunde
 */
@With
@Builder
public record Customer(
        @Id String id,
        String name,
        String city,
        String street,
        String houseNumber,
        String zipCode
        ) {
}
