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

    /**
     * Testet, dass ein Produkt korrekt erstellt wird.
     */
    @Test
    @WithMockUser
    void testCreateProduct() {

        // GIVEN
        Product input = new Product(null, "Produkt X", null, "Beschreibung X", 20, "W1");

        // Repo soll das gespeicherte Produkt zurückgeben
        Product saved = new Product("123", "Produkt X", "BARCODE123", "Beschreibung X", 20, "W1");

        // ANY Product speichern → saved zurückgeben
        when(productRepo.save(any(Product.class))).thenReturn(saved);

        // WHEN
        Product result = productService.createProduct(input);

        // THEN
        assertNotNull(result);
        assertEquals("Produkt X", result.name());
        assertEquals("Beschreibung X", result.description());
        assertEquals(20, result.quantity());
        assertEquals("W1", result.warehouseId());

        // Barcode wird im Service generiert
        assertNotNull(result.barcode());

        verify(productRepo).save(any(Product.class));
    }

    @Test
    @WithMockUser
    void testUpdateProduct_success() {

        // GIVEN: existierendes Produkt
        Product oldProduct = new Product("p1", "Alt", "OLD123", "Alt Beschreibung", 10, "W1");

        when(productRepo.findById("p1")).thenReturn(Optional.of(oldProduct));

        Product updatedData = new Product(
                "p1", "Neu", "NEW456", "Neue Beschreibung", 99, "W1"
        );

        // WHEN
        Product result = productService.updateProduct("p1", updatedData);

        // THEN
        assertEquals("Neu", result.name());
        assertEquals("Neue Beschreibung", result.description());
        assertEquals(99, result.quantity());
        assertEquals("NEW456", result.barcode());

        // Verify repo interactions
        verify(productRepo).findById("p1");
        verify(productRepo).save(any(Product.class));
    }


    /**
     * Testet, dass Update eine Exception wirft,
     * wenn die ID nicht existiert.
     */
    @Test
    @WithMockUser
    void testUpdateProduct_notFound() {

        // GIVEN
        when(productRepo.findById("p1")).thenReturn(Optional.empty());

        Product newData = new Product("p1", "Neu", "NEW456", "Neue Beschreibung", 20, "W1");

        // WHEN + THEN
        WarehouseAppException ex = assertThrows(
                WarehouseAppException.class,
                () -> productService.updateProduct("p1", newData)
        );

        assertTrue(ex.getMessage().contains("Product not found: p1"));

        verify(productRepo).findById("p1");
        verify(productRepo, never()).save(any());
    }


    /**
     * Testet das Löschen eines Produkts per ID.
     */
    @Test
    @WithMockUser
    void testDeleteProduct() {

        // WHEN
        productService.deleteProduct("p1");

        // THEN
        verify(productRepo).deleteById("p1");
    }


}
