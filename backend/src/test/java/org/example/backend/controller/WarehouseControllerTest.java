package org.example.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.backend.model.entities.Warehouse;
import org.example.backend.service.WarehouseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Unit-Tests für den WarehouseController.
 *
 * Wir nutzen @WebMvcTest, da wir nur den Controller testen möchten,
 * NICHT den kompletten Spring Context.
 *
 * Der WarehouseService wird mit @MockBean gemockt,
 * damit keine echten Datenbankzugriffe stattfinden.
 */
@WebMvcTest(WarehouseController.class)
class WarehouseControllerTest {

    @Autowired
    MockMvc mockMvc; // Simuliert HTTP-Anfragen

    @Autowired
    ObjectMapper objectMapper; // Für JSON-Serialisierung

    @MockBean
    WarehouseService warehouseService; // Mock des Services

    // Beispiel-Warehouse für alle Tests
    Warehouse exampleWarehouse = Warehouse.builder()
            .id("1")
            .name("Lagerhaus Name")
            .city("Köln")
            .street("Neue Straße")
            .houseNumber("99")
            .zipCode("50000")
            .build();

    /**
     * Testet GET /api/warehouse
     *
     * - Service liefert eine Liste mit einem Warehouse
     * - Response JSON wird als Textblock erwartet
     */
    @Test
    void getAllWarehouses_shouldReturnList() throws Exception {

        when(warehouseService.getAllWarehouses()).thenReturn(List.of(exampleWarehouse));

        // Erwartetes JSON als textblock
        String expectedJson = """
                [
                    {
                        "id": "1",
                        "name": "Lagerhaus Name",
                        "city": "Köln",
                        "street": "Neue Straße",
                        "houseNumber": "99",
                        "zipCode": "50000"
                    }
                ]
                """;

        mockMvc.perform(get("/api/warehouse"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson)); // JSON-Vergleich

        verify(warehouseService).getAllWarehouses(); // prüft Aufruf
    }

    /**
     * Testet GET /api/warehouse/{id}
     */
    @Test
    void getWarehouseById_shouldReturnJson() throws Exception {

        when(warehouseService.getWarehouseById("1")).thenReturn(exampleWarehouse);

        String expectedJson = """
                {
                    "id": "1",
                    "name": "Lagerhaus Name",
                    "city": "Köln",
                    "street": "Neue Straße",
                    "houseNumber": "99",
                    "zipCode": "50000"
                }
                """;

        mockMvc.perform(get("/api/warehouse/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));

        verify(warehouseService).getWarehouseById("1");
    }

    /**
     * Testet POST /api/warehouse
     */
    @Test
    void createWarehouse_shouldReturnJson() throws Exception {

        when(warehouseService.createWarehouse(any())).thenReturn(exampleWarehouse);

        String expectedJson = """
                {
                    "id": "1",
                    "name": "Lagerhaus Name",
                    "city": "Köln",
                    "street": "Neue Straße",
                    "houseNumber": "99",
                    "zipCode": "50000"
                }
                """;

        mockMvc.perform(post("/api/warehouse")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(exampleWarehouse)))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));

        verify(warehouseService).createWarehouse(any());
    }

    /**
     * Testet PUT /api/warehouse/{id}
     */
    @Test
    void updateWarehouse_shouldReturnUpdatedJson() throws Exception {

        when(warehouseService.updateWarehouse(eq("1"), any()))
                .thenReturn(exampleWarehouse);

        String expectedJson = """
                {
                    "id": "1",
                    "name": "Lagerhaus Name",
                    "city": "Köln",
                    "street": "Neue Straße",
                    "houseNumber": "99",
                    "zipCode": "50000"
                }
                """;

        mockMvc.perform(put("/api/warehouse/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(exampleWarehouse)))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));

        verify(warehouseService).updateWarehouse(eq("1"), any());
    }

    /**
     * Testet DELETE /api/warehouse/{id}
     */
    @Test
    void deleteWarehouse_shouldReturnOk() throws Exception {

        doNothing().when(warehouseService).deleteWarehouse("1");

        mockMvc.perform(delete("/api/warehouse/1"))
                .andExpect(status().isOk());

        verify(warehouseService).deleteWarehouse("1");
    }
}
