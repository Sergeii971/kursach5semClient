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

public class AdminInterfaceController {
    private final Logger logger = LogManager.getLogger(AdminInterfaceController.class);
    public Button buttonShowCars;
    public Button buttonShowOrders;
    @FXML
    private Button buttonAddCar;
    @FXML
    private Button buttonAccount;
    @FXML
    private Button buttonExitAdmin;

    public void addCar(ActionEvent actionEvent) {
        buttonAddCar.setOnAction(event -> {
            try {
                Stage newStage = new Stage();
                AnchorPane anchorPanePopup = (AnchorPane)  FXMLLoader.load(getClass().getResource("/view/addCar.fxml"));
                Scene scene = new Scene(anchorPanePopup);
                newStage.setScene(scene);
                newStage.initModality(Modality.APPLICATION_MODAL);
                newStage.setTitle("");
                newStage.setMaxHeight(838);
                newStage.setMaxWidth(900);
                newStage.showAndWait();
            } catch (IOException e) {
                logger.log(Level.ERROR, "error while opening adding car");
            }
        });
    }

    public void openAccountsManagementInterface(ActionEvent actionEvent) {
        buttonAccount.setOnAction(event -> {
            try {
                Stage newStage = new Stage();
                AnchorPane anchorPanePopup = (AnchorPane)  FXMLLoader.load(getClass().getResource("/view/accountsManagement.fxml"));
                Scene scene = new Scene(anchorPanePopup);
                newStage.setScene(scene);
                newStage.initModality(Modality.APPLICATION_MODAL);
                newStage.setTitle("");
                newStage.setMaxHeight(530);
                newStage.setMaxWidth(700);
                newStage.showAndWait();
            } catch (IOException e) {
                logger.log(Level.ERROR, "error while opening account management interface");
            }
        });
    }

    public void exitAdmin(ActionEvent actionEvent) {
        buttonExitAdmin.setOnAction(event -> {
            ((Stage) ((Node) actionEvent.getSource()).getScene().getWindow()).close();
        });

    }

    public void showCars(ActionEvent actionEvent) {
        buttonShowCars.setOnAction(event -> {
            try {
                Stage newStage = new Stage();
                AnchorPane anchorPanePopup = (AnchorPane)  FXMLLoader.load(getClass().getResource("/view/adminShowCars.fxml"));
                Scene scene = new Scene(anchorPanePopup);
                newStage.setScene(scene);
                newStage.initModality(Modality.APPLICATION_MODAL);
                newStage.setTitle("");
                newStage.setMaxHeight(730);
                newStage.setMaxWidth(950);
                newStage.showAndWait();
            } catch (IOException e) {
                logger.log(Level.ERROR, "error while opening show cars interface");
            }
        });
    }

    public void showOrders(ActionEvent actionEvent) {
        buttonShowOrders.setOnAction(e -> {
            try {
                Stage newStage = new Stage();
                AnchorPane anchorPanePopup = (AnchorPane)  FXMLLoader.load(getClass().getResource("/view/showOrders.fxml"));
                Scene scene = new Scene(anchorPanePopup);
                newStage.setScene(scene);
                newStage.initModality(Modality.APPLICATION_MODAL);
                newStage.setTitle("");
                newStage.setMaxHeight(730);
                newStage.setMaxWidth(950);
                newStage.showAndWait();
            } catch (IOException ex) {
                logger.log(Level.ERROR, "error while opening show orders interface");
            }
        });
    }
}
