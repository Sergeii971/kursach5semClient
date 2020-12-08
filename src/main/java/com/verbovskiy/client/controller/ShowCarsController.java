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

public class ShowCarsController {
    private final Logger logger = LogManager.getLogger(ShowCarsController.class);
    private final ObservableList<String> brands = FXCollections.observableArrayList("Audi",
            "BMW", "Bugatti", "Bentley", "Cadillac", "Nissan", "Ferrari", "Jaguar", "Maserati");
    private final ObservableList<String> colors = FXCollections.observableArrayList("black", "red", "white", "orange");
    private final ObservableList<String> boxes = FXCollections.observableArrayList("mechanics", "automation");
    private final ObservableList<String> carEngines = FXCollections.observableArrayList("diesel", "petrol");
    private final List<Car> cars = new ArrayList<>();
    private final Map<String, Image> images = new HashMap<>();
    private int currentCarIndex = 0;
    private static final String BLOCK = "Заблокировать";
    private static final String UNBLOCK = "Разблокировать";

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
    private TextField from;
    @FXML
    private TextField to;
    @FXML
    private ChoiceBox<String> carBrand;
    @FXML
    private ChoiceBox<String> carColor;
    @FXML
    private ChoiceBox<String> boxType;
    @FXML
    private ChoiceBox<String> carEngine;
    @FXML
    private Button buttonChangeIsAvailableStatus;
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
        initData("SHOW_CARS");
    }

    public void jumpNextPage(ActionEvent actionEvent) {
        buttonNextPage.setOnAction(e -> {
            if (currentCarIndex < cars.size() - 1) {
                currentCarIndex++;
                Car car = cars.get(currentCarIndex);
                Image image = images.get(car.getImageName());
                setData(car, image);
            }
        });
    }

    public void delete(ActionEvent actionEvent) {
        buttonDelete.setOnAction(e -> {
            Car car = cars.get(currentCarIndex);
            long carId = car.getCarId();
            String imageName = car.getImageName();
            ServerConnection connection = ServerConnection.getInstance();
            UserRequest request = connection.getRequest();
            request.setAttribute(RequestParameter.COMMAND_NAME, "DELETE_CAR");
            request.setAttribute(RequestParameter.CAR_ID, car.getCarId());
            connection.sendRequest();
            ServerResponse response = connection.getResponse();
            boolean isInOrderList = (boolean) response.getAttribute(RequestParameter.IN_ORDER_LIST);
            if (!isInOrderList) {
                cars.remove(car);
                images.remove(imageName);
                carImage.setImage(null);
                information.clear();
                if (currentCarIndex < cars.size()) {
                    Car newCar = cars.get(currentCarIndex);
                    Image image = images.get(newCar.getImageName());
                    setData(car, image);
                } else {
                    if (cars.size() > 0) {
                        currentCarIndex = cars.size() - 1;
                        Car newCar = cars.get(currentCarIndex);
                        Image image = images.get(newCar.getImageName());
                        setData(car, image);
                    }
                }
            } else {
                InformationWindow.showCarInOrderList();
            }
        });
    }

    public void back(ActionEvent actionEvent) {
        buttonBack.setOnAction(e -> {
            if (currentCarIndex > 0) {
                currentCarIndex--;
                Car car = cars.get(currentCarIndex);
                Image image = images.get(car.getImageName());
                setData(car, image);
            }
        });
    }

    public void changeIsAvailableStatus(ActionEvent actionEvent) {
        buttonChangeIsAvailableStatus.setOnAction(e -> {
            Car car = cars.get(currentCarIndex);
            ServerConnection connection = ServerConnection.getInstance();
            UserRequest request = connection.getRequest();
            request.setAttribute(RequestParameter.COMMAND_NAME, "CHANGE_CAR_AVAILABLE_STATUS");
            request.setAttribute(RequestParameter.CAR_ID, car.getCarId());
            request.setAttribute(RequestParameter.IS_AVAILABLE, !car.getIsAvailable());
            connection.sendRequest();
            car.setAvailable(!car.getIsAvailable());
            String isAvailable = car.getIsAvailable() ? BLOCK : UNBLOCK;
            buttonChangeIsAvailableStatus.setText(isAvailable);
        });
    }

    public void find(ActionEvent actionEvent) {
        buttonFind.setOnAction(e -> {
            String searchParameter = search.getText();
            String fromPrice = from.getText();
            String toPrice = to.getText();
            String brand = carBrand.getValue();
            String color = carColor.getValue();
            String boxType1 = boxType.getValue();
            String engineType = carEngine.getValue();
            ServerConnection connection = ServerConnection.getInstance();
            UserRequest request = connection.getRequest();
            request.setAttribute(RequestParameter.SEARCH_PARAMETER, searchParameter);
            request.setAttribute(RequestParameter.FROM_PRICE, fromPrice);
            request.setAttribute(RequestParameter.TO_PRICE, toPrice);
            request.setAttribute(RequestParameter.BRAND, brand);
            request.setAttribute(RequestParameter.COLOR, color);
            request.setAttribute(RequestParameter.BOX_TYPE, boxType1);
            request.setAttribute(RequestParameter.ENGINE_TYPE, engineType);
            initData("FIND_CARS");

        });
    }

    public void exit(ActionEvent actionEvent) {
        buttonExit.setOnAction(e -> {
            ((Stage) ((Node) actionEvent.getSource()).getScene().getWindow()).close();
        });
    }

    private void initData(String commandName) {
        ServerConnection connection = ServerConnection.getInstance();
        UserRequest request = connection.getRequest();
        request.setAttribute(RequestParameter.COMMAND_NAME, commandName);
        connection.sendRequest();
        ServerResponse response = connection.getResponse();
        Boolean isIncorrectParameters = (Boolean) response.getAttribute(AttributeKey.INCORRECT_PARAMETER);
        if (isIncorrectParameters != null && isIncorrectParameters) {
            InformationWindow.showLineError();
        } else {
            int numberOfCars = (int) response.getAttribute(RequestParameter.SIZE);
            for (int i = 0; i < numberOfCars; i++) {
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
                cars.add(car);
                images.put(imageName, image);
                if (i == 0) {
                    setData(car, image);
                }
            }
        }
    }

    private void setData(Car car, Image image) {
        carImage.setImage(image);
        StringBuilder builder = new StringBuilder();
        builder.append("Марка : ").append(car.getBrand().getBrand()).append("\n")
                .append("Цена : ").append(car.getPrice()).append("\n")
                .append("Модель : ").append(car.getModel()).append("\n")
                .append("Год выпуска : ").append(car.getManufactureYear()).append("\n")
                .append("Цвет : ").append(car.getColor().getColor()).append("\n")
                .append("Тип двигателя : ").append(car.getEngineType().getEngine()).append("\n")
                .append("Тип коробки : ").append(car.getBoxType().getBox()).append("\n")
                .append("Дата добавления : ").append(car.getAddedDate()).append("\n");
        String isAvailable = car.getIsAvailable() ? BLOCK : UNBLOCK;
        buttonChangeIsAvailableStatus.setText(isAvailable);
        information.setText(builder.toString());
    }
}
