package com.mycompany.hypermarket;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;

public class AdminController {

    private Admin admin;
    @FXML
    private Button listEmployees;
    @FXML
    private TextField id;
    @FXML
    private MenuButton type;
    @FXML
    private TextField newUsername;
    @FXML
    private TextField newEmployeeUsername;
    @FXML
    private MenuButton newEmployeeType;
    @FXML
    private TextField newEmployeeEmail;
    @FXML
    private TextField newEmployeePassword;
    @FXML
    private TextField newEmployeeAddress;
    @FXML
    private TextField newEmployeeNumber;

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    @FXML
    public void initialize() {
        for (MenuItem item : type.getItems()) {
            item.setOnAction(event -> type.setText(item.getText()));
        }

        for (MenuItem item : newEmployeeType.getItems()) {
            item.setOnAction(event -> newEmployeeType.setText(item.getText()));
        }
    }

    @FXML
    private void listAllEmployees() throws IOException {
        String[] employees = admin.listAllEmployees();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("List of Employees");
        alert.setHeaderText(null);
        alert.setContentText(String.join("\n", employees));
        alert.showAndWait();
    }

    @FXML
    private void updateEmployeeType() {
        try {
            if (id.getText().isEmpty()) {
                throw new Exception("ID is required");
            }

            if (type.getText().equals("Role")) {
                throw new Exception("Role is required");
            }

            String employeeId = id.getText();
            String role = type.getText();

            admin.setEmployeeType(employeeId, role);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void updateEmployeeUsername() {
        try {
            if (id.getText().isEmpty()) {
                throw new Exception("ID is required");
            }

            if (newUsername.getText().isEmpty()) {
                throw new Exception("New username is required");
            }

            String employeeId = id.getText();
            int intID = Integer.parseInt(employeeId.substring(employeeId.indexOf("_") + 1));
            String employeeType = null;
            String username = newUsername.getText();

            if (employeeId.startsWith("Seller_")) {
                employeeType = "Seller";
            } else if (employeeId.startsWith("MarketingEmployee_")) {
                employeeType = "MarketingEmployee";
            } else if (employeeId.startsWith("InventoryEmployee_")) {
                employeeType = "InventoryEmployee";
            } else {
                throw new Exception("Invalid employee ID");
            }

            admin.updateEmployeeUsername(employeeType, intID, username);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void addEmployee() {
        try {
            if (newEmployeeEmail.getText().isEmpty()) {
                throw new Exception("Email is required");
            }

            if (newEmployeePassword.getText().isEmpty()) {
                throw new Exception("Password is required");
            }

            if (newEmployeeType.getText().equals("Role")) {
                throw new Exception("Role is required");
            }

            if (newEmployeeAddress.getText().isEmpty()) {
                throw new Exception("Address is required");
            }

            if (newEmployeeNumber.getText().isEmpty()) {
                throw new Exception("Number is required");
            }

            String username = newEmployeeUsername.getText();
            String email = newEmployeeEmail.getText();
            String password = newEmployeePassword.getText();
            String role = newEmployeeType.getText();
            String address = newEmployeeAddress.getText();
            int number = Integer.parseInt(newEmployeeNumber.getText());

            switch (role) {
                case "Seller":
                    admin.addSellerEmployee(username, email, password, address, number);
                    break;
                case "MarketingEmployee":
                    admin.addMarketingEmployee(username, email, password, address, number);
                    break;
                case "InventoryEmployee":
                    admin.addInventoryEmployee(username, email, password, address, number);
                    break;
                default:
                    throw new Exception("Invalid role");
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Employee added successfully.");
            alert.showAndWait();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void searchEmployee() {
        try {
            if (id.getText().isEmpty()) {
                throw new Exception("ID is required");
            }

            String employeeId = id.getText();
            int intID = Integer.parseInt(employeeId.substring(employeeId.indexOf("_") + 1));
            String details = null;

            if (employeeId.startsWith("Seller_")) {
                details = admin.searchSellerEmployee(intID);
            } else if (employeeId.startsWith("MarketingEmployee_")) {
                details = admin.searchMarketingEmployee(intID);
            } else if (employeeId.startsWith("InventoryEmployee_")) {
                details = admin.searchInventoryEmployee(intID);
            } else {
                throw new Exception("Invalid employee ID");
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Employee Details");
            alert.setHeaderText(null);
            alert.setContentText(String.join("\n", details));
            alert.showAndWait();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void deleteEmployee() {
        try {
            if (id.getText().isEmpty()) {
                throw new Exception("ID is required");
            }

            String employeeId = id.getText();
            int intID = Integer.parseInt(employeeId.substring(employeeId.indexOf("_") + 1));

            if (employeeId.startsWith("Seller_")) {
                admin.deleteSellerEmployee(intID);
            } else if (employeeId.startsWith("MarketingEmployee_")) {
                admin.deleteMarketingEmployee(intID);
            } else if (employeeId.startsWith("InventoryEmployee_")) {
                admin.deleteInventoryEmployee(intID);
            } else {
                throw new Exception("Invalid employee ID");
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Employee deleted successfully.");
            alert.showAndWait();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}