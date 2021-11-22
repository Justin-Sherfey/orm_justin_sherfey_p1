package com.revature.testing;

import com.revature.connection.Dao;
import com.revature.*;
import com.revature.orm.OrmPostgre;

public class Main {

    public static void main(String args[]) {
        System.out.println("Test");

        // Test if create table is working
        String createTableString = "CREATE TABLE if not exists orm_tables.javatest (column1 serial4 not null, \"name\" varchar NULL, \"password\" varchar NOT NULL, CONSTRAINT javatest_pk PRIMARY KEY (column1));";
        //Dao.sql(createTableString);

        System.out.println("------------------create done---------------------------");

        OrmPostgre.create(AmplifierPersonell.class);

        AmplifierPersonell justin = new AmplifierPersonell();
        justin.setID(10);
        justin.setName("Jeff");

        OrmPostgre.update(justin);


    }
}
