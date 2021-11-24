package com.revature.services.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.lang.*;

public class ConnectionService {

    private static final String url = "jdbc:postgresql://bank-db.cv3oxlhpjbwv.us-east-2.rds.amazonaws.com:5432/postgres?currentSchema=orm_tables";
    private static final String username = "postgres";
    private static final String password = "ryder123";

    private static Connection instance;

    /**
     * Constructor for connector singleton instance
     */
    private ConnectionService(){}

    /**
     * Checks to make sure only instance running, returns a new connection if none running
     *
     * @return new connection that will be used to connect to database
     * @throws SQLException catches any sql exceptions that may be thrown
     */
    public static Connection getInstance() throws SQLException {
        if(instance == null || instance.isClosed()){
            try {
                Class.forName("org.postgresql.Driver");

                instance = DriverManager.getConnection(url, username, password);
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }
}

