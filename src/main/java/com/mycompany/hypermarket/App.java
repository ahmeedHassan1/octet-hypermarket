package com.mycompany.hypermarket;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("primary"), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        // MarketingEmployee aa = new MarketingEmployee("username", "email", "password", "address", 123);
        // MarketingEmployee aa1 = new MarketingEmployee("username1", "email1", "password1", "address1", 123);
        // Seller aa2 = new Seller("username2", "email2", "password2", "address2", 123);
        // Seller aa3 = new Seller("username3", "email3", "password3", "address3", 123);
        //
        // String[] str = Admin.listAllEmployees();
        //
        // for (String line : str) {
        // System.out.println(line);
        // }

        Admin.updateEmployeeUsername("Seller", 2, "Ahmedggggg");

    }

}
