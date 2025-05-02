module com.example.hospitalinformationsystem {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.hospitalinformationsystem to javafx.fxml;
    exports com.example.hospitalinformationsystem;
}