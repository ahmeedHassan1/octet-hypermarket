package com.mycompany.hypermarket;

import java.io.IOException;

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

    public void login(ActionEvent event) throws IOException {

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

                    loader = new FXMLLoader(getClass().getResource("admin.fxml"));
                    root = loader.load();
                    AdminController adminController = loader.getController();

                    Admin admin1 = new Admin(details[1], details[2], details[3], details[4],
                    Integer.parseInt(details[5]), false);
                    admin1.setId(details[0]);

                    adminController.setAdmin(admin1);
                    break;
                case "MarketingEmployee":
                    MarketingEmployee marketingEmployee = new MarketingEmployee();
                    details = marketingEmployee.login(mail, pass);
                    System.out.println(details[0]);
                    break;
                case "InventoryEmployee":
                    InventoryEmployee inventoryEmployee = new InventoryEmployee();
                    details = inventoryEmployee.login(mail, pass);
                    System.out.println(details[0]);
                    break;
                case "Seller":
                    Seller seller = new Seller();
                    details = seller.login(mail, pass);
                    System.out.println(details[0]);
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
