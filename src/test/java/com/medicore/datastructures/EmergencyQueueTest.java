package com.medicore.datastructures;

import com.medicore.model.Patient;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class EmergencyQueueTest {

    @Test
    void dequeueOnEmptyQueueThrows() {
        EmergencyQueue queue = new EmergencyQueue();
        assertThrows(NoSuchElementException.class, queue::dequeue);
    }

    @Test
    void followsFifoOrder() {
        EmergencyQueue queue = new EmergencyQueue();
        Patient p1 = new Patient(1, "A", 20, "0", "x");
        Patient p2 = new Patient(2, "B", 20, "0", "x");
        Patient p3 = new Patient(3, "C", 20, "0", "x");

        queue.enqueue(p1);
        queue.enqueue(p2);
        queue.enqueue(p3);

        assertEquals(p1, queue.dequeue());
        assertEquals(p2, queue.dequeue());
        assertEquals(p3, queue.dequeue());
        assertTrue(queue.isEmpty());
    }

    @Test
    void displayAllPreservesOrderWithoutRemoving() {
        EmergencyQueue queue = new EmergencyQueue();
        queue.enqueue(new Patient(1, "A", 20, "0", "x"));
        queue.enqueue(new Patient(2, "B", 20, "0", "x"));

        List<Patient> snapshot = queue.displayAll();
        assertEquals(2, snapshot.size());
        assertEquals(2, queue.size()); // nothing removed
    }
}
