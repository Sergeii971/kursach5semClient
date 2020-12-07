package com.verbovskiy.client.controller;

import com.verbovskiy.client.connection.*;
import com.verbovskiy.client.information_window.InformationWindow;
import com.verbovskiy.client.util.ByteConverter;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.util.Map;

public class AddCarController {
    private final Logger logger = LogManager.getLogger(AddCarController.class);
    private final ObservableList<String> brands = FXCollections.observableArrayList("Audi",
            "BMW", "Bugatti", "Bentley", "Cadillac", "Nissan", "Ferrari", "Jaguar", "Maserati");
    private final ObservableList<String> colors = FXCollections.observableArrayList("black", "red", "white", "orange");
    private final ObservableList<String> boxes = FXCollections.observableArrayList("mechanics", "automation");
    private final ObservableList<String> carEngines = FXCollections.observableArrayList("diesel", "petrol");
    @FXML
    private Button buttonSelectImage;
    @FXML
    private ChoiceBox<String> carBrand;
    @FXML
    private ChoiceBox<String> carColor;
    @FXML
    private ChoiceBox<String> boxType;
    @FXML
    private ChoiceBox<String> carEngine;
    @FXML
    private TextField yearOfManufacture;
    @FXML
    private TextField price;
    @FXML
    private TextField carModel;
    @FXML
    private TextArea description;
    @FXML
    private Button buttonSaveCar;
    @FXML
    private Button buttonExit;
    @FXML
    private ImageView imageView;
    private Image image;
    private File file;

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
    }

    public void selectCar(ActionEvent actionEvent) {
        buttonSelectImage.setOnAction(e -> {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Document");
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("Images", "*.jpg");
        fileChooser.getExtensionFilters().add(extFilter);
        file = fileChooser.showOpenDialog(((Node) actionEvent.getSource()).getScene().getWindow());
        if (file != null) {
            try {
                image = new Image(file.toURI().toURL().toString());
            } catch (MalformedURLException exception) {
               logger.log(Level.ERROR, "error while opening image");
            }
            imageView.setImage(image);
        }
        });
    }

    public void saveCar(ActionEvent actionEvent) {
        buttonSaveCar.setOnAction(e -> {
            String brand = carBrand.getValue();
            String price1 = price.getText();
            String description1 = description.getText();
            String model = carModel.getText();
            String manufactureYear = yearOfManufacture.getText();
            String color = carColor.getValue();
            String engineType = carEngine.getValue();
            String boxType1 = boxType.getValue();
            boolean isAvailable = true;
            LocalDate addedDate = LocalDate.now();
            if (price1.isEmpty() || model.isEmpty() || manufactureYear.isEmpty() || file == null) {
                InformationWindow.showLineIsEmpty();
            } else {
                ServerConnection connection = ServerConnection.getInstance();
                UserRequest request = connection.getRequest();
                request.setAttribute(RequestParameter.COMMAND_NAME, "ADD_CAR");
                request.setAttribute(RequestParameter.BRAND, brand);
                request.setAttribute(RequestParameter.PRICE, price1);
                request.setAttribute(RequestParameter.DESCRIPTION, description1);
                request.setAttribute(RequestParameter.IMAGE, ByteConverter.convertToBytes(file));
                request.setAttribute(RequestParameter.MODEL, model);
                request.setAttribute(RequestParameter.MANUFACTURE_YEAR, manufactureYear);
                request.setAttribute(RequestParameter.COLOR, color);
                request.setAttribute(RequestParameter.ENGINE_TYPE, engineType);
                request.setAttribute(RequestParameter.BOX_TYPE, boxType1);
                request.setAttribute(RequestParameter.IS_AVAILABLE, isAvailable);
                request.setAttribute(RequestParameter.ADDED_DATE, addedDate);
                connection.sendRequest();
                ServerResponse response = connection.getResponse();
                Map<String, Boolean> incorrectParameters = (Map<String, Boolean>) response.getAttribute(AttributeKey
                        .INCORRECT_PARAMETER);
                if (incorrectParameters.isEmpty()) {
                    ((Stage) ((Node) actionEvent.getSource()).getScene().getWindow()).close();
                    InformationWindow.showSuccessfulSaving();
                } else {
                    InformationWindow.showAddCarError(incorrectParameters);
                }
            }
        });

    }

    public void exit(ActionEvent actionEvent) {
    }
}
