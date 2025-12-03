package org.example.backend.model.entities;

import lombok.Builder;
import lombok.With;

/**
 * User => für Login
 */
@With
@Builder
public record AppUser(String id, String name, String role) {
}
