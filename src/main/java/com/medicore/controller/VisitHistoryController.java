package com.medicore.controller;

import com.medicore.MainApp;
import com.medicore.model.Visit;
import com.medicore.service.HospitalService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class VisitHistoryController implements Initializable {

    @FXML private TextField patientIdField;
    @FXML private TextField doctorField;
    @FXML private TextField diagnosisField;
    @FXML private TextField treatmentField;
    @FXML private TextField visitIdField;
    @FXML private Label statusLabel;
    @FXML private ListView<String> visitListView;

    private final HospitalService service = MainApp.getHospitalService();
    private int currentPatientId = -1;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Nothing loaded until a patient ID is entered.
    }

    @FXML
    private void handleLoad() {
        try {
            currentPatientId = Integer.parseInt(patientIdField.getText().trim());
            refreshList();
            statusLabel.setText("Loaded history for #" + currentPatientId + ".");
        } catch (NumberFormatException e) {
            statusLabel.setText("Enter a valid numeric Patient ID.");
        } catch (NoSuchElementException e) {
            statusLabel.setText(e.getMessage());
        }
    }

    @FXML
    private void handleAddVisit() {
        if (currentPatientId == -1) {
            statusLabel.setText("Load a patient first.");
            return;
        }
        String doctor = doctorField.getText().trim();
        String diagnosis = diagnosisField.getText().trim();
        String treatment = treatmentField.getText().trim();
        if (doctor.isEmpty() || diagnosis.isEmpty()) {
            statusLabel.setText("Doctor and diagnosis are required.");
            return;
        }
        try {
            Visit visit = service.addVisit(currentPatientId, doctor, diagnosis, treatment);
            statusLabel.setText("Added visit #" + visit.getVisitId() + ".");
            doctorField.clear(); diagnosisField.clear(); treatmentField.clear();
            refreshList();
        } catch (NoSuchElementException e) {
            statusLabel.setText(e.getMessage());
        }
    }

    @FXML
    private void handleSearchVisit() {
        if (currentPatientId == -1) {
            statusLabel.setText("Load a patient first.");
            return;
        }
        try {
            int visitId = Integer.parseInt(visitIdField.getText().trim());
            Visit visit = service.findVisit(currentPatientId, visitId);
            statusLabel.setText(visit == null ? "No visit found with ID " + visitId : "Found: " + visit);
        } catch (NumberFormatException e) {
            statusLabel.setText("Enter a valid numeric Visit ID.");
        }
    }

    @FXML
    private void handleRemoveVisit() {
        if (currentPatientId == -1) {
            statusLabel.setText("Load a patient first.");
            return;
        }
        try {
            int visitId = Integer.parseInt(visitIdField.getText().trim());
            boolean removed = service.removeVisit(currentPatientId, visitId);
            statusLabel.setText(removed ? "Removed visit #" + visitId + "." : "No visit found with ID " + visitId);
            if (removed) refreshList();
        } catch (NumberFormatException e) {
            statusLabel.setText("Enter a valid numeric Visit ID.");
        }
    }

    private void refreshList() {
        visitListView.getItems().setAll(
                service.visitHistoryFor(currentPatientId).stream()
                        .map(v -> "#" + v.getVisitId() + "  " + v.getVisitDate()
                                + "  ·  Dr. " + v.getDoctorName()
                                + "  ·  " + v.getDiagnosis())
                        .collect(Collectors.toList()));
    }
}
