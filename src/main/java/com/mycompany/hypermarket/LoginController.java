package com.mycompany.hypermarket;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private TextField email;
    @FXML
    private TextField password;
    @FXML
    private MenuButton role;
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    public void initialize() {
        for (MenuItem item : role.getItems()) {
            item.setOnAction(event -> role.setText(item.getText()));
        }
    }

    @FXML
    public void login(ActionEvent event) throws Exception {

        try {
            if (email.getText().isEmpty()) {
                throw new Exception("Email is required");
            }

            if (password.getText().isEmpty()) {
                throw new Exception("Password is required");
            }

            if (role.getText().equals("Role")) {
                throw new Exception("Role is required");
            }

            String mail = email.getText();
            String pass = password.getText();
            String rol = role.getText();

            String[] details = null;
            FXMLLoader loader = null;

            switch (rol) {
                case "Admin":
                    Admin admin = new Admin();
                    details = admin.login(mail, pass);

                    admin.setId(details[0]);
                    admin.setUsername(details[1]);
                    admin.setEmail(details[2]);
                    admin.setPassword(details[3]);
                    admin.setAddress(details[4]);
                    admin.setNumber(Integer.parseInt(details[5]));

                    loader = new FXMLLoader(getClass().getResource("admin.fxml"));
                    root = loader.load();
                    AdminController adminController = loader.getController();

                    adminController.setAdmin(admin);
                    break;
                case "MarketingEmployee":
                    MarketingEmployee marketingEmployee = new MarketingEmployee();
                    details = marketingEmployee.login(mail, pass);

                    marketingEmployee.setId(details[0]);
                    marketingEmployee.setUsername(details[1]);
                    marketingEmployee.setEmail(details[2]);
                    marketingEmployee.setPassword(details[3]);
                    marketingEmployee.setAddress(details[4]);
                    marketingEmployee.setNumber(Integer.parseInt(details[5]));

                    loader = new FXMLLoader(getClass().getResource("marketing.fxml"));
                    root = loader.load();
                    MarketingController marketingController = loader.getController();
                    marketingController.setMarketingEmployee(marketingEmployee);
                    break;
                case "InventoryEmployee":
                    InventoryEmployee inventoryEmployee = new InventoryEmployee();
                    details = inventoryEmployee.login(mail, pass);

                    inventoryEmployee.setId(details[0]);
                    inventoryEmployee.setUsername(details[1]);
                    inventoryEmployee.setEmail(details[2]);
                    inventoryEmployee.setPassword(details[3]);
                    inventoryEmployee.setAddress(details[4]);
                    inventoryEmployee.setNumber(Integer.parseInt(details[5]));

                    loader = new FXMLLoader(getClass().getResource("inventory.fxml"));
                    root = loader.load();
                    InventoryController inventoryController = loader.getController();
                    inventoryController.setInventoryEmployee(inventoryEmployee);
                    break;
                case "Seller":
                    Seller seller = new Seller();
                    details = seller.login(mail, pass);

                    seller.setId(details[0]);
                    seller.setUsername(details[1]);
                    seller.setEmail(details[2]);
                    seller.setPassword(details[3]);
                    seller.setAddress(details[4]);
                    seller.setNumber(Integer.parseInt(details[5]));

                    loader = new FXMLLoader(getClass().getResource("seller.fxml"));
                    root = loader.load();
                    SellerController sellerController = loader.getController();
                    sellerController.setSeller(seller);
                    break;
                default:
                    throw new Exception("Invalid role");
            }

            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Validation Error");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}
