package com.example.hospitalinformationsystem.dao;

import com.example.hospitalinformationsystem.exception.InvalidInputException;
import com.example.hospitalinformationsystem.model.Patient;
import com.example.hospitalinformationsystem.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO {

    public void insertPatient(Patient patient) throws InvalidInputException {
        if (patient.getFirstName() == null || patient.getFirstName().isEmpty()) {
            throw new InvalidInputException("Field cannot be empty");
        }

        String sql = "INSERT INTO Patient VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, patient.getId());
            stmt.setString(2, patient.getFirstName());
            stmt.setString(3, patient.getLastName());
            stmt.setString(4, patient.getAddress());
            stmt.setString(4, patient.getPhone());

            stmt.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            System.err.println("Error: Patient ID already exists!");

        } catch (SQLException e) {
            System.err.println("Insert Patient details failed!");
            System.err.println("Database error: " + e.getMessage());
        }
    }


    public List<Patient> getAllPatients() {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT * FROM Patient";

        try (Connection connection = DBConnection.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql);
        ) {
            while (rs.next()) {
                Patient p = new Patient(
                        rs.getInt("patient_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("address"),
                        rs.getString("phone")
                );
                patients.add(p);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving patient records: " + e.getMessage());
        }
        return patients;
    }


    public void updatePatientPhone(int id, String firstName, String lastName, String address, String phone) {
        String sql = "UPDATE Patient SET first_name = ?, last_name = ?, address = ?, phone = ? WHERE patient_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)
        ) {
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setString(3, address);
            stmt.setString(4, phone);
            stmt.setInt(5, id);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0 ) {
                System.out.println("Updated patient details successfully!");
            } else {
                System.out.println("No patient found with given ID.");
            }

        } catch (SQLException e) {
            System.err.println("Update failed: " + e.getMessage());
        }

    }


    public void deletePatient(int id) {
        String sql = "DELETE FROM Patient WHERE patient_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)
        ) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Patient records deleted!");
        } catch (SQLException e) {
            System.err.println("Delete failed: " + e.getMessage());
        }
    }

}
