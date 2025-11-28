package org.example.backend.model.entities;

import lombok.With;
import org.springframework.data.annotation.Id;

/**
 * Kunde
 */
@With
public record Customer(
        @Id String id,
        String name,
        String address,
        String email) {
}
