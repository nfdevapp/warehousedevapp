package org.example.backend.service;

import org.example.backend.exceptions.WarehouseAppException;
import org.example.backend.model.entities.Product;
import org.example.backend.model.entities.Warehouse;
import org.example.backend.repository.ProductRepo;
import org.example.backend.repository.WarehouseRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WarehouseServiceTest {

    private WarehouseRepo warehouseRepo;
    private ProductRepo productRepo;
    private ProductService productService;

    private WarehouseService warehouseService;

    /**
     * vor jedem einzelnen Test ausführen, damit jeder Test
     * eine frische, saubere Umgebung bekommt
     */
    @BeforeEach
    void setup() {
        warehouseRepo = mock(WarehouseRepo.class);
        productRepo = mock(ProductRepo.class);
        productService = mock(ProductService.class);
        warehouseService = new WarehouseService(warehouseRepo, productRepo, productService);
    }

    /**
     * Testet, dass ein Warehouse korrekt per ID gefunden wird.
     */
    @Test
    void testGetWarehouseById_found() {

        // GIVEN: Ein bestehendes Warehouse
        Warehouse w = new Warehouse("1", "Test", "City", "Street", "1A", "12345");
        when(warehouseRepo.findById("1")).thenReturn(Optional.of(w));

        // WHEN: Die Methode aufgerufen wird
        Warehouse result = warehouseService.getWarehouseById("1");

        // THEN: Das Warehouse wird korrekt zurückgegeben
        assertEquals("1", result.id());
        verify(warehouseRepo).findById("1");
    }

    /**
     * Testet, dass eine WarehouseAppException geworfen wird,
     * wenn das Warehouse nicht existiert.
     */
    @Test
    void testGetWarehouseById_notFound() {

        // GIVEN: Keine Daten im Repository
        when(warehouseRepo.findById("1")).thenReturn(Optional.empty());

        // WHEN + THEN: Aufruf führt zu Exception
        assertThrows(WarehouseAppException.class,
                () -> warehouseService.getWarehouseById("1"));
    }

    /**
     * Testet das Erstellen eines neuen Warehouses.
     */
    @Test
    void testCreateWarehouse() {

        // GIVEN: Ein Warehouse-Objekt
        // given – Eingabe des Users (ID = null)
        Warehouse input = Warehouse.builder()
                .name("Zentrallager")
                .city("Berlin")
                .street("Hauptstr")
                .houseNumber("10A")
                .zipCode("10115")
                .build();

        // Erwartetes Objekt nach dem Speichern (MongoDB setzt ID)
        Warehouse saved = Warehouse.builder()
                .id("123456")
                .name(input.name())
                .city(input.city())
                .street(input.street())
                .houseNumber(input.houseNumber())
                .zipCode(input.zipCode())
                .build();

        // Verhalten des Repos mocken
        when(warehouseRepo.save(any(Warehouse.class))).thenReturn(saved);


        // WHEN: createWarehouse aufgerufen wird
        Warehouse result = warehouseService.createWarehouse(input);

        // THEN: Das Warehouse wird gespeichert und zurückgegeben
        assertNotNull(result);
        assertEquals("123456", result.id());
        assertEquals("Zentrallager", result.name());

        // sicherstellen, dass save() genau einmal aufgerufen wurde
        verify(warehouseRepo, times(1)).save(any(Warehouse.class));
    }

    /**
     * Testet das Aktualisieren eines existierenden Warehouses.
     */
    @Test
    void testUpdateWarehouse() {

        // GIVEN: ein altes Warehouse im Repo
        Warehouse oldW = new Warehouse("1", "Old", "OldC", "OldSt", "11", "00000");
        Warehouse newW = new Warehouse("1", "New", "NewC", "NewSt", "22", "99999");
        when(warehouseRepo.findById("1")).thenReturn(Optional.of(oldW));

        // WHEN: updateWarehouse aufgerufen wird
        warehouseService.updateWarehouse("1", newW);

        // THEN: Das geänderte Warehouse wird gespeichert
        verify(warehouseRepo).save(any(Warehouse.class));
    }

    /**
     * Testet das Löschen eines Warehouses inklusive:
     * - Löschen aller zugehörigen Produkte
     * - Löschen des Warehouses selbst
     */
    @Test
    void testDeleteWarehouse_withProducts() {

        // GIVEN: Ein Warehouse und zwei zugehörige Produkte
        Warehouse w = new Warehouse("1", "Test", "City", "Street", "1A", "12345");
        Product p1 = new Product("p1", "Prod1", "1111111111111", "Testprodukt 1", 10, "1");
        Product p2 = new Product("p2", "Prod2", "2222222222222", "Testprodukt 2", 20, "1");

        when(warehouseRepo.findById("1")).thenReturn(Optional.of(w));
        when(productRepo.findByWarehouseId("1")).thenReturn(List.of(p1, p2));

        // WHEN: deleteWarehouse aufgerufen wird
        warehouseService.deleteWarehouse("1");

        // THEN: Produkte werden gelöscht und anschließend das Warehouse selbst
        verify(productService).deleteProduct("p1");
        verify(productService).deleteProduct("p2");
        verify(warehouseRepo).delete(w);
    }

    /**
     * Testet, dass alle Warehouses korrekt aus dem Repository gelesen werden.
     */
    @Test
    void testGetAllWarehouses() {

        // GIVEN: Das Repository enthält zwei Warehouses
        Warehouse w1 = new Warehouse("1", "WH1", "City1", "Street1", "10", "11111");
        Warehouse w2 = new Warehouse("2", "WH2", "City2", "Street2", "20", "22222");

        when(warehouseRepo.findAll()).thenReturn(List.of(w1, w2));

        // WHEN: getAllWarehouses() wird aufgerufen
        List<Warehouse> result = warehouseService.getAllWarehouses();

        // THEN: Die beiden Warehouses werden zurückgegeben
        assertEquals(2, result.size());
        assertTrue(result.contains(w1));
        assertTrue(result.contains(w2));

        // Zusätzlich prüfen: findAll() wurde einmal aufgerufen
        verify(warehouseRepo).findAll();
    }



}
