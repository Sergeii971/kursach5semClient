package com.verbovskiy.client.controller;

import com.verbovskiy.client.connection.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class UserInterfaceController {

    @FXML
    private Button buttonSubmitApplication;
    public void submitApplication(ActionEvent actionEvent) {
        buttonSubmitApplication.setOnAction(event -> {
            try {
                submitApplicationInterface();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    private void submitApplicationInterface() throws IOException {
        Stage newStage = new Stage();
        AnchorPane anchorPanePopup = (AnchorPane)  FXMLLoader.load(getClass().getResource("Fxml/submitApplication.fxml"));
        Scene scene = new Scene(anchorPanePopup);
        newStage.setScene(scene);
        newStage.initModality(Modality.APPLICATION_MODAL);
        newStage.setTitle("");
        newStage.setMaxHeight(730);
        newStage.setMaxWidth(900);
        newStage.showAndWait();
    }
    @FXML
    private Button buttonExitUser;
    public void exitUser(ActionEvent actionEvent) {
        buttonExitUser.setOnAction(event -> {
            ((Stage) ((Node) actionEvent.getSource()).getScene().getWindow()).close();
        });
    }
    @FXML
    private Button buttonWatchAnswers;
    public void watchAnswers(ActionEvent actionEvent) {
        buttonWatchAnswers.setOnAction(event -> {
            try {
                watchAnswersInterface();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    private void watchAnswersInterface() throws IOException {
        Stage newStage = new Stage();
        AnchorPane anchorPanePopup = (AnchorPane)  FXMLLoader.load(getClass().getResource("Fxml/watchAnswers.fxml"));
        Scene scene = new Scene(anchorPanePopup);
        newStage.setScene(scene);
        newStage.initModality(Modality.APPLICATION_MODAL);
        newStage.setTitle("");
        newStage.setMaxHeight(730);
        newStage.setMaxWidth(900);
        newStage.showAndWait();
    }
}
