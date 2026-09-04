package com.medicore.datastructures;

import com.medicore.model.Patient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PatientBSTTest {

    private PatientBST tree;

    @BeforeEach
    void setUp() {
        tree = new PatientBST();
        tree.insert(new Patient(50, "Alice", 30, "071", "Flu"));
        tree.insert(new Patient(30, "Bob", 25, "072", "Fracture"));
        tree.insert(new Patient(70, "Cara", 40, "073", "Burn"));
    }

    @Test
    void insertRejectsDuplicateIds() {
        assertFalse(tree.insert(new Patient(50, "Duplicate", 20, "074", "X")));
        assertEquals(3, tree.size());
    }

    @Test
    void searchFindsExistingPatient() {
        Patient found = tree.search(30);
        assertNotNull(found);
        assertEquals("Bob", found.getName());
    }

    @Test
    void searchReturnsNullForMissingId() {
        assertNull(tree.search(999));
    }

    @Test
    void inOrderTraversalReturnsAscendingOrder() {
        List<Patient> sorted = tree.inOrderTraversal();
        assertEquals(3, sorted.size());
        assertEquals(30, sorted.get(0).getPatientId());
        assertEquals(50, sorted.get(1).getPatientId());
        assertEquals(70, sorted.get(2).getPatientId());
    }

    @Test
    void deleteLeafNode() {
        assertTrue(tree.delete(30));
        assertNull(tree.search(30));
        assertEquals(2, tree.size());
    }

    @Test
    void deleteNodeWithTwoChildrenKeepsTreeValid() {
        tree.insert(new Patient(40, "Dee", 22, "075", "Cut"));
        tree.insert(new Patient(60, "Eve", 33, "076", "Cold"));
        assertTrue(tree.delete(50)); // root, has two children
        assertNull(tree.search(50));
        List<Patient> sorted = tree.inOrderTraversal();
        assertEquals(List.of(30, 40, 60, 70),
                sorted.stream().map(Patient::getPatientId).toList());
    }
}
