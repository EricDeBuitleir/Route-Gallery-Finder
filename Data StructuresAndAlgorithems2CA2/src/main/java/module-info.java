module com.example.routefinder {
    requires javafx.controls;
    requires javafx.fxml;
    requires jmh.core;


    opens com.example.routefinder to javafx.fxml;
    exports com.example.routefinder;
}