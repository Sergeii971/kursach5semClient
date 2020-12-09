package com.verbovskiy.client.controller;

import com.verbovskiy.client.connection.*;
import com.verbovskiy.client.entity.*;
import com.verbovskiy.client.information_window.InformationWindow;
import com.verbovskiy.client.util.ImageConverter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowOrdersController {
    private final Logger logger = LogManager.getLogger(ShowOrdersController.class);
    private final ObservableList<String> brands = FXCollections.observableArrayList("Audi",
            "BMW", "Bugatti", "Bentley", "Cadillac", "Nissan", "Ferrari", "Jaguar", "Maserati");
    private final ObservableList<String> colors = FXCollections.observableArrayList("black", "red", "white", "orange");
    private final ObservableList<String> boxes = FXCollections.observableArrayList("mechanics", "automation");
    private final ObservableList<String> carEngines = FXCollections.observableArrayList("diesel", "petrol");
    private final List<UserOrder> orders = new ArrayList<>();
    private final Map<String, Image> images = new HashMap<>();
    private int currentOrderIndex = 0;
    private static final String YES = "Yes";
    private static final String NO = "No";

    @FXML
    private Button buttonDealTookPlace;
    @FXML
    private ImageView carImage;
    @FXML
    private TextArea information;
    @FXML
    private Button buttonBack;
    @FXML
    private Button buttonNextPage;
    @FXML
    private Button buttonDelete;
    @FXML
    private TextField search;
    @FXML
    private ChoiceBox<String> carBrand;
    @FXML
    private ChoiceBox<String> carColor;
    @FXML
    private ChoiceBox<String> boxType;
    @FXML
    private ChoiceBox<String> carEngine;
    @FXML
    private Button buttonExit;
    @FXML
    private Button buttonFind;


    @FXML
    public void initialize() {
        carBrand.setItems(brands);
        carBrand.setValue("Audi");
        carEngine.setItems(carEngines);
        carEngine.setValue("diesel");
        carColor.setItems(colors);
        carColor.setValue("black");
        boxType.setItems(boxes);
        boxType.setValue("mechanics");
        information.setEditable(false);
        initData("SHOW_ORDERS");
    }

    public void jumpNextPage(ActionEvent actionEvent) {
        buttonNextPage.setOnAction(e -> {
            if (currentOrderIndex < orders.size() - 1) {
                currentOrderIndex++;
                UserOrder order = orders.get(currentOrderIndex);
                Image image = images.get(order.getCar().getImageName());
                setData(order, image);
            }
        });
    }

    public void delete(ActionEvent actionEvent) {
        buttonDelete.setOnAction(e -> {
            UserOrder order = orders.get(currentOrderIndex);
            long orderId = order.getOrderId();
            String imageName = order.getCar().getImageName();
            ServerConnection connection = ServerConnection.getInstance();
            UserRequest request = connection.getRequest();
            request.setAttribute(RequestParameter.COMMAND_NAME, "DELETE_ORDER");
            request.setAttribute(RequestParameter.ORDER_ID, orderId);
            orders.remove(order);
            images.remove(imageName);
            carImage.setImage(null);
            information.clear();
            if (currentOrderIndex < orders.size()) {
                UserOrder newOrder = orders.get(currentOrderIndex);
                Image image = images.get(newOrder.getCar().getImageName());
                setData(newOrder, image);
            } else {
                if (orders.size() > 0) {
                    currentOrderIndex = orders.size() - 1;
                    UserOrder newOrder = orders.get(currentOrderIndex);
                    Image image = images.get(newOrder.getCar().getImageName());
                    setData(newOrder, image);
                }
            }
        });
    }

    public void submitOrder(ActionEvent actionEvent) {
        buttonDealTookPlace.setOnAction(e -> {
            UserOrder order = orders.get(currentOrderIndex);
            long orderId = order.getOrderId();
            ServerConnection connection = ServerConnection.getInstance();
            UserRequest request = connection.getRequest();
            request.setAttribute(RequestParameter.COMMAND_NAME, "CHANGE_ORDER_STATUS");
            request.setAttribute(RequestParameter.ORDER_ID, orderId);
            connection.sendRequest();
            order.setInProcessing(!order.isInProcessing());
            Image image = images.get(order.getCar().getImageName());
            setData(order, image);
        });
    }

    public void back(ActionEvent actionEvent) {
        buttonBack.setOnAction(e -> {
            if (currentOrderIndex > 0) {
                currentOrderIndex--;
                UserOrder order = orders.get(currentOrderIndex);
                Image image = images.get(order.getCar().getImageName());
                setData(order, image);
            }
        });
    }

    public void find(ActionEvent actionEvent) {
        buttonFind.setOnAction(e -> {
            String searchParameter = search.getText();
            String brand = carBrand.getValue();
            String color = carColor.getValue();
            String boxType1 = boxType.getValue();
            String engineType = carEngine.getValue();
            ServerConnection connection = ServerConnection.getInstance();
            UserRequest request = connection.getRequest();
            request.setAttribute(RequestParameter.SEARCH_PARAMETER, searchParameter);
            request.setAttribute(RequestParameter.BRAND, brand);
            request.setAttribute(RequestParameter.COLOR, color);
            request.setAttribute(RequestParameter.BOX_TYPE, boxType1);
            request.setAttribute(RequestParameter.ENGINE_TYPE, engineType);
            initData("FIND_ORDERS");

        });
    }

    public void exit(ActionEvent actionEvent) {
        buttonExit.setOnAction(e -> {
            ((Stage) ((Node) actionEvent.getSource()).getScene().getWindow()).close();
        });
    }

    private void initData(String commandName) {
        carImage.setImage(null);
        information.clear();
        ServerConnection connection = ServerConnection.getInstance();
        UserRequest request = connection.getRequest();
        request.setAttribute(RequestParameter.COMMAND_NAME, commandName);
        connection.sendRequest();
        ServerResponse response = connection.getResponse();
        Boolean isIncorrectParameters = (Boolean) response.getAttribute(AttributeKey.INCORRECT_PARAMETER);
        if (isIncorrectParameters != null && isIncorrectParameters) {
            InformationWindow.showLineError();
        } else {
            int numberOfOrders = (int) response.getAttribute(RequestParameter.SIZE);
            for (int i = 0; i < numberOfOrders; i++) {
                long orderId = Long.parseLong((String) response.getAttribute(RequestParameter.ORDER_ID + i));
                LocalDate orderDate = (LocalDate) response.getAttribute(RequestParameter.ORDER_DATE + i);
                boolean inProcessing = (boolean) response.getAttribute(RequestParameter.IN_PROCESSING + i);
                long carId = Long.parseLong((String) response.getAttribute(RequestParameter.CAR_ID + i));
                CarBrand brand = CarBrand.valueOf((String) response.getAttribute(RequestParameter.BRAND + i));
                double price = Double.parseDouble((String) response.getAttribute(RequestParameter.PRICE + i));
                String description = (String) response.getAttribute(RequestParameter.DESCRIPTION + i);
                String model = (String) response.getAttribute(RequestParameter.MODEL + i);
                int manufactureYear = (int) response.getAttribute(RequestParameter.MANUFACTURE_YEAR + i);
                CarColor color = CarColor.valueOf((String) response.getAttribute(RequestParameter.COLOR + i));
                CarEngine engineType = CarEngine.valueOf((String) response.getAttribute(RequestParameter.ENGINE_TYPE + i));
                BoxType boxType = BoxType.valueOf((String) response.getAttribute(RequestParameter.BOX_TYPE + i));
                boolean isAvailable = (boolean) response.getAttribute(RequestParameter.IS_AVAILABLE + i);
                LocalDate addedDate = (LocalDate) response.getAttribute(RequestParameter.ADDED_DATE + i);
                String imageName = (String) response.getAttribute(RequestParameter.IMAGE_NAME + i);
                byte[] bytes = (byte[]) response.getAttribute(RequestParameter.IMAGE + i);
                Image image = ImageConverter.convertToImage(bytes);
                Car car = new Car(carId, brand, model, manufactureYear, price, description, imageName, addedDate, isAvailable,
                        color, boxType, engineType);
                String login = (String) response.getAttribute(RequestParameter.LOGIN + i);
                String name = (String) response.getAttribute(RequestParameter.NAME + i);
                String surname = (String) response.getAttribute(RequestParameter.SURNAME + i);
                boolean isBlocked = (boolean) response.getAttribute(RequestParameter.IS_BLOCKED + i);
                String isBlockedStringFormat = isBlocked ? YES : NO;
                User user = new User(login, name, surname, isBlockedStringFormat);
                UserOrder order = new UserOrder(orderId, orderDate, user, car, inProcessing);
                orders.add(order);
                images.put(imageName, image);
                if (i == 0) {
                    setData(order, image);
                }
            }
        }
    }

    private void setData(UserOrder order, Image image) {
        carImage.setImage(image);
        if (!order.isInProcessing()) {
            buttonDelete.setVisible(false);
            buttonDelete.setDisable(true);
            buttonDealTookPlace.setVisible(false);
            buttonDealTookPlace.setDisable(true);
        } else {
            buttonDelete.setVisible(true);
            buttonDelete.setDisable(false);
            buttonDealTookPlace.setVisible(true);
            buttonDealTookPlace.setDisable(false);
        }
        StringBuilder builder = new StringBuilder();
        builder.append("Эмейл покупателя : ").append(order.getUser().getLogin()).append("\n")
                .append("Марка : ").append(order.getCar().getBrand().getBrand()).append("\n")
                .append("Цена : ").append(order.getCar().getPrice()).append("\n")
                .append("Модель : ").append(order.getCar().getModel()).append("\n")
                .append("Год выпуска : ").append(order.getCar().getManufactureYear()).append("\n")
                .append("Цвет : ").append(order.getCar().getColor().getColor()).append("\n")
                .append("Тип двигателя : ").append(order.getCar().getEngineType().getEngine()).append("\n")
                .append("Тип коробки : ").append(order.getCar().getBoxType().getBox()).append("\n")
                .append("Дата добавления : ").append(order.getCar().getAddedDate()).append("\n")
                .append("Дата заказа : ").append(order.getDate()).append("\n");
        information.setText(builder.toString());
    }
}
