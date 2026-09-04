package com.medicore.controller;

import com.medicore.MainApp;
import com.medicore.model.Patient;
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

public class EmergencyQueueController implements Initializable {

    @FXML private TextField enqueueIdField;
    @FXML private Label statusLabel;
    @FXML private ListView<String> queueListView;

    private final HospitalService service = MainApp.getHospitalService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        refreshList();
    }

    @FXML
    private void handleEnqueue() {
        try {
            int id = Integer.parseInt(enqueueIdField.getText().trim());
            Patient patient = service.findPatient(id);
            if (patient == null) {
                statusLabel.setText("No registered patient with ID " + id + ". Register them first.");
                return;
            }
            service.addToQueue(patient);
            statusLabel.setText("Added #" + id + " (" + patient.getName() + ") to the queue.");
            enqueueIdField.clear();
            refreshList();
        } catch (NumberFormatException e) {
            statusLabel.setText("Enter a valid numeric Patient ID.");
        }
    }

    @FXML
    private void handleDequeue() {
        try {
            Patient patient = service.callNextPatient();
            statusLabel.setText("Now treating #" + patient.getPatientId() + " (" + patient.getName() + ").");
            refreshList();
        } catch (NoSuchElementException e) {
            statusLabel.setText("Queue is empty — no patients waiting.");
        }
    }

    private void refreshList() {
        queueListView.getItems().setAll(
                service.currentQueue().stream()
                        .map(p -> "#" + p.getPatientId() + "  " + p.getName()
                                + "   [" + p.getPriority().getLabel() + "]")
                        .collect(Collectors.toList()));
    }
}
