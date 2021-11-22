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

        // testing create and with non-serializable pk
        OrmPostgre.create(AmplifierPersonell.class);

        AmplifierPersonell justin = new AmplifierPersonell();
        justin.setID(10);
        justin.setName("Jeff");

        OrmPostgre.update(justin);

        // testing updating with a serial pk
        OrmPostgre.create(AmpliferSerial.class);

        AmpliferSerial jake = new AmpliferSerial();
        jake.setName("Brad");
        AmpliferSerial josh = new AmpliferSerial();
        josh.setName("Erin");

        OrmPostgre.update(jake);
        OrmPostgre.update(josh);

        // testing delete
        OrmPostgre.delete(AmpliferSerial.class, 1);

    }
}
