package org.example.backend.service;

import org.example.backend.exceptions.WarehouseAppException;
import org.example.backend.model.entities.Product;
import org.example.backend.repository.ProductRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    private ProductRepo productRepo;
    private ProductService productService;

    /**
     * Vor jedem einzelnen Test ausführen, damit jeder Test
     * eine frische, saubere Umgebung bekommt.
     */
    @BeforeEach
    void setup() {
        productRepo = mock(ProductRepo.class);
        productService = new ProductService(productRepo);
    }

    /**
     * Testet, dass alle Produkte eines bestimmten Warehouses korrekt
     * über das Repository geladen und zurückgegeben werden.
     */
    @Test
    @WithMockUser
    void testGetAllProductsById() {

        // GIVEN: Zwei Produkte im gleichen Warehouse
        Product p1 = new Product("p1", "Produkt 1", "1111111111111", "Beschreibung 1", 10, "W1");
        Product p2 = new Product("p2", "Produkt 2", "2222222222222", "Beschreibung 2", 5, "W1");

        when(productRepo.findByWarehouseId("W1"))
                .thenReturn(List.of(p1, p2));

        // WHEN: Die Methode aufgerufen wird
        List<Product> result = productService.getAllProductsById("W1");

        // THEN: Die beiden Produkte werden korrekt zurückgegeben
        assertEquals(2, result.size());
        assertTrue(result.contains(p1));
        assertTrue(result.contains(p2));

        // Sicherstellen, dass findByWarehouseId() aufgerufen wurde
        verify(productRepo).findByWarehouseId("W1");
    }

    /**
     * Testet, dass ein Produkt korrekt per ID gefunden wird.
     */
    @Test
    @WithMockUser
    void testGetProductById_found() {

        // GIVEN: Repository vorbereitet, sodass Produkt existiert
        Product p = new Product("p1", "Prod1", "1111111111111", "Beschreibung", 10, "W1");
        when(productRepo.findById("p1")).thenReturn(Optional.of(p));

        // WHEN
        Product result = productService.getProductById("p1");

        // THEN
        assertEquals("p1", result.id());
        assertEquals("Prod1", result.name());
        verify(productRepo).findById("p1");
    }

    /**
     * Testet, dass eine Exception geworfen wird,
     * wenn Produkt ID nicht existiert.
     */
    @Test
    @WithMockUser
    void testGetProductById_notFound() {

        // GIVEN: Repository liefert Optional.empty()
        when(productRepo.findById("p1")).thenReturn(Optional.empty());

        // WHEN + THEN
        WarehouseAppException ex = assertThrows(
                WarehouseAppException.class,
                () -> productService.getProductById("p1")
        );

        assertTrue(ex.getMessage().contains("Product not found: p1"));

        verify(productRepo).findById("p1");
    }

//    TODO
//    createProduct, updateProduct, deleteProduct
}
