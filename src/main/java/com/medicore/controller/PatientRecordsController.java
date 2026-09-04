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
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class PatientRecordsController implements Initializable {

    @FXML private TextField idField;
    @FXML private TextField nameField;
    @FXML private TextField ageField;
    @FXML private TextField contactField;
    @FXML private TextField conditionField;
    @FXML private Label formMessageLabel;

    @FXML private TextField searchField;
    @FXML private Label searchResultLabel;
    @FXML private ListView<String> patientListView;

    private final HospitalService service = MainApp.getHospitalService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        refreshList();
    }

    @FXML
    private void handleInsert() {
        try {
            int id = Integer.parseInt(idField.getText().trim());
            String name = nameField.getText().trim();
            int age = Integer.parseInt(ageField.getText().trim());
            String contact = contactField.getText().trim();
            String condition = conditionField.getText().trim();

            if (name.isEmpty() || condition.isEmpty()) {
                formMessageLabel.setText("Name and condition are required.");
                return;
            }

            boolean inserted = service.registerPatient(new Patient(id, name, age, contact, condition));
            formMessageLabel.setText(inserted
                    ? "Patient #" + id + " registered."
                    : "A patient with ID " + id + " already exists.");

            if (inserted) {
                idField.clear(); nameField.clear(); ageField.clear();
                contactField.clear(); conditionField.clear();
                refreshList();
            }
        } catch (NumberFormatException e) {
            formMessageLabel.setText("Patient ID and Age must be numbers.");
        }
    }

    @FXML
    private void handleSearch() {
        try {
            int id = Integer.parseInt(searchField.getText().trim());
            Patient patient = service.findPatient(id);
            searchResultLabel.setText(patient == null
                    ? "No patient found with ID " + id
                    : "Found: " + patient);
        } catch (NumberFormatException e) {
            searchResultLabel.setText("Enter a valid numeric Patient ID.");
        }
    }

    @FXML
    private void handleDelete() {
        try {
            int id = Integer.parseInt(searchField.getText().trim());
            boolean deleted = service.deletePatient(id);
            searchResultLabel.setText(deleted
                    ? "Patient #" + id + " deleted."
                    : "No patient found with ID " + id);
            if (deleted) refreshList();
        } catch (NumberFormatException e) {
            searchResultLabel.setText("Enter a valid numeric Patient ID.");
        }
    }

    @FXML
    private void handleRefresh() {
        refreshList();
    }

    private void refreshList() {
        patientListView.getItems().setAll(
                service.allPatientsSortedById().stream()
                        .map(p -> "#" + p.getPatientId() + "  " + p.getName()
                                + "  ·  age " + p.getAge()
                                + "  ·  " + p.getMedicalCondition())
                        .collect(Collectors.toList()));
    }
}
