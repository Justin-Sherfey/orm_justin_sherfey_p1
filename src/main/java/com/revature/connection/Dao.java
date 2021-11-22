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

    /**
     * Executes query to read from database and gets a resultset that has all the obj
     * information
     *
     * @param query - query to be run
     * @return - object set that will be parsed in the orm
     */
    public static ArrayList<Object> sqlRead(StringBuilder query) {

        ArrayList<Object> objList = new ArrayList<Object>();

        try(Connection connection = ConnectionService.getInstance()) {
            PreparedStatement statement = connection.prepareStatement(String.valueOf(query));

            ResultSet rs = statement.executeQuery();

            if(rs.next()) {
                ResultSetMetaData metaData = rs.getMetaData();
                for(int i = 1; i < metaData.getColumnCount()+1; i++) {

                    String column = metaData.getColumnName(i);
                    int type = metaData.getColumnType(i);

                    if(type == Types.VARCHAR || type == Types.CHAR) {
                        objList.add(rs.getString(column));
                    } else

                    if(type == Types.BOOLEAN) {
                        objList.add(rs.getBoolean(column));
                    } else

                    if(type == Types.STRUCT) {
                        objList.add(rs.getObject(column));
                    } else

                    if(type == Types.INTEGER) {
                        objList.add(rs.getInt(column));
                    } else

                    if(type == Types.DOUBLE) {
                        objList.add(rs.getDouble(column));
                    }
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return objList;
    }
}
