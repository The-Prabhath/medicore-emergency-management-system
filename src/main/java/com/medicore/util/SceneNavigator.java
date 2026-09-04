package com.medicore.util;

import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.IOException;

/**
 * Loads FXML screens into the shared content region of main-layout.fxml
 * and cross-fades between them. Registered once from MainApp.
 */
public class SceneNavigator {

    private static Scene scene;
    private static StackPane contentRegion;

    public static void init(Scene scene) {
        SceneNavigator.scene = scene;
    }

    public static void registerContentRegion(StackPane region) {
        contentRegion = region;
    }

    public static void navigateTo(String fxmlName) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    SceneNavigator.class.getResource("/com/medicore/fxml/" + fxmlName + ".fxml"));
            Node newView = loader.load();
            newView.setOpacity(0);

            contentRegion.getChildren().setAll(newView);

            FadeTransition fade = new FadeTransition(Duration.millis(200), newView);
            fade.setFromValue(0);
            fade.setToValue(1);
            fade.play();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load screen: " + fxmlName, e);
        }
    }
}
