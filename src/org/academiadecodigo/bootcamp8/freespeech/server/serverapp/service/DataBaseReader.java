package org.academiadecodigo.bootcamp8.freespeech.server.serverapp.service;


import org.academiadecodigo.bootcamp8.freespeech.server.serverapp.Utils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Developed @ <Academia de Código_>
 * Created by
 * <Code Cadet> PedroMAlves
 */
public class DataBaseReader {
    private Statement statement;
    private JdbcConnectionManager connection;

    public DataBaseReader(JdbcConnectionManager manager) {
        connection = manager;
    }


    public String executeQuery(String query) {
        statement = null;
        ResultSet resultSet;
        ResultSetMetaData metaData;
        StringBuilder builder = new StringBuilder();
        builder.append(query);
        builder.append("\n");

        try {

            statement = connection.getConnection().createStatement();
            resultSet = statement.executeQuery(query);
            metaData = resultSet.getMetaData();
            int columnsNumber = metaData.getColumnCount();


            while (resultSet.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) builder.append(" <||> ");
                    String columnValue = resultSet.getString(i);
                    builder.append(metaData.getColumnName(i));
                    builder.append(": ");
                    builder.append(columnValue);
                }
                builder.append("\n");
            }
            builder.append("\n");

        } catch (SQLException e) {
            System.out.println("Unable to read from database " + e.getMessage());
        }
        return builder.toString();
    }

    public boolean clearTable() {
        boolean deleteOk;
        statement = null;

        try {
            statement = connection.getConnection().createStatement();
            statement.executeUpdate(Utils.CLEAR_TABLE);
            deleteOk = true;
        } catch (SQLException e) {
            deleteOk = false;
        } finally {
            closeStatement();
        }
        return deleteOk;
    }

    public void closeStatement() {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

