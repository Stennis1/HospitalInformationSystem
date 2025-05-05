module com.example.hospitalinformationsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.hospitalinformationsystem to javafx.fxml;
    exports com.example.hospitalinformationsystem;
}