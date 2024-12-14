package com.mycompany.hypermarket;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;

public class InventoryController extends BaseController {

    private InventoryEmployee inventoryEmployee;
    @FXML
    private TextField newInventoryEmployeeUsername;
    @FXML
    private TextField newInventoryEmployeeEmail;
    @FXML
    private TextField newInventoryEmployeePassword;
    @FXML
    private TextField newInventoryEmployeeAddress;
    @FXML
    private TextField newInventoryEmployeeNumber;
    @FXML
    private TextField name;
    @FXML
    private TextField quantity;
    @FXML
    private TextField price;
    @FXML
    private TextField expiryDate;
    @FXML
    private TextField productID;
    @FXML
    private TextField newQuantity;
    @FXML
    private TextField newPrice;

    public void setInventoryEmployee(InventoryEmployee inventoryEmployee) {
        this.inventoryEmployee = inventoryEmployee;
    }

    @FXML
    private void addProduct() {
        try {
            if (name.getText().isEmpty()) {
                throw new Exception("Name is required");
            }

            if (quantity.getText().isEmpty()) {
                throw new Exception("Quantity is required");
            }

            if (price.getText().isEmpty()) {
                throw new Exception("Price is required");
            }

            if (expiryDate.getText().isEmpty()) {
                throw new Exception("Expiry date is required");
            }

            inventoryEmployee.addProduct(name.getText(), Integer.parseInt(quantity.getText()),
                    Double.parseDouble(price.getText()),
                    new SimpleDateFormat("dd-MM-yyyy").parse(expiryDate.getText()));

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Product added successfully");
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
    private void listAllProducts() {
        String[] products = inventoryEmployee.listProducts();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("List of Products");
        alert.setHeaderText(null);
        alert.setContentText(String.join("\n", products));
        alert.showAndWait();
    }

    @FXML
    private void deleteProduct() {
        try {
            if (productID.getText().isEmpty()) {
                throw new Exception("Product ID is required");
            }

            inventoryEmployee.deleteProduct(Integer.parseInt(productID.getText()));

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Product deleted successfully");
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
    private void updateProductQuantity() {
        try {
            if (productID.getText().isEmpty()) {
                throw new Exception("Product ID is required");
            }

            if (newQuantity.getText().isEmpty()) {
                throw new Exception("New quantity is required");
            }

            InventoryEmployee.updateProductQuantity(Integer.parseInt(productID.getText()),
                    Integer.parseInt(newQuantity.getText()));

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Product quantity updated successfully");
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
    private void updateProductPrice() {
        try {
            if (productID.getText().isEmpty()) {
                throw new Exception("Product ID is required");
            }

            if (newPrice.getText().isEmpty()) {
                throw new Exception("New price is required");
            }

            inventoryEmployee.updateProductPrice(Integer.parseInt(productID.getText()),
                    Double.parseDouble(newPrice.getText()));

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Product price updated successfully");
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
    private void searchProduct() {
        try {
            if (productID.getText().isEmpty()) {
                throw new Exception("Product ID is required");
            }

            String product = inventoryEmployee.searchProduct(Integer.parseInt(productID.getText()));

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Product Details");
            alert.setHeaderText(null);
            alert.setContentText(product);
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
    private void quantityNotification() {
        try {
            if (productID.getText().isEmpty()) {
                throw new Exception("Product ID is required");
            }

            String result = inventoryEmployee.quantityOfProductNotification(Integer.parseInt(productID.getText()));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Quantity Notification");
            alert.setHeaderText(null);
            alert.setContentText(result);
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
    private void expiryDateNotification() {
        try {
            if (productID.getText().isEmpty()) {
                throw new Exception("Product ID is required");
            }

            String result = inventoryEmployee.expiryDateOfProductNotification(Integer.parseInt(productID.getText()));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Expiry Date Notification");
            alert.setHeaderText(null);
            alert.setContentText(result);
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
    private void updateInventoryEmployee() {
        try {
            String username = newInventoryEmployeeUsername.getText().isEmpty() ? inventoryEmployee.getUsername()
                    : newInventoryEmployeeUsername.getText();
            String email = newInventoryEmployeeEmail.getText().isEmpty() ? inventoryEmployee.getEmail()
                    : newInventoryEmployeeEmail.getText();
            String password = newInventoryEmployeePassword.getText().isEmpty() ? inventoryEmployee.getPassword()
                    : newInventoryEmployeePassword.getText();
            String address = newInventoryEmployeeAddress.getText().isEmpty() ? inventoryEmployee.getAddress()
                    : newInventoryEmployeeAddress.getText();
            int number = newInventoryEmployeeNumber.getText().isEmpty() ? inventoryEmployee.getNumber()
                    : Integer.parseInt(newInventoryEmployeeNumber.getText());

            inventoryEmployee.updateInventoryEmployee(username, email, password, address, number);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Inventory updated successfully.");
            alert.showAndWait();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @Override
    public void logout(ActionEvent event) throws Exception {
        super.logout(event);
        inventoryEmployee = null;
    }
}