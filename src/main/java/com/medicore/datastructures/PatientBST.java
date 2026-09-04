package com.medicore.datastructures;

import com.medicore.model.Patient;

import java.util.ArrayList;
import java.util.List;

/**
 * Binary Search Tree storing Patient records keyed by patientId.
 *
 * Required operations: insert, search, delete, in-order traversal.
 * Average case O(log n) for insert/search/delete on a balanced tree;
 * worst case O(n) if input arrives in sorted order (not self-balancing —
 * a deliberate scope decision to keep the assignment focused).
 */
public class PatientBST {

    private BSTNode root;
    private int size;

    public boolean insert(Patient patient) {
        if (search(patient.getPatientId()) != null) {
            return false; // duplicate ID, reject
        }
        root = insertRecursive(root, patient);
        size++;
        return true;
    }

    private BSTNode insertRecursive(BSTNode node, Patient patient) {
        if (node == null) {
            return new BSTNode(patient);
        }
        if (patient.getPatientId() < node.patientId) {
            node.left = insertRecursive(node.left, patient);
        } else if (patient.getPatientId() > node.patientId) {
            node.right = insertRecursive(node.right, patient);
        }
        return node;
    }

    public Patient search(int patientId) {
        BSTNode node = searchRecursive(root, patientId);
        return node == null ? null : node.patient;
    }

    private BSTNode searchRecursive(BSTNode node, int patientId) {
        if (node == null || node.patientId == patientId) {
            return node;
        }
        return patientId < node.patientId
                ? searchRecursive(node.left, patientId)
                : searchRecursive(node.right, patientId);
    }

    public boolean delete(int patientId) {
        if (search(patientId) == null) {
            return false;
        }
        root = deleteRecursive(root, patientId);
        size--;
        return true;
    }

    private BSTNode deleteRecursive(BSTNode node, int patientId) {
        if (node == null) {
            return null;
        }
        if (patientId < node.patientId) {
            node.left = deleteRecursive(node.left, patientId);
        } else if (patientId > node.patientId) {
            node.right = deleteRecursive(node.right, patientId);
        } else {
            // Node to delete found
            if (node.left == null) {
                return node.right;
            }
            if (node.right == null) {
                return node.left;
            }
            // Two children: replace with in-order successor (smallest in right subtree)
            BSTNode successor = findMin(node.right);
            node.patientId = successor.patientId;
            node.patient = successor.patient;
            node.right = deleteRecursive(node.right, successor.patientId);
        }
        return node;
    }

    private BSTNode findMin(BSTNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    /** In-order traversal: returns patients sorted ascending by Patient ID. */
    public List<Patient> inOrderTraversal() {
        List<Patient> result = new ArrayList<>();
        inOrderRecursive(root, result);
        return result;
    }

    private void inOrderRecursive(BSTNode node, List<Patient> result) {
        if (node == null) return;
        inOrderRecursive(node.left, result);
        result.add(node.patient);
        inOrderRecursive(node.right, result);
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return root == null;
    }
}
