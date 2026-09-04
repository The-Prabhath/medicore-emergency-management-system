package com.medicore.controller;

import com.medicore.MainApp;
import com.medicore.model.Patient;
import com.medicore.model.TreatmentRecord;
import com.medicore.service.HospitalService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.EmptyStackException;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class TreatmentHistoryController implements Initializable {

    @FXML private TextField patientIdField;
    @FXML private TextField summaryField;
    @FXML private Label statusLabel;
    @FXML private ListView<String> stackListView;

    private final HospitalService service = MainApp.getHospitalService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        refreshList();
    }

    @FXML
    private void handlePush() {
        try {
            int id = Integer.parseInt(patientIdField.getText().trim());
            String summary = summaryField.getText().trim();
            Patient patient = service.findPatient(id);

            if (patient == null) {
                statusLabel.setText("No registered patient with ID " + id + ".");
                return;
            }
            if (summary.isEmpty()) {
                statusLabel.setText("Enter a treatment summary.");
                return;
            }

            service.completeTreatment(patient, summary);
            statusLabel.setText("Logged treatment for #" + id + ".");
            patientIdField.clear();
            summaryField.clear();
            refreshList();
        } catch (NumberFormatException e) {
            statusLabel.setText("Enter a valid numeric Patient ID.");
        }
    }

    @FXML
    private void handlePop() {
        try {
            TreatmentRecord record = service.undoLastTreatment();
            statusLabel.setText("Undid treatment for #" + record.getPatientId() + " (" + record.getPatientName() + ").");
            refreshList();
        } catch (EmptyStackException e) {
            statusLabel.setText("No treatment records to undo.");
        }
    }

    private void refreshList() {
        stackListView.getItems().setAll(
                service.allTreatmentsMostRecentFirst().stream()
                        .map(r -> "#" + r.getPatientId() + "  " + r.getPatientName()
                                + "  ·  " + r.getTreatmentSummary()
                                + "  ·  " + r.getCompletedAt().toLocalTime().withNano(0))
                        .collect(Collectors.toList()));
    }
}
