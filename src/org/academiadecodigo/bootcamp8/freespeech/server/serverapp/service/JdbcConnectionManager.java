package org.academiadecodigo.bootcamp8.freespeech.server.serverapp.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Developed @ <Academia de Código_>
 * Created by
 * <Code Cadet> PedroMAlves
 */
public class JdbcConnectionManager {
    private Connection connection = null;


    public Connection getConnection() {

        try {
            if (connection == null) {
                connection = DriverManager.getConnection("jdbc:mysql://localhost/book_loan", "root", "");
            }
        } catch (SQLException ex) {
            System.out.println("Connection to database failed : " + ex.getMessage());
        }
        return connection;
    }

    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException ex) {
            System.out.println("Error while closing database connection: " + ex.getMessage());
        }
    }
}
