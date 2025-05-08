package com.example.hospitalinformationsystem.controller;

import com.example.hospitalinformationsystem.dao.PatientDAO;
import com.example.hospitalinformationsystem.exception.InvalidInputException;
import com.example.hospitalinformationsystem.model.Patient;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;

public class MainController {

    @FXML
    private TextField idField, firstNameField, lastNameField, addressField, phoneField;

    @FXML
    private TableView<Patient> patientTable;

    @FXML
    private TableColumn<Patient, Integer> idColumn;

    @FXML
    private TableColumn<Patient, String> firstNameColumn, lastNameColumn, addressColumn, phoneColumn;

    private final PatientDAO patientDAO = new PatientDAO();

    @FXML
    public void initiliaze() {
        // Bind table columns
        idColumn.setCellValueFactory(cellData -> new javafx.beans.property.
                SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        firstNameColumn.setCellValueFactory(cellData -> new javafx.beans.property.
                SimpleStringProperty(cellData.getValue().getFirstName()));
        lastNameColumn.setCellValueFactory(cellData -> new javafx.beans.property.
                SimpleStringProperty(cellData.getValue().getLastName()));
        addressColumn.setCellValueFactory(cellData -> new javafx.beans.property.
                SimpleStringProperty(cellData.getValue().getAddress()));
        phoneColumn.setCellValueFactory(cellData -> new javafx.beans.property.
                SimpleStringProperty(cellData.getValue().getPhone()));

        refreshTable();
    }

    @FXML
    public void addPatient() {
        try {
            Patient patient = new Patient(
                    Integer.parseInt(idField.getText()),
                    firstNameField.getText(),
                    lastNameField.getText(),
                    addressField.getText(),
                    phoneField.getText()
            );
            patientDAO.insertPatient(patient);
            showAlert(Alert.AlertType.INFORMATION, "Patient added successfully.");
            refreshTable();
            clearForm();
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "ID must be a number");
        } catch (InvalidInputException e) {
            showAlert(Alert.AlertType.ERROR, e.getMessage());
        }
    }

    @FXML
    public void updatePatient() {
        try {
            int id = Integer.parseInt(idField.getText());
            patientDAO.updatePatientPhone(
                    id, firstNameField.getText(), lastNameField.getText(),
                    phoneField.getText(), addressField.getText()
            );
            showAlert(Alert.AlertType.INFORMATION, "Patient updated!");
            clearForm();
            refreshTable();
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "ID must be a number");
        }
    }

    @FXML
    public void deletePatient() {
        try  {
            int id = Integer.parseInt(idField.getText());
            patientDAO.deletePatient(id);
            showAlert(Alert.AlertType.INFORMATION, "Patient deleted.");
            refreshTable();
            clearForm();
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "ID must be a number!");
        }
    }

    private void refreshTable() {
        List<Patient> patients = patientDAO.getAllPatients();
        ObservableList<Patient> observablePatients = FXCollections.observableArrayList(patients);
        patientTable.setItems(observablePatients);
    }

    private void clearForm() {
        idField.clear();
        firstNameField.clear();
        lastNameField.clear();
        addressField.clear();
        phoneField.clear();
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setTitle("Patient Management");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}