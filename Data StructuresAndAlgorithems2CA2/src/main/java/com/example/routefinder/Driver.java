package com.example.routefinder;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Driver extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Driver.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1500, 800);
        stage.setTitle("Route Finder");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws FileNotFoundException {
//        Scanner scanner = new Scanner(new File("C:\\Users\\New User\\Downloads\\Gallery.csv"));
//        scanner.useDelimiter(",");
//        while (scanner.hasNext()) {
//            System.out.println(scanner.next());
//        }
//        scanner.close();
        launch();
    }
}