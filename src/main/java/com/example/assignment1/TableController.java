package com.example.assignment1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    private Label welcomeText;
    @FXML
    private BarChart<String, Integer> barChart;

    @FXML
    private TableView<CarSale> tableView;


    private static final String DB_URL = "jdbc:mysql://localhost:3306/ASSIGNMENT2";
    private static final String DB_USERNAME = "ASSIGNMENT2";
    private static final String DB_PASSWORD = "Shoaibimran04";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            ObservableList<CarSale> data = FXCollections.observableArrayList();

            retrieveDataFromDatabase(connection, "2003", data);

            TableColumn<CarSale, String> brandColumn = new TableColumn<>("Brand");
            brandColumn.setCellValueFactory(new PropertyValueFactory<>("brand"));

            TableColumn<CarSale, Integer> salesColumn = new TableColumn<>("Model");
            salesColumn.setCellValueFactory(new PropertyValueFactory<>("model"));

            tableView.getColumns().addAll(brandColumn, salesColumn);
            tableView.setItems(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void retrieveDataFromDatabase(Connection connection, String year, ObservableList<CarSale> data) throws SQLException {
        String query = "SELECT brand, sales FROM car_sales WHERE year = '" + year + "'";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                String brand = resultSet.getString("brand");
                int sales = resultSet.getInt("model");
                data.add(new CarSale(brand, sales));
            }
        }
    }


    @FXML
    void Change(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        Main main = new Main();

        try {
            main.TableView(stage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
