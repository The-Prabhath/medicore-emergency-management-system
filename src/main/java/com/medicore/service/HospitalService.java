package com.medicore.service;

import com.medicore.datastructures.EmergencyQueue;
import com.medicore.datastructures.PatientBST;
import com.medicore.datastructures.TreatmentStack;
import com.medicore.model.Patient;
import com.medicore.model.TreatmentRecord;
import com.medicore.model.Visit;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Single façade that owns the four core data structures and exposes a
 * clean API to controllers. Controllers never touch PatientBST /
 * EmergencyQueue / TreatmentStack / VisitLinkedList directly — this keeps
 * the UI layer decoupled from the data-structure implementations, and
 * makes both sides independently testable.
 */
public class HospitalService {

    private final PatientBST patientRecords = new PatientBST();
    private final EmergencyQueue emergencyQueue = new EmergencyQueue();
    private final TreatmentStack treatmentHistory = new TreatmentStack();

    private int nextVisitId = 1;

    // ----- Patient records (BST) -----

    public boolean registerPatient(Patient patient) {
        return patientRecords.insert(patient);
    }

    public Patient findPatient(int patientId) {
        return patientRecords.search(patientId);
    }

    public boolean deletePatient(int patientId) {
        return patientRecords.delete(patientId);
    }

    public List<Patient> allPatientsSortedById() {
        return patientRecords.inOrderTraversal();
    }

    public int totalPatients() {
        return patientRecords.size();
    }

    // ----- Emergency queue -----

    public void addToQueue(Patient patient) {
        emergencyQueue.enqueue(patient);
    }

    /** Calls the next patient for treatment and removes them from the queue. */
    public Patient callNextPatient() {
        return emergencyQueue.dequeue();
    }

    public List<Patient> currentQueue() {
        return emergencyQueue.displayAll();
    }

    public int queueSize() {
        return emergencyQueue.size();
    }

    public boolean isQueueEmpty() {
        return emergencyQueue.isEmpty();
    }

    // ----- Treatment history (Stack) -----

    public void completeTreatment(Patient patient, String treatmentSummary) {
        TreatmentRecord record = new TreatmentRecord(
                patient.getPatientId(), patient.getName(), treatmentSummary, LocalDateTime.now());
        treatmentHistory.push(record);
    }

    /** Undoes the most recently logged treatment (pop). */
    public TreatmentRecord undoLastTreatment() {
        return treatmentHistory.pop();
    }

    public List<TreatmentRecord> allTreatmentsMostRecentFirst() {
        return treatmentHistory.displayAll();
    }

    public int treatmentsCompletedToday() {
        LocalDateTime startOfDay = LocalDateTime.now().toLocalDate().atStartOfDay();
        return (int) treatmentHistory.displayAll().stream()
                .filter(r -> r.getCompletedAt().isAfter(startOfDay))
                .count();
    }

    // ----- Visit history (Singly Linked List, per patient) -----

    public Visit addVisit(int patientId, String doctorName, String diagnosis, String treatment) {
        Patient patient = patientRecords.search(patientId);
        if (patient == null) {
            throw new NoSuchElementException("No patient found with ID " + patientId);
        }
        Visit visit = new Visit(nextVisitId++, java.time.LocalDate.now(), doctorName, diagnosis, treatment);
        patient.getVisitHistory().addVisit(visit);
        return visit;
    }

    public boolean removeVisit(int patientId, int visitId) {
        Patient patient = patientRecords.search(patientId);
        return patient != null && patient.getVisitHistory().removeVisit(visitId);
    }

    public Visit findVisit(int patientId, int visitId) {
        Patient patient = patientRecords.search(patientId);
        return patient == null ? null : patient.getVisitHistory().searchVisit(visitId);
    }

    public List<Visit> visitHistoryFor(int patientId) {
        Patient patient = patientRecords.search(patientId);
        if (patient == null) {
            throw new NoSuchElementException("No patient found with ID " + patientId);
        }
        return patient.getVisitHistory().displayAll();
    }
}
