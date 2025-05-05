package com.example.hospitalinformationsystem.utils;

import java.sql.Connection;

public class TestDB {
    public static void main (String [] args) {
        try (Connection connection = DBConnection.getConnection()) {
            System.out.println("Connection was successful!");
        } catch (Exception e) {
            System.out.println("Connection failed: " + e.getMessage());
        }
    }
}
