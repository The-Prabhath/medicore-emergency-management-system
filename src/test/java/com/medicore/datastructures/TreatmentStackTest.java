package com.medicore.datastructures;

import com.medicore.model.TreatmentRecord;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.EmptyStackException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TreatmentStackTest {

    @Test
    void popOnEmptyStackThrows() {
        TreatmentStack stack = new TreatmentStack();
        assertThrows(EmptyStackException.class, stack::pop);
    }

    @Test
    void followsLifoOrder() {
        TreatmentStack stack = new TreatmentStack();
        TreatmentRecord r1 = new TreatmentRecord(1, "A", "x", LocalDateTime.now());
        TreatmentRecord r2 = new TreatmentRecord(2, "B", "y", LocalDateTime.now());

        stack.push(r1);
        stack.push(r2);

        assertEquals(r2, stack.pop()); // most recently pushed comes off first
        assertEquals(r1, stack.pop());
        assertTrue(stack.isEmpty());
    }

    @Test
    void displayAllShowsMostRecentFirst() {
        TreatmentStack stack = new TreatmentStack();
        stack.push(new TreatmentRecord(1, "A", "x", LocalDateTime.now()));
        stack.push(new TreatmentRecord(2, "B", "y", LocalDateTime.now()));

        List<TreatmentRecord> all = stack.displayAll();
        assertEquals(2, all.get(0).getPatientId());
        assertEquals(1, all.get(1).getPatientId());
    }
}
