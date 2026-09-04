package com.medicore.datastructures;

import com.medicore.model.Visit;

import java.util.ArrayList;
import java.util.List;

/**
 * Singly linked list holding one patient's chronological visit history.
 * Required operations: add, remove, search, display.
 */
public class VisitLinkedList {

    private static class VisitNode {
        Visit visit;
        VisitNode next;
        VisitNode(Visit visit) {
            this.visit = visit;
        }
    }

    private VisitNode head;
    private VisitNode tail;
    private int size;

    /** Appends a new visit to the end of the history (chronological order). */
    public void addVisit(Visit visit) {
        VisitNode node = new VisitNode(visit);
        if (head == null) {
            head = tail = node;
        } else {
            tail.next = node;
            tail = node;
        }
        size++;
    }

    /** Removes the visit with the given visitId. Returns true if removed. */
    public boolean removeVisit(int visitId) {
        if (head == null) return false;

        if (head.visit.getVisitId() == visitId) {
            head = head.next;
            if (head == null) tail = null;
            size--;
            return true;
        }

        VisitNode current = head;
        while (current.next != null) {
            if (current.next.visit.getVisitId() == visitId) {
                current.next = current.next.next;
                if (current.next == null) tail = current;
                size--;
                return true;
            }
            current = current.next;
        }
        return false;
    }

    /** Linear search for a visit by visitId. Returns null if not found. */
    public Visit searchVisit(int visitId) {
        VisitNode current = head;
        while (current != null) {
            if (current.visit.getVisitId() == visitId) {
                return current.visit;
            }
            current = current.next;
        }
        return null;
    }

    /** Returns the full visit history in chronological (insertion) order. */
    public List<Visit> displayAll() {
        List<Visit> result = new ArrayList<>();
        VisitNode current = head;
        while (current != null) {
            result.add(current.visit);
            current = current.next;
        }
        return result;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return head == null;
    }
}
