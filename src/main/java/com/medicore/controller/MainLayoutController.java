package com.medicore.controller;

import com.medicore.util.SceneNavigator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MainLayoutController implements Initializable {

    @FXML
    private StackPane contentRegion;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SceneNavigator.registerContentRegion(contentRegion);
        SceneNavigator.navigateTo("dashboard");
    }

    @FXML
    private void goToDashboard() {
        SceneNavigator.navigateTo("dashboard");
    }

    @FXML
    private void goToPatients() {
        SceneNavigator.navigateTo("patient-records");
    }

    @FXML
    private void goToQueue() {
        SceneNavigator.navigateTo("emergency-queue");
    }

    @FXML
    private void goToTreatments() {
        SceneNavigator.navigateTo("treatment-history");
    }

    @FXML
    private void goToVisits() {
        SceneNavigator.navigateTo("visit-history");
    }

    @FXML
    private void goToSettings() {
        SceneNavigator.navigateTo("settings");
    }
}
