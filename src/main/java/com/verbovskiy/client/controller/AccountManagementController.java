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

public class AccountManagementController {
    private final Logger logger = LogManager.getLogger(AccountManagementController.class);

    @FXML
    private Button buttonShowUsers;
    public void showUsers(ActionEvent actionEvent) {
        buttonShowUsers.setOnAction(event -> {
            try {
                Stage newStage = new Stage();
                AnchorPane anchorPanePopup = (AnchorPane)  FXMLLoader.load(getClass().getResource("/view/showUsers.fxml"));
                Scene scene = new Scene(anchorPanePopup);
                newStage.setScene(scene);
                newStage.initModality(Modality.APPLICATION_MODAL);
                newStage.setTitle("");
                newStage.setMaxHeight(767);
                newStage.setMaxWidth(934);
                newStage.showAndWait();
            } catch (IOException e) {
                logger.log(Level.ERROR, "error while show users");
            }
        });
    }

    @FXML
    private Button buttonChangeAdminData;
    public void changeAdminData(ActionEvent actionEvent) {
        buttonChangeAdminData.setOnAction(event -> {
            try {
                Stage newStage = new Stage();
                AnchorPane anchorPanePopup = (AnchorPane)  FXMLLoader.load(getClass().getResource("/view/changeAdminData.fxml"));
                Scene scene = new Scene(anchorPanePopup);
                newStage.setScene(scene);
                newStage.initModality(Modality.APPLICATION_MODAL);
                newStage.setTitle("Управление учетными записями");
                newStage.setMaxHeight(730);
                newStage.setMaxWidth(900);
                newStage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    private Button buttonExitAccountsManaging;
    public void exitAccountsManaging(ActionEvent actionEvent) {
        buttonExitAccountsManaging.setOnAction(event -> {
            ((Stage) ((Node) actionEvent.getSource()).getScene().getWindow()).close();
        });
    }
}
