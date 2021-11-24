package com.revature.services.persistance;

import com.revature.annotations.Column;
import com.revature.annotations.PK;

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
    private static void create(Class<?> clazz) {

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
    //select * from orm_tables.amplifierpersonell where "ID" = 2
    public static Object read(Class<?> clazz, Object pk) {

        Object obj = null;
        try {
            obj = clazz.getConstructor().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        ArrayList<Object> objList = null;

        StringBuilder query = new StringBuilder();
        query.append("select * from orm_tables." + clazz.getSimpleName() +
                " where \"");

        for(Field field: clazz.getDeclaredFields()) {
            if(Arrays.toString(field.getAnnotations()).contains("PK")) {
                query.append(field.getName() + "\" = '" + pk.toString() + "'");
                objList = Dao.sqlRead(query);
            }
        }

        int count = 0;
        for(Field field: clazz.getDeclaredFields()) {
            field.setAccessible(true);

             if(field.getType().getTypeName() == "java.lang.String") {
                try {
                    field.set(obj, (String) objList.get(count));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
             if(field.getType().getTypeName() == "int") {
                try {
                    int val = (int) objList.get(count);
                    field.setInt(obj, val);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
             else if(field.getType().getTypeName() == "double") {
                try {
                    field.set(obj, (Double) objList.get(count));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            count++;
        }
        return obj;
    }

    /**
     * Updates an object in a table, if does not exist inserts into
     * If the pk is serial, only inserts into table
     *
     * @param obj
     */
    public static void update(Object obj) {

        // creates a new table for object if class does not exist already
        if(!contains(obj.getClass())) {
            create(obj.getClass());
        }

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
        // builds values portion of query
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

    /**
     * Helper function to determine if class table exists in db yet
     *
     * @param clazz - class being checked for
     * @return true or false
     */
    //SELECT to_regclass('schema_name.table_name');
    private static boolean contains(Class<?> clazz) {
        StringBuilder query = new StringBuilder();
        query.append("select to_regclass('orm_tables." + clazz.getSimpleName().toLowerCase() + "')");
        System.out.println(query);
        return Dao.sqlContains(query);
    }
}
