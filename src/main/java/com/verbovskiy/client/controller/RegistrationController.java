package com.verbovskiy.client.controller;

import com.verbovskiy.client.connection.*;
import com.verbovskiy.client.information_window.InformationWindow;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Map;

public class RegistrationController {
    @FXML
    private TextField textFieldName;
    @FXML
    private TextField textFieldSurname;
    @FXML
    private TextField textFieldLogin;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button buttonSave;

    public void registration(ActionEvent actionEvent) {
        buttonSave.setOnAction(event -> {
            String name = textFieldName.getText();
            String surname = textFieldSurname.getText();
            String email = textFieldLogin.getText();
            String password = passwordField.getText();

            if (name.isEmpty() || surname.isEmpty() || email.isEmpty() || password.isEmpty()) {
                InformationWindow.showLineIsEmpty();
            } else {
                ServerConnection connection = ServerConnection.getInstance();
                ThreadLocal<UserRequest> request = connection.getRequest();
                request.get().setAttribute(RequestParameter.COMMAND_NAME, "REGISTRATION");
                request.get().setAttribute(RequestParameter.NAME, name);
                request.get().setAttribute(RequestParameter.SURNAME, surname);
                request.get().setAttribute(RequestParameter.EMAIL, email);
                request.get().setAttribute(RequestParameter.PASSWORD, password);
                connection.sendRequest();
                ThreadLocal<ServerResponse> response = connection.getResponse();
                Map<String, Boolean> incorrectParameters = (Map<String, Boolean>) response.get().getAttribute(AttributeKey
                        .INCORRECT_PARAMETER);
                if (incorrectParameters.isEmpty()) {
                    ((Stage) ((Node) actionEvent.getSource()).getScene().getWindow()).close();
                    InformationWindow.showSuccessfulSaving();
                } else {
                    passwordField.clear();
                    InformationWindow.showRegistrationError(incorrectParameters);
                }
            }});
    }

    @FXML
    private Button buttonExitRegistration;
    public void exitRegistration(ActionEvent actionEvent) {
        buttonExitRegistration.setOnAction(event -> {
            ((Stage) ((Node) actionEvent.getSource()).getScene().getWindow()).close();
        });
    }
}
