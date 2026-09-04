package com.medicore.model;

import java.time.LocalDate;

/**
 * A single hospital visit record. Instances of this class are wrapped in
 * VisitNode and chained together inside a patient's VisitLinkedList.
 */
public class Visit {

    private final int visitId;
    private final LocalDate visitDate;
    private final String doctorName;
    private final String diagnosis;
    private final String treatment;

    public Visit(int visitId, LocalDate visitDate, String doctorName, String diagnosis, String treatment) {
        this.visitId = visitId;
        this.visitDate = visitDate;
        this.doctorName = doctorName;
        this.diagnosis = diagnosis;
        this.treatment = treatment;
    }

    public int getVisitId() {
        return visitId;
    }

    public LocalDate getVisitDate() {
        return visitDate;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public String getTreatment() {
        return treatment;
    }

    @Override
    public String toString() {
        return "Visit{id=" + visitId + ", date=" + visitDate + ", doctor='" + doctorName + "', diagnosis='" + diagnosis + "'}";
    }
}
