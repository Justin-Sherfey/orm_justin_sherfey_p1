package com.revature.testing;

import com.revature.annotations.Column;
import com.revature.annotations.PK;

public class AmplifierUpdate {


    @Column(notNull = true, unique = true)
    private int age;

    @PK(serial = false)
    private String name;


    public AmplifierUpdate (String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() { return name; }

    public int getID() { return age; }

    public void setName(String name) { this.name = name; }

    public void setID(int ID) { this.age = age; }


}
