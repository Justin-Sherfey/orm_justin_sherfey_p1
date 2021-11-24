package com.revature.testing;

import com.revature.services.persistance.OrmPostgre;

public class Main {

    public static void main(String args[]) {
        System.out.println("Test");

        // Test if create table is working
        String createTableString = "CREATE TABLE if not exists orm_tables.javatest (column1 serial4 not null, \"name\" varchar NULL, \"password\" varchar NOT NULL, CONSTRAINT javatest_pk PRIMARY KEY (column1));";
        //Dao.sql(createTableString);

        OrmPostgre.create(UserTest.class);


        System.out.println("------------------create done---------------------------");

        // testing create and with non-serializable pk
        //OrmPostgre.create(AmplifierPersonell.class);
        //OrmPostgre.create(User.class);

        AmplifierUpdate chloe = new AmplifierUpdate("Chloe", 22);

        OrmPostgre.update(chloe);

        AmplifierPersonell justin = new AmplifierPersonell();
        justin.setID(10);
        justin.setName("Jeff");

        OrmPostgre.update(justin);

        // testing updating with a serial pk
        // OrmPostgre.create(AmpliferSerial.class);

        AmpliferSerial jake = new AmpliferSerial();
        jake.setName("Tim");
        AmpliferSerial josh = new AmpliferSerial();
        josh.setName("Julien");

        //OrmPostgre.update(jake);
        //OrmPostgre.update(josh);

        // testing delete
        OrmPostgre.delete(AmpliferSerial.class, 10);

        OrmPostgre.read(AmpliferSerial.class, 3);

        AmplifierPersonell henry = new AmplifierPersonell();
        henry.setID(44);
        henry.setName("Henry");

        OrmPostgre.update(henry);

        AmplifierPersonell henryEmpty = new AmplifierPersonell();
        henryEmpty = (AmplifierPersonell) OrmPostgre.read(AmplifierPersonell.class, 44);
        System.out.println(henryEmpty.getID());
        System.out.println(henryEmpty.getName());

        AmpliferSerial sophia = new AmpliferSerial();
        sophia.setName("Sophia");

        // OrmPostgre.update(sophia);

        AmpliferSerial sophiaEmpty = new AmpliferSerial();
        sophiaEmpty = (AmpliferSerial) OrmPostgre.read(AmpliferSerial.class, 20);
        System.out.println(sophiaEmpty.getID());
        System.out.println(sophiaEmpty.getName());

        System.out.println("------------Read test-----------------");
        //User user = (User) OrmPostgre.read(User.class, "Justin");
        //System.out.println(user.getAge());





    }
}
