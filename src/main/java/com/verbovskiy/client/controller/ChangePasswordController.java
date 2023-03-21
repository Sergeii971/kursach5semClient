package com.verbovskiy.client.controller;

import com.verbovskiy.client.connection.*;
import com.verbovskiy.client.information_window.InformationWindow;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

public class ChangePasswordController {
    @FXML
    private PasswordField oldPassword;
    @FXML
    private PasswordField newPassword;
    @FXML
    private PasswordField passwordConfirmation;
    @FXML
    private Button buttonOk;
    @FXML
    private Button buttonExit;

    public void changePassword(ActionEvent actionEvent) {
        buttonOk.setOnAction(event -> {
            if (oldPassword.getLength() == 0 || newPassword.getLength() == 0 || passwordConfirmation.getLength() == 0) {
                InformationWindow.showLineIsEmpty();
            } else {
                ServerConnection connection = ServerConnection.getInstance();
                ThreadLocal<UserRequest> request = connection.getRequest();
                request.get().setAttribute(RequestParameter.COMMAND_NAME, "CHANGE_PASSWORD");
                String oldPasswordStringFormat = oldPassword.getText();
                String newPasswordStringFormat = newPassword.getText();
                String passwordConfirmationStringFormat = passwordConfirmation.getText();
                request.get().setAttribute(RequestParameter.PASSWORD, oldPasswordStringFormat);
                request.get().setAttribute(RequestParameter.NEW_PASSWORD, newPasswordStringFormat);
                request.get().setAttribute(RequestParameter.PASSWORD_CONFIRMATION, passwordConfirmationStringFormat);
                connection.sendRequest();
                ThreadLocal<ServerResponse> response = connection.getResponse();
                boolean isPasswordChange = (boolean) response.get().getAttribute(AttributeKey.SUCCESSFUL_PASSWORD_CHANGE);
                if (isPasswordChange) {
                    ((Stage) ((Node) actionEvent.getSource()).getScene().getWindow()).close();
                    InformationWindow.changePasswordSuccessful();
                } else {
                    oldPassword.clear();
                    newPassword.clear();
                    passwordConfirmation.clear();
                    InformationWindow.changePasswordError();
                }
            }});
    }

    public void exitChangeAdminData(ActionEvent actionEvent) {
        buttonExit.setOnAction(event -> {
            ((Stage) ((Node) actionEvent.getSource()).getScene().getWindow()).close();
        });
    }
}
