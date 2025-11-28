package org.example.backend.model.entities;

import lombok.With;
import org.springframework.data.annotation.Id;

/**
 * Lieferant
 */
@With
public record Supplier(
        @Id String id,
        String name,
        String address,
        String email) {
}
