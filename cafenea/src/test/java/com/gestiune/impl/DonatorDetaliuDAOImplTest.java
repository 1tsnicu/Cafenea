package com.gestiune.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DonatorDetaliuDAOImplTest {

    private DonatorDetaliuDAOImpl donatorDAO;
    private DonatorDetaliu testDonator;

    @BeforeEach
    void setUp() {
        donatorDAO = new DonatorDetaliuDAOImpl();
        
        // Creăm un donator de test
        testDonator = new DonatorDetaliu();
        testDonator.setCodDonator(1);
        testDonator.setNumeDonator("Test Donator");
        testDonator.setEmail("test@example.com");
        testDonator.setTelefon("1234567890");
        testDonator.setAdresa("Test Address");
        testDonator.setDataDonatie(java.sql.Date.valueOf("2024-03-20"));
        testDonator.setSuma(1000.0);
    }

    @Test
    void testInsertAndGetById() {
        // Testăm inserarea
        donatorDAO.insert(testDonator);
        
        // Verificăm că putem recupera donatorul
        DonatorDetaliu retrieved = donatorDAO.getById(testDonator.getCodDonator());
        assertNotNull(retrieved);
        assertEquals(testDonator.getNumeDonator(), retrieved.getNumeDonator());
        assertEquals(testDonator.getEmail(), retrieved.getEmail());
        assertEquals(testDonator.getSuma(), retrieved.getSuma());
    }

    @Test
    void testUpdate() {
        // Inserăm donatorul inițial
        donatorDAO.insert(testDonator);
        
        // Modificăm datele
        testDonator.setSuma(2000.0);
        donatorDAO.update(testDonator);
        
        // Verificăm modificările
        DonatorDetaliu updated = donatorDAO.getById(testDonator.getCodDonator());
        assertEquals(2000.0, updated.getSuma());
    }

    @Test
    void testDelete() {
        // Inserăm donatorul
        donatorDAO.insert(testDonator);
        
        // Ștergem donatorul
        donatorDAO.delete(testDonator.getCodDonator());
        
        // Verificăm că donatorul a fost șters
        DonatorDetaliu deleted = donatorDAO.getById(testDonator.getCodDonator());
        assertNull(deleted);
    }

    @Test
    void testGetAll() {
        // Inserăm donatorul de test
        donatorDAO.insert(testDonator);
        
        // Creăm un al doilea donator
        DonatorDetaliu secondDonator = new DonatorDetaliu();
        secondDonator.setCodDonator(2);
        secondDonator.setNumeDonator("Second Donator");
        secondDonator.setEmail("second@example.com");
        secondDonator.setTelefon("0987654321");
        secondDonator.setAdresa("Second Address");
        secondDonator.setDataDonatie(java.sql.Date.valueOf("2024-03-21"));
        secondDonator.setSuma(1500.0);
        donatorDAO.insert(secondDonator);
        
        // Verificăm că putem recupera toți donatorii
        List<DonatorDetaliu> allDonators = donatorDAO.getAll();
        assertNotNull(allDonators);
        assertTrue(allDonators.size() >= 2);
    }

    @Test
    void testSearchByNume() {
        // Inserăm donatorul de test
        donatorDAO.insert(testDonator);
        
        // Căutăm după nume
        List<DonatorDetaliu> results = donatorDAO.searchByNume("Test");
        assertNotNull(results);
        assertFalse(results.isEmpty());
        assertEquals(testDonator.getNumeDonator(), results.get(0).getNumeDonator());
    }

    @Test
    void testSearchByEmail() {
        // Inserăm donatorul de test
        donatorDAO.insert(testDonator);
        
        // Căutăm după email
        List<DonatorDetaliu> results = donatorDAO.searchByEmail("test@example.com");
        assertNotNull(results);
        assertFalse(results.isEmpty());
        assertEquals(testDonator.getEmail(), results.get(0).getEmail());
    }

    @AfterEach
    void tearDown() {
        // Curățăm datele de test după fiecare test
        if (testDonator != null && testDonator.getCodDonator() != null) {
            donatorDAO.delete(testDonator.getCodDonator());
        }
    }
} 