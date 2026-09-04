package com.medicore.datastructures;

import com.medicore.model.Visit;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VisitLinkedListTest {

    @Test
    void addVisitPreservesChronologicalOrder() {
        VisitLinkedList list = new VisitLinkedList();
        list.addVisit(new Visit(1, LocalDate.now(), "Dr A", "Flu", "Rest"));
        list.addVisit(new Visit(2, LocalDate.now(), "Dr B", "Cold", "Meds"));

        List<Visit> all = list.displayAll();
        assertEquals(1, all.get(0).getVisitId());
        assertEquals(2, all.get(1).getVisitId());
    }

    @Test
    void searchFindsById() {
        VisitLinkedList list = new VisitLinkedList();
        list.addVisit(new Visit(1, LocalDate.now(), "Dr A", "Flu", "Rest"));
        assertNotNull(list.searchVisit(1));
        assertNull(list.searchVisit(99));
    }

    @Test
    void removeHeadMiddleAndTail() {
        VisitLinkedList list = new VisitLinkedList();
        list.addVisit(new Visit(1, LocalDate.now(), "Dr A", "Flu", "Rest"));
        list.addVisit(new Visit(2, LocalDate.now(), "Dr B", "Cold", "Meds"));
        list.addVisit(new Visit(3, LocalDate.now(), "Dr C", "Cut", "Stitches"));

        assertTrue(list.removeVisit(2)); // middle
        assertEquals(2, list.size());
        assertTrue(list.removeVisit(1)); // head
        assertTrue(list.removeVisit(3)); // tail (now the only node)
        assertTrue(list.isEmpty());
        assertFalse(list.removeVisit(1)); // already gone
    }
}
