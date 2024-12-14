package com.mycompany.hypermarket;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class MarketingController extends BaseController {

    @FXML
    private MarketingEmployee marketingEmployee;
    @FXML
    private TextField reportContent;
    @FXML
    private TextField productID;
    @FXML
    private TextField discount;
    @FXML
    private TextField newMarketingEmployeeUsername;
    @FXML
    private TextField newMarketingEmployeeEmail;
    @FXML
    private TextField newMarketingEmployeePassword;
    @FXML
    private TextField newMarketingEmployeeAddress;
    @FXML
    private TextField newMarketingEmployeeNumber;

    public void setMarketingEmployee(MarketingEmployee marketingEmployee) {
        this.marketingEmployee = marketingEmployee;
    }

    @FXML
    private void createReport() {
        try {
            if (reportContent.getText().isEmpty()) {
                throw new Exception("Report content is required");
            }

            marketingEmployee.createReport(reportContent.getText());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Report created successfully");
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
    private void createOffer() {
        try {
            if (productID.getText().isEmpty()) {
                throw new Exception("Product ID is required");
            }

            if (discount.getText().isEmpty()) {
                throw new Exception("Discount is required");
            }

            marketingEmployee.createOffer(Integer.parseInt(productID.getText()),
                    Double.parseDouble(discount.getText()));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Offer created successfully");
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
    private void updateMarketingEmployee() {
        try {
            String username = newMarketingEmployeeUsername.getText().isEmpty() ? marketingEmployee.getUsername()
                    : newMarketingEmployeeUsername.getText();
            String email = newMarketingEmployeeEmail.getText().isEmpty() ? marketingEmployee.getEmail()
                    : newMarketingEmployeeEmail.getText();
            String password = newMarketingEmployeePassword.getText().isEmpty() ? marketingEmployee.getPassword()
                    : newMarketingEmployeePassword.getText();
            String address = newMarketingEmployeeAddress.getText().isEmpty() ? marketingEmployee.getAddress()
                    : newMarketingEmployeeAddress.getText();
            int number = newMarketingEmployeeNumber.getText().isEmpty() ? marketingEmployee.getNumber()
                    : Integer.parseInt(newMarketingEmployeeNumber.getText());

            marketingEmployee.updateMarketingEmployee(username, email, password, address, number);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Marketing updated successfully.");
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
        marketingEmployee = null;
    }
}