package org.example.backend.model.dto;

import lombok.Builder;
import lombok.With;

@With
@Builder
public record ProductDto(
                         String name,
                         String city,
                         String street,
                         String houseNumber,
                         String zipCode
                         ) {
}
