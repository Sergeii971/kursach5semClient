package com.verbovskiy.client.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;

public class UserInterfaceController {
    private final Logger logger = LogManager.getLogger(UserInterfaceController.class);

    public Button buttonShowCars;
    public Button buttonChangePassword;
    public Button buttonForInvestors;

    public void showCars(ActionEvent actionEvent) {
        buttonShowCars.setOnAction(e -> {
            try {
                Stage newStage = new Stage();
                AnchorPane anchorPanePopup = (AnchorPane)  FXMLLoader.load(getClass().getResource("/view/userShowCars.fxml"));
                Scene scene = new Scene(anchorPanePopup);
                newStage.setScene(scene);
                newStage.initModality(Modality.APPLICATION_MODAL);
                newStage.setTitle("");
                newStage.setMaxHeight(730);
                newStage.setMaxWidth(950);
                newStage.showAndWait();
            } catch (IOException ex) {
                logger.log(Level.ERROR, "error while opening show cars interface");
            }
        });
    }

    public void changePassword(ActionEvent actionEvent) {
        buttonChangePassword.setOnAction(e -> {
            try {
                Stage newStage = new Stage();
                AnchorPane anchorPanePopup = (AnchorPane)  FXMLLoader.load(getClass().getResource("/view/changePassword.fxml"));
                Scene scene = new Scene(anchorPanePopup);
                newStage.setScene(scene);
                newStage.initModality(Modality.APPLICATION_MODAL);
                newStage.setTitle("Управление учетными записями");
                newStage.setMaxHeight(730);
                newStage.setMaxWidth(900);
                newStage.showAndWait();
            } catch (IOException ex) {
                logger.log(Level.ERROR, "error while show change password");
            }
        });
    }

    public void showForInvestors(ActionEvent actionEvent) {
        buttonForInvestors.setOnAction(e -> {
            try {
                Stage newStage = new Stage();
                AnchorPane anchorPanePopup = (AnchorPane)  FXMLLoader.load(getClass().getResource("/view/forInvestors.fxml"));
                Scene scene = new Scene(anchorPanePopup);
                newStage.setScene(scene);
                newStage.initModality(Modality.APPLICATION_MODAL);
                newStage.setTitle("Управление учетными записями");
                newStage.setMaxHeight(730);
                newStage.setMaxWidth(900);
                newStage.showAndWait();
            } catch (IOException ex) {
                logger.log(Level.ERROR, "error while show for investors");
            }
        });
    }

    @FXML
    private Button buttonExitUser;

    public void exitUser(ActionEvent actionEvent) {
        buttonExitUser.setOnAction(event -> {
            ((Stage) ((Node) actionEvent.getSource()).getScene().getWindow()).close();
        });
    }
}
