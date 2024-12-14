package com.mycompany.hypermarket;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BaseController {

    public Stage stage;
    public Scene scene;
    public Parent root;

    public void logout(ActionEvent event) throws Exception {
        root = new FXMLLoader(getClass().getResource("login.fxml")).load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
