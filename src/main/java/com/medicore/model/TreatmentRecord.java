package com.medicore.model;

import java.time.LocalDateTime;

/**
 * A completed treatment event. Pushed onto TreatmentStack when a patient
 * finishes emergency treatment, so the most recently completed treatment
 * is always the one on top (LIFO).
 */
public class TreatmentRecord {

    private final int patientId;
    private final String patientName;
    private final String treatmentSummary;
    private final LocalDateTime completedAt;

    public TreatmentRecord(int patientId, String patientName, String treatmentSummary, LocalDateTime completedAt) {
        this.patientId = patientId;
        this.patientName = patientName;
        this.treatmentSummary = treatmentSummary;
        this.completedAt = completedAt;
    }

    public int getPatientId() {
        return patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getTreatmentSummary() {
        return treatmentSummary;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    @Override
    public String toString() {
        return "TreatmentRecord{patientId=" + patientId + ", patient='" + patientName
                + "', summary='" + treatmentSummary + "', completedAt=" + completedAt + "}";
    }
}
