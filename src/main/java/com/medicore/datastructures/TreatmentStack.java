package com.medicore.datastructures;

import com.medicore.model.TreatmentRecord;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EmptyStackException;
import java.util.List;

/**
 * LIFO stack of completed treatment records.
 * Backed by a dynamic array (ArrayList) rather than java.util.Stack so the
 * push/pop mechanics are implemented explicitly for the assignment.
 */
public class TreatmentStack {

    private final List<TreatmentRecord> elements = new ArrayList<>();

    public void push(TreatmentRecord record) {
        elements.add(record);
    }

    /**
     * Removes and returns the most recently completed treatment record.
     * @throws EmptyStackException if the stack is empty
     */
    public TreatmentRecord pop() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return elements.remove(elements.size() - 1);
    }

    public TreatmentRecord peek() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return elements.get(elements.size() - 1);
    }

    public boolean isEmpty() {
        return elements.isEmpty();
    }

    public int size() {
        return elements.size();
    }

    /** Returns all treatment records, most recent first (top of stack first). */
    public List<TreatmentRecord> displayAll() {
        List<TreatmentRecord> reversed = new ArrayList<>(elements);
        Collections.reverse(reversed);
        return reversed;
    }
}
