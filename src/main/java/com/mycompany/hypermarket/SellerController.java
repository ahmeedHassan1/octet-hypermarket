package com.mycompany.hypermarket;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class SellerController extends BaseController {

    @FXML
    private Seller seller;
    @FXML
    private TextField productID;
    @FXML
    private TextField newSellerEmployeeUsername;
    @FXML
    private TextField newSellerEmployeeEmail;
    @FXML
    private TextField newSellerEmployeePassword;
    @FXML
    private TextField newSellerEmployeeAddress;
    @FXML
    private TextField newSellerEmployeeNumber;
    @FXML
    private TextField productsIDs;
    @FXML
    private TextField productsQuantities;
    @FXML
    private TextField orderID;

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    @FXML
    private void listAllProducts() {
        String[] products = seller.listProducts();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("List of Products");
        alert.setHeaderText(null);
        alert.setContentText(String.join("\n", products));
        alert.showAndWait();
    }

    @FXML
    private void searchProduct() {
        try {
            if (productID.getText().isEmpty()) {
                throw new Exception("Product ID is required");
            }

            String product = Seller.searchProduct(Integer.parseInt(productID.getText()));

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
    private void createOrder() {
        try {
            if (productsIDs.getText().isEmpty()) {
                throw new Exception("Products IDs are required");
            }

            if (productsQuantities.getText().isEmpty()) {
                throw new Exception("Products quantities are required");
            }

            String[] idsString = productsIDs.getText().split(",");
            String[] quantitiesString = productsQuantities.getText().split(",");

            int[] ids = new int[idsString.length];
            int[] quantities = new int[quantitiesString.length];

            for (int i = 0; i < idsString.length; i++) {
                ids[i] = Integer.parseInt(idsString[i]);
            }
            for (int i = 0; i < quantitiesString.length; i++) {
                quantities[i] = Integer.parseInt(quantitiesString[i]);
            }

            if (ids.length != quantities.length) {
                throw new Exception("Products IDs and quantities must have the same length");
            }

            if (ids.length == 0 || quantities.length == 0) {
                throw new Exception("Products IDs and quantities are required");
            }

            seller.createOrder(ids, quantities);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Order created successfully");
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
    private void cancelOrder() {
        try {
            if (orderID.getText().isEmpty()) {
                throw new Exception("Order ID is required");
            }

            seller.cancelOrder("Order_" + orderID.getText());

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Order canceled successfully");
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
    private void updateSellerEmployee() {
        try {
            String username = newSellerEmployeeUsername.getText().isEmpty() ? seller.getUsername()
                    : newSellerEmployeeUsername.getText();
            String email = newSellerEmployeeEmail.getText().isEmpty() ? seller.getEmail()
                    : newSellerEmployeeEmail.getText();
            String password = newSellerEmployeePassword.getText().isEmpty() ? seller.getPassword()
                    : newSellerEmployeePassword.getText();
            String address = newSellerEmployeeAddress.getText().isEmpty() ? seller.getAddress()
                    : newSellerEmployeeAddress.getText();
            int number = newSellerEmployeeNumber.getText().isEmpty() ? seller.getNumber()
                    : Integer.parseInt(newSellerEmployeeNumber.getText());

            seller.updateSellerEmployee(username, email, password, address, number);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Seller updated successfully.");
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
        seller = null;
    }
}