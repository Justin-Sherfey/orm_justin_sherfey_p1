package com.revature.orm;

import com.revature.*;
import com.revature.annotations.Column;
import com.revature.annotations.PK;
import com.revature.connection.Dao;

import java.sql.SQLException;
import java.util.*;
import java.lang.reflect.*;

/**
 * ORM to store objects in database using java methods, communicates with Dao to reach database
 */
public class OrmPostgre {

    /**
     * creates a table if one does not exist in the database yet
     *
     * @param clazz - the class of the object
     */
    public static void create(Class<?> clazz) {

        // initializes string builder and beginning of query
        StringBuilder query = new StringBuilder();

        query.append("CREATE TABLE if not exists orm_tables." + clazz.getSimpleName() + " (");

        // assigns primary key to table
        for(Field field: clazz.getDeclaredFields()) {
            if(Arrays.toString(field.getAnnotations()).contains("PK")) {
                query.append("\"" + field.getName() + "\" ");
                // checks if serial or not
                if(field.getDeclaredAnnotation(PK.class).serial()) {
                    query.append("serial4 not null primary key,");
                } else {
                    query.append(sqlType(field.getType()) + " not null primary key, ");
                }
            }
        }

        // assigns columns to table
        for(Field field: clazz.getDeclaredFields()) {
            if(Arrays.toString(field.getAnnotations()).contains("Column")) {
                query.append("\"" + field.getName() + "\" ");
                query.append(sqlType(field.getType()) + " ");
                if(field.getDeclaredAnnotation(Column.class).notNull()) {
                    query.append("not null ");
                }
                if(field.getDeclaredAnnotation(Column.class).unique()) {
                    query.append("unique ");
                }
            }
        }
        // end of query and execute with dao
        query.append(");");
        Dao.sql(query);
    }

    /**
     * Reads from the database using primary key and returns the object wanted
     *
     * @return Object found from primary key inputted
     */
    public static Object read() {

        return null;
    }

    /**
     * Updates an object in a table, if does not exist inserts into
     * If the pk is serial, only inserts into table
     *
     * @param obj
     */
    public static void update(Object obj) {

        boolean firstArg = true;
        boolean pkSerial = false;
        boolean serial = false;
        Field pk = null;

        StringBuilder query = new StringBuilder();
        query.append("insert into orm_tables." + obj.getClass().getSimpleName() + " (");

        // build fields parameters
        for(Field field: obj.getClass().getDeclaredFields()) {
            if(!firstArg) {
                query.append(",");
            }

            // assign primary key for later if it is the correct value
            if(Arrays.toString(field.getDeclaredAnnotations()).contains("PK")) {
                pk = field;
                if(field.getDeclaredAnnotation(PK.class).serial()) {
                    pkSerial = true;
                    serial = true;
                }
            }
            // if the primary key is serial, do not append it to the statement
            if(!pkSerial) {
                query.append("\"" + field.getName() + "\" ");
                firstArg = false;
            } else {
                pkSerial = false;
            }
        }

        query.append(") values (");
        firstArg = true;

        // build value parameters
        for(Field field: obj.getClass().getDeclaredFields()) {
            if(!firstArg) {
                query.append(",");
            }
            field.setAccessible(true);
            if(Arrays.toString(field.getDeclaredAnnotations()).contains("PK") &&
                    field.getDeclaredAnnotation(PK.class).serial()) {
                pkSerial = true;
            }
            // if the primary key is serial, do not append it to the statement
            if(!pkSerial) {
                try {
                    query.append("'" + field.get(obj) + "' ");
                } catch(IllegalAccessException e) {
                    e.printStackTrace();
                }
                firstArg = false;
            } else {
                pkSerial = false;
            }
        }
        // add the update value if the pk is not serializable
        if(!serial) {
            query.append(") on conflict (\"" + pk.getName() + "\") do update set ");
            firstArg = true;


            // do update if already present and needs updating
            for (Field field : obj.getClass().getDeclaredFields()) {
                if (!Arrays.toString(field.getDeclaredAnnotations()).contains("PK")) {
                    if (!firstArg) {
                        query.append(",");
                    }
                    field.setAccessible(true);
                    try {
                        query.append("\"" + field.getName() + "\" = '" + field.get(obj) + "' ");
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    firstArg = false;
                }
            }
        } else {
            query.append(")");
        }
        Dao.sql(query);
        System.out.println(query);
    }

    /**
     * Deletes the item requested identified by primary key
     */
    // delete from orm_tables.amplifierpersonell where "ID" = '10'
    public static void delete(Class<?> clazz, Object pk) {

        StringBuilder query = new StringBuilder();

        query.append("delete from orm_tables." + clazz.getSimpleName() +
                " where \"");
        for(Field field: clazz.getDeclaredFields()) {
            if(Arrays.toString(field.getAnnotations()).contains("PK")) {
                query.append(field.getName() + "\" = '" + pk.toString() + "'");
                Dao.sql(query);
                System.out.println(query);
            }
        }
    }

    /**
     * Converts from java type to sql type
     *
     * @param type
     * @return the string of the sql type corresponding
     */
    private static String sqlType(Type type) {

        switch(type.getTypeName()) {
            case "java.lang.String":
                return "text";

            case "boolean":
                return "bool";

            default:
                return type.toString();
        }
    }
}
