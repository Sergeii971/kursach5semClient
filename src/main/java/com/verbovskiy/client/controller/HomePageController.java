package com.verbovskiy.client.controller;

import com.verbovskiy.client.connection.*;
import com.verbovskiy.client.information_window.InformationWindow;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;

public class HomePageController {
    private final Logger logger = LogManager.getLogger(HomePageController.class);
    @FXML
    private Button registrationButton;
    @FXML
    private TextField textFieldLogin;
    @FXML
    private PasswordField passwordField;

    public void authorization(ActionEvent actionEvent) {
        try {
            Session.getInstance().get().clear();
            ServerConnection connection = ServerConnection.getInstance();
            String login = textFieldLogin.getText();
            String password = passwordField.getText();
            ThreadLocal<UserRequest> request = connection.getRequest();
            request.get().setAttribute(RequestParameter.COMMAND_NAME, "AUTHENTICATION");
            request.get().setAttribute(RequestParameter.LOGIN, login);
            request.get().setAttribute(RequestParameter.PASSWORD, password);
            connection.sendRequest();
            ThreadLocal<ServerResponse> response = connection.getResponse();
            boolean successfulAuthentication = (boolean) response.get().getAttribute(AttributeKey.SUCCESSFUL_AUTHENTICATION);
            boolean successfulActivation = (boolean) response.get().getAttribute(AttributeKey.SUCCESSFUL_ACTIVATION);
            if (successfulAuthentication) {
                if (successfulActivation) {
                    boolean isAdmin = (boolean) response.get().getAttribute(RequestParameter.IS_ADMIN);
                    ThreadLocal<Session> session = Session.getInstance();
                    session.get().setAttribute(RequestParameter.EMAIL, login);
                    session.get().setAttribute(RequestParameter.IS_ADMIN, isAdmin);
                    textFieldLogin.clear();
                    passwordField.clear();
                    if (isAdmin) {
                        adminInterface();
                    } else {
                        userInterface();
                    }
                } else {
                    passwordField.clear();
                    InformationWindow.showAccountIsBlocked();
                }
            } else {
                passwordField.clear();
                InformationWindow.showAuthorizationError();
            }
        } catch (IOException e) {
            logger.log(Level.ERROR, "error while opening new window" + e);
        }
    }

    public void registration(ActionEvent actionEvent) {
        registrationButton.setOnAction(event -> {
            try {
                Session.getInstance().get().clear();
                registrationInterface();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    private void registrationInterface() throws IOException {
        Stage newStage = new Stage();
        AnchorPane anchorPanePopup = (AnchorPane)  FXMLLoader.load(getClass().getResource("/view/registration.fxml"));
        Scene scene = new Scene(anchorPanePopup);
        newStage.setScene(scene);
        newStage.initModality(Modality.APPLICATION_MODAL);
        newStage.setTitle("Регистрация");
        newStage.setMaxHeight(730);
        newStage.setMaxWidth(900);
        newStage.showAndWait();
    }
    private void adminInterface() throws IOException {
        Stage newStage = new Stage();
        AnchorPane anchorPanePopup = (AnchorPane)  FXMLLoader.load(getClass().getResource("/view/admin.fxml"));
        Scene scene = new Scene(anchorPanePopup);
        newStage.setScene(scene);
        newStage.initModality(Modality.APPLICATION_MODAL);
        newStage.setTitle("");
        newStage.setMaxHeight(730);
        newStage.setMaxWidth(900);
        newStage.showAndWait();
    }
    private void userInterface() throws IOException {
        Stage newStage = new Stage();
        AnchorPane anchorPanePopup = (AnchorPane)  FXMLLoader.load(getClass().getResource("/view/userInterface.fxml"));
        Scene scene = new Scene(anchorPanePopup);
        newStage.setScene(scene);
        newStage.initModality(Modality.APPLICATION_MODAL);
        newStage.setTitle("");
        newStage.setMaxHeight(730);
        newStage.setMaxWidth(900);
        newStage.showAndWait();
    }
}
