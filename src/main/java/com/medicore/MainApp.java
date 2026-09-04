package com.medicore;

import com.medicore.service.HospitalService;
import com.medicore.util.SceneNavigator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {

    private static HospitalService hospitalService;

    @Override
    public void start(Stage primaryStage) throws IOException {
        hospitalService = new HospitalService();
        seedDemoData(hospitalService);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/medicore/fxml/main-layout.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, 1200, 780);
        scene.getStylesheets().add(getClass().getResource("/com/medicore/css/theme-light.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("/com/medicore/css/components.css").toExternalForm());

        SceneNavigator.init(scene);

        primaryStage.setTitle("MediCore — Emergency Management System");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(1000);
        primaryStage.setMinHeight(680);
        primaryStage.show();
    }

    public static HospitalService getHospitalService() {
        return hospitalService;
    }

    /** Seeds a handful of records so the UI isn't empty on first run. */
    private static void seedDemoData(HospitalService service) {
        com.medicore.model.Patient p1 = new com.medicore.model.Patient(1042, "Nadeesha Perera", 34, "0771234567", "Chest pain");
        p1.setPriority(com.medicore.model.Priority.CRITICAL);
        com.medicore.model.Patient p2 = new com.medicore.model.Patient(1039, "Kasun Rathnayake", 28, "0777654321", "Fracture - left arm");
        p2.setPriority(com.medicore.model.Priority.URGENT);
        com.medicore.model.Patient p3 = new com.medicore.model.Patient(1035, "Ishara Dias", 45, "0712223334", "Fever and dehydration");
        p3.setPriority(com.medicore.model.Priority.STANDARD);

        service.registerPatient(p1);
        service.registerPatient(p2);
        service.registerPatient(p3);

        service.addToQueue(p1);
        service.addToQueue(p2);
        service.addToQueue(p3);

        service.addVisit(1042, "Dr. Fernando", "Angina", "ECG + monitoring");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
