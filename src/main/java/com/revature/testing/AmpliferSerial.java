package com.revature.testing;

import com.revature.annotations.Column;
import com.revature.annotations.PK;

public class AmpliferSerial {
    @PK(serial = true)
    private int ID;

    @Column(notNull = true, unique = true)
    private String name;

    public String getName() { return name; }

    public int getID() { return ID; }

    public void setName(String name) { this.name = name; }

    public void setID(int ID) { this.ID = ID; }
}
