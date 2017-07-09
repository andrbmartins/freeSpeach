package org.academiadecodigo.bootcamp8.freespeech.server.model;

import org.academiadecodigo.bootcamp8.freespeech.server.utils.logger.Logger;
import org.academiadecodigo.bootcamp8.freespeech.server.utils.logger.LoggerMessages;
import org.academiadecodigo.bootcamp8.freespeech.server.utils.logger.TypeEvent;
import org.academiadecodigo.bootcamp8.freespeech.shared.Queries;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Developed @ <Academia de Código_>
 * Created by
 * <Code Cadet> JPM Ramos
 */

public class ConnectionManager {
    private Connection connection;

    public Connection getConnection() {

        final String URL = "jdbc:mysql://localhost:3306/freespeech";
        final String USER = "root";
        final String PASSWORD = "1234";

        try {
            if (connection == null) {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                Logger.getInstance().eventlogger(TypeEvent.DATABASE, LoggerMessages.DB_CONNECT);
            }
        } catch (SQLException ex) {
            System.err.println(LoggerMessages.DB_DISCONNECT + ex.getMessage());
            System.exit(1);
        }

        return connection;
    }

    public boolean insertUser(String username, String password) {

        boolean registered = true;
        PreparedStatement statement = null;

        try {

            statement = connection.prepareStatement(Queries.INSERT_USER);
            statement.setString(1, username);
            statement.setString(2, password);
            statement.execute();
            statement = connection.prepareStatement(Queries.INSERT_INTO_BIO);
            statement.setString(1, username);
            statement.executeUpdate();

        } catch (SQLException e) {
            Logger.getInstance().eventlogger(TypeEvent.DATABASE, e.getMessage());
            registered = false;

        } finally {

            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                Logger.getInstance().eventlogger(TypeEvent.DATABASE, e.getMessage());
            }
        }

        return registered;
    }

    public User findUser(String username) throws SQLException {

        User user = null;
        PreparedStatement statement = connection.prepareStatement(Queries.SELECT_USER);
        statement.setString(1, username);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {

            String usernameValue = resultSet.getString("user_name");
            String passwordValue = resultSet.getString("user_password");
            user = new User(usernameValue, passwordValue);
        }

        statement.close();

        return user;
    }

    public boolean changePass(String username, String newPass) {

        boolean passChanged = true;
        PreparedStatement statement = null;

        try {

            statement = connection.prepareStatement(Queries.ALTER_PASSWORD);
            statement.setString(1, newPass);
            statement.setString(2, username);
            statement.execute();

        } catch (SQLException e1) {

            Logger.getInstance().eventlogger(TypeEvent.DATABASE, e1.getMessage());
            passChanged = false;

        } finally {

            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                Logger.getInstance().eventlogger(TypeEvent.DATABASE, e.getMessage());
            }
        }

        return passChanged;
    }

    public boolean deleteAccount(String username) {

        boolean deleted = true;
        PreparedStatement statement = null;

        try {

            statement = connection.prepareStatement(Queries.DELETE_USER);
            statement.setString(1, username);
            statement.execute();

        } catch (SQLException e1) {

            Logger.getInstance().eventlogger(TypeEvent.DATABASE, e1.getMessage());
            deleted = false;

        } finally {

            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                Logger.getInstance().eventlogger(TypeEvent.DATABASE, e.getMessage());
            }

        }

        return deleted;
    }

    // TODO why not prepared statement?
    public int count() throws SQLException {

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(Queries.COUNT_USERS);

        return resultSet.next() ? resultSet.getInt(1) : 0;

    }

    public List<String> getUserBio(String username) throws SQLException {

        PreparedStatement statement = connection.prepareStatement(Queries.SHOW_BIO);
        statement.setString(1, username);
        ResultSet resultSet = statement.executeQuery();
            List<String> userbio = new LinkedList<String>();

        if (resultSet.next()) {
            userbio.add(resultSet.getString("user_name"));
            userbio.add(resultSet.getString("email"));
            userbio.add(resultSet.getString("date_birth"));
            userbio.add(resultSet.getString("bio"));
        }

        statement.close();

        return userbio;
    }

    public boolean updateBio(String username, String email, String dateBirth, String bio) {

        PreparedStatement statement = null;
        boolean updated = true;

        try {

            statement = connection.prepareStatement(Queries.UPDATE_BIO);
            statement.setString(1, email);
            statement.setString(2, dateBirth);
            statement.setString(3, bio);
            statement.setString(4, username);
            statement.execute();

        } catch (SQLException e) {
            Logger.getInstance().eventlogger(TypeEvent.DATABASE, e.getMessage());
            updated = false;
        } finally {

            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                Logger.getInstance().eventlogger(TypeEvent.DATABASE, e.getMessage());
            }

        }

        return updated;
    }



    public void reportUser(String username , String user_reported){

        PreparedStatement statement = null;

        try {

            statement = connection.prepareStatement(Queries.REPORT_USER);
            statement.setString(1, username);
            statement.setString(2, user_reported);
            statement.execute();

        } catch (SQLException e) {
            Logger.getInstance().eventlogger(TypeEvent.DATABASE, e.getMessage());
        } finally {

            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                Logger.getInstance().eventlogger(TypeEvent.DATABASE, e.getMessage());
            }

        }

    }


    public int verifyReport(String username, String user_reported) {

        PreparedStatement statement = null;
        ResultSet resultSet;
        try {

            statement = connection.prepareStatement(Queries.REPORTED_USER);
            statement.setString(1, username);
            statement.setString(2, user_reported);
            resultSet =  statement.executeQuery();
            return resultSet.next() ? resultSet.getInt(1) : 0;

        } catch (SQLException e) {
            Logger.getInstance().eventlogger(TypeEvent.DATABASE, e.getMessage());
        } finally {

            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                Logger.getInstance().eventlogger(TypeEvent.DATABASE, e.getMessage());
            }

        }

        return 0;
    }



    public int verifyUserReported(String username) {
        PreparedStatement statement = null;
        ResultSet resultSet;
        try {

            statement = connection.prepareStatement(Queries.COUNT_REPORTED);
            statement.setString(1, username);
            resultSet =  statement.executeQuery();
            return resultSet.next() ? resultSet.getInt(1) : 0;

        } catch (SQLException e) {
            Logger.getInstance().eventlogger(TypeEvent.DATABASE, e.getMessage());
        } finally {

            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                Logger.getInstance().eventlogger(TypeEvent.DATABASE, e.getMessage());
            }

        }

        return 0;
    }

    public void close() {

        try {
            if (connection != null) {
                Logger.getInstance().eventlogger(TypeEvent.DATABASE, LoggerMessages.DB_TERMINATE);
                connection.close();
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

    }
}