package com.medicore.controller;

import com.medicore.MainApp;
import com.medicore.model.Patient;
import com.medicore.model.TreatmentRecord;
import com.medicore.service.HospitalService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class DashboardController implements Initializable {

    @FXML private Label totalPatientsLabel;
    @FXML private Label waitingLabel;
    @FXML private Label treatedTodayLabel;
    @FXML private Label recordsLabel;
    @FXML private ListView<String> queuePreviewList;
    @FXML private ListView<String> treatmentPreviewList;

    private final HospitalService service = MainApp.getHospitalService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        totalPatientsLabel.setText(String.valueOf(service.totalPatients()));
        waitingLabel.setText(String.valueOf(service.queueSize()));
        treatedTodayLabel.setText(String.valueOf(service.treatmentsCompletedToday()));
        recordsLabel.setText(String.valueOf(service.totalPatients()));

        queuePreviewList.getItems().setAll(
                service.currentQueue().stream()
                        .limit(5)
                        .map(this::formatQueueRow)
                        .collect(Collectors.toList()));

        treatmentPreviewList.getItems().setAll(
                service.allTreatmentsMostRecentFirst().stream()
                        .limit(5)
                        .map(this::formatTreatmentRow)
                        .collect(Collectors.toList()));
    }

    private String formatQueueRow(Patient p) {
        return "#" + p.getPatientId() + "  " + p.getName() + "   [" + p.getPriority().getLabel() + "]";
    }

    private String formatTreatmentRow(TreatmentRecord r) {
        return "#" + r.getPatientId() + "  " + r.getPatientName() + "  ·  " + r.getTreatmentSummary();
    }
}
