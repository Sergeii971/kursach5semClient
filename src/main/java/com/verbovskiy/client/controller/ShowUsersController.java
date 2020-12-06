package com.verbovskiy.client.controller;

import com.verbovskiy.client.connection.*;
import com.verbovskiy.client.entity.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ShowUsersController {
    private final ObservableList<User> usersData = FXCollections.observableArrayList();
    private final ObservableList<String> filterTypeList = FXCollections.observableArrayList(DEFAULT, BLOCKED_USERS);
    private final ObservableList<String> sortTypeList = FXCollections.observableArrayList(DEFAULT,SORT_BY_SURNAME,
            SORT_BY_EMAIL);
    private static final String YES = "Yes";
    private static final String NO = "No";
    private static final String DEFAULT = "Default";
    private static final String BLOCKED_USERS = "Blocked users";
    private static final String SORT_BY_SURNAME = "Sort by surname";
    private static final String SORT_BY_EMAIL = "Sort by email";

    @FXML
    private Button buttonSearch;
    @FXML
    private Button filterOut;
    @FXML
    private TextField search;
    @FXML
    private ChoiceBox<String> sortType;
    @FXML
    private Button sort;
    @FXML
    private ChoiceBox<String> filterType;
    @FXML
    private TableView<User> tableUsers;
    @FXML
    private TableColumn<User, String> columnName;
    @FXML
    private TableColumn<User, String> columnSurname;
    @FXML
    private TableColumn<User, String> columnLogin;
    @FXML
    private TableColumn<User, String> columnIsBlocked;
    @FXML
    private Button changeUserStatus;
    @FXML
    private Button buttonExitShowUsers;

    @FXML
    public void initialize() {
        initData("SHOW_USERS");
        columnSurname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnLogin.setCellValueFactory(new PropertyValueFactory<>("login"));
        columnIsBlocked.setCellValueFactory(new PropertyValueFactory<>("isBlocked"));
        tableUsers.setItems(usersData);
        filterType.setItems(filterTypeList);
        filterType.setValue(DEFAULT);
        sortType.setItems(sortTypeList);
        sortType.setValue(DEFAULT);
    }

    public void filterOut(ActionEvent actionEvent) {
        filterOut.setOnAction(e -> {
            ServerConnection connection = ServerConnection.getInstance();
            UserRequest request = connection.getRequest();
            String selectedFilterType = filterType.getValue().equals(BLOCKED_USERS) ? RequestParameter.IS_BLOCKED : DEFAULT;
            request.setAttribute(RequestParameter.USER_STATUS, selectedFilterType);
            tableUsers.refresh();
            initData("FILTER_USERS");
        });
    }

    public void sort(ActionEvent actionEvent) {
        sort.setOnAction(e -> {
            ServerConnection connection = ServerConnection.getInstance();
            UserRequest request = connection.getRequest();
            String selectedSortType = sortType.getValue().equals(SORT_BY_SURNAME) ? RequestParameter.SURNAME :
                    sortType.getValue().equals(SORT_BY_EMAIL) ? RequestParameter.EMAIL : DEFAULT;
            request.setAttribute(RequestParameter.SORT_TYPE, selectedSortType);
            tableUsers.refresh();
            initData("SORT_USERS");
        });
    }

    public void search(ActionEvent actionEvent) {
        buttonSearch.setOnAction(e -> {
            ServerConnection connection = ServerConnection.getInstance();
            UserRequest request = connection.getRequest();
            request.setAttribute(RequestParameter.SEARCH_PARAMETER, search.getText());
            tableUsers.refresh();
            initData("SEARCH_USERS");
        });
    }

    public void blockUser(ActionEvent actionEvent) {
        changeUserStatus.setOnAction(e -> {
            ObservableList<User> userSelected, allUser;
            allUser = tableUsers.getItems();
            userSelected = tableUsers.getSelectionModel().getSelectedItems();
            if (allUser.size() != 0 && userSelected != null) {
                ServerConnection connection = ServerConnection.getInstance();
                UserRequest request = connection.getRequest();
                request.setAttribute(RequestParameter.COMMAND_NAME, "CHANGE_USER_BLOCK_STATUS");
                boolean isBlocked = !userSelected.get(0).getIsBlocked().equals(YES);
                request.setAttribute(RequestParameter.USER_STATUS, isBlocked);
                request.setAttribute(RequestParameter.USER_ID, userSelected.get(0).getLogin());
                connection.sendRequest();
                for (int i = 0; i < allUser.size(); i++) {
                    if (allUser.get(i).getLogin().equals(userSelected.get(0).getLogin())) {
                        usersData.get(i).setIsBlocked(userSelected.get(0).getIsBlocked().equals(YES) ? NO : YES);
                        tableUsers.refresh();
                        tableUsers.setItems(usersData);
                        break;
                    }
                }
            }
        });
    }

    public void exitShowUsers(ActionEvent actionEvent) {
        buttonExitShowUsers.setOnAction(event -> {
            ((Stage) ((Node) actionEvent.getSource()).getScene().getWindow()).close();
        });
    }

    private void initData(String commandName) {
        usersData.clear();
        ServerConnection connection = ServerConnection.getInstance();
        UserRequest request = connection.getRequest();
        request.setAttribute(RequestParameter.COMMAND_NAME, commandName);
        connection.sendRequest();
        ServerResponse response = connection.getResponse();
        int numberOfUsers = (int) response.getAttribute(RequestParameter.SIZE);
        for (int i = 0; i < numberOfUsers; i++) {
            String login = (String) response.getAttribute(RequestParameter.LOGIN + i);
            String name = (String) response.getAttribute(RequestParameter.NAME + i);
            String surname = (String) response.getAttribute(RequestParameter.SURNAME + i);
            boolean isBlocked = (boolean) response.getAttribute(RequestParameter.IS_BLOCKED + i);
            String isBlockedStringFormat = isBlocked ? YES : NO;
            usersData.add(new User(login, name, surname, isBlockedStringFormat));
        }
        tableUsers.setItems(usersData);
    }
}
