package com.revature.connection;

import com.revature.annotations.*;
import java.lang.reflect.*;
import java.sql.*;
import com.revature.connection.ConnectionService;

import java.util.*;

/**
 * Handles executing the sql commands using the connection
 */
public class Dao {

    /**
     * Converts string to sql query to be run
     *
     * @param query - query to be run
     */
    public static void sql(StringBuilder query) {

        try(Connection connection = ConnectionService.getInstance()) {
            PreparedStatement statement = connection.prepareStatement(String.valueOf(query));
            statement.execute();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }


}
