package com.verbovskiy.client.controller;

import com.verbovskiy.client.connection.RequestParameter;
import com.verbovskiy.client.connection.ServerConnection;
import com.verbovskiy.client.connection.ServerResponse;
import com.verbovskiy.client.connection.UserRequest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class ForInvestorsController implements Initializable {
    @FXML
    private TextArea information;
    @FXML
    private Button buttonExit;
    @FXML
    private LineChart<Number, Number> chart1;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        XYChart.Series<Number, Number> grimotor = new XYChart.Series<>();
        grimotor.setName("Grimotor");
        grimotor.getData().add(new XYChart.Data<>(1, 20));
        grimotor.getData().add(new XYChart.Data<>(2, 100));
        grimotor.getData().add(new XYChart.Data<>(3, 80));
        grimotor.getData().add(new XYChart.Data<>(4, 180));
        grimotor.getData().add(new XYChart.Data<>(5, 20));
        grimotor.getData().add(new XYChart.Data<>(6, -10));
        chart1.getData().add(grimotor);
        XYChart.Series<Number, Number> siemotor = new XYChart.Series<>();
        siemotor.setName("Siemotor");
        siemotor.getData().add(new XYChart.Data<>(1, 5));
        siemotor.getData().add(new XYChart.Data<>(2, 60));
        siemotor.getData().add(new XYChart.Data<>(3, 50));
        siemotor.getData().add(new XYChart.Data<>(4, 80));
        siemotor.getData().add(new XYChart.Data<>(5, 40));
        siemotor.getData().add(new XYChart.Data<>(6, 50));
        chart1.getData().add(siemotor);
        XYChart.Series<Number, Number> carInc = new XYChart.Series<>();
        carInc.setName("Car Inc");
        carInc.getData().add(new XYChart.Data<>(1, 35));
        carInc.getData().add(new XYChart.Data<>(2, 40));
        carInc.getData().add(new XYChart.Data<>(3, 50));
        carInc.getData().add(new XYChart.Data<>(4, 70));
        carInc.getData().add(new XYChart.Data<>(5, 60));
        carInc.getData().add(new XYChart.Data<>(6, 70));
        chart1.getData().add(carInc);
        generateInformationMessage();
    }

    public void exit(ActionEvent actionEvent) {
        buttonExit.setOnAction(event -> {
            ((Stage) ((Node) actionEvent.getSource()).getScene().getWindow()).close();
        });
    }

    private void generateInformationMessage() {
        ServerConnection connection = ServerConnection.getInstance();
        UserRequest request = connection.getRequest();
        request.setAttribute(RequestParameter.COMMAND_NAME, "WEEK_PROFIT");
        connection.sendRequest();
        ServerResponse response = connection.getResponse();
        Map<String, Double> profits = (Map<String, Double>) response.getAttribute(RequestParameter.PROFITS);
        XYChart.Series<Number, Number> ourCompany = new XYChart.Series<>();
        ourCompany.setName("Наша компания");
         int xValue = 6;
        for (int i = 0; i < profits.size() - 1; i++) {
            ourCompany.getData().add(new XYChart.Data<>(xValue, profits.get(RequestParameter.DAY_PROFIT + i) / 100));
            xValue--;
        }
        chart1.getData().add(ourCompany);
        double weekProfit = profits.get(RequestParameter.COMMON_COMPANY_PROFIT) / 100;
        double firstCompanyWeekProfit = 390;
        double secondCompanyWeekProfit = 285;
        double thirdCompanyWeekProfit = 325;
        StringBuilder builder = new StringBuilder();

        if (weekProfit > firstCompanyWeekProfit) {
            builder.append("недельная прибыль больше чем у Grimotor на ")
                    .append(String.format("%.1f", (weekProfit - firstCompanyWeekProfit) * 100));
        } else {
            builder.append("недельная прибыль меньше чем у Grimotor на ")
                    .append(String.format("%.1f", (firstCompanyWeekProfit - weekProfit) * 100));
        }
        builder.append("$\n");
        if (weekProfit > secondCompanyWeekProfit) {
            builder.append("недельная прибыль больше чем у Siemotor на ")
                    .append(String.format("%.1f", (weekProfit - secondCompanyWeekProfit) * 100));
        } else {
            builder.append("недельная прибыль меньше чем у Siemotor на ")
                    .append(String.format("%.1f", (secondCompanyWeekProfit - weekProfit) * 100));
        }
        builder.append("$\n");
        if (weekProfit > thirdCompanyWeekProfit) {
            builder.append("недельная прибыль больше чем у Car Inc на ")
                    .append(String.format("%.1f", (weekProfit - thirdCompanyWeekProfit) * 100));
        } else {
            builder.append("недельная прибыль меньше чем у Car Inc на ")
                    .append(String.format("%.1f", (thirdCompanyWeekProfit - weekProfit) * 100));
        }
        builder.append("$\n");
        information.setText(builder.toString());
    }
}
