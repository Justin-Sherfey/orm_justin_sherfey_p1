package com.revature.testing;

import com.revature.annotations.Column;
import com.revature.annotations.PK;

/**
 * Sample class to test my ORM and annotations
 */
public class AmplifierPersonell {

    @PK(serial = false)
    private int ID;

    @Column(notNull = true, unique = true)
    private String name;

    public String getName() { return name; }

    public int getID() { return ID; }

    public void setName(String name) { this.name = name; }

    public void setID(int ID) { this.ID = ID; }

}
