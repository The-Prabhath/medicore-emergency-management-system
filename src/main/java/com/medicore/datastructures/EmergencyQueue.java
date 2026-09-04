package com.medicore.datastructures;

import com.medicore.model.Patient;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * FIFO queue of patients waiting for emergency treatment.
 * Implemented from scratch with a singly linked chain of QueueNode
 * (rather than java.util.Queue) so the underlying mechanics are explicit
 * and demonstrable for the assignment.
 */
public class EmergencyQueue {

    private static class QueueNode {
        Patient patient;
        QueueNode next;
        QueueNode(Patient patient) {
            this.patient = patient;
        }
    }

    private QueueNode front;
    private QueueNode rear;
    private int size;

    public void enqueue(Patient patient) {
        QueueNode node = new QueueNode(patient);
        if (rear == null) {
            front = rear = node;
        } else {
            rear.next = node;
            rear = node;
        }
        size++;
    }

    /**
     * Removes and returns the patient who has been waiting longest.
     * @throws NoSuchElementException if the queue is empty
     */
    public Patient dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Emergency queue is empty — no patients waiting.");
        }
        Patient patient = front.patient;
        front = front.next;
        if (front == null) {
            rear = null;
        }
        size--;
        return patient;
    }

    public Patient peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Emergency queue is empty.");
        }
        return front.patient;
    }

    public boolean isEmpty() {
        return front == null;
    }

    public int size() {
        return size;
    }

    /** Returns all waiting patients in FIFO order without removing them. */
    public List<Patient> displayAll() {
        List<Patient> result = new ArrayList<>();
        QueueNode current = front;
        while (current != null) {
            result.add(current.patient);
            current = current.next;
        }
        return result;
    }
}
