package com.medicore.datastructures;

import com.medicore.model.Patient;

/**
 * Node used internally by PatientBST. Keyed by patientId for BST ordering.
 */
public class BSTNode {

    int patientId;
    Patient patient;
    BSTNode left;
    BSTNode right;

    public BSTNode(Patient patient) {
        this.patientId = patient.getPatientId();
        this.patient = patient;
    }
}
