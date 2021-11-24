package com.revature.testing;

import com.revature.annotations.Column;
import com.revature.annotations.PK;

public class UserTest {
    @PK(serial = false)
    private String name;

    @Column(notNull = false, unique = false)
    private int pageIdOwner;

    @Column(notNull = false, unique = false)
    private int stranger;

    public UserTest() {}

    public UserTest(String name, int pageIdOwner, int pageIdStranger) {
        this.name = name;
        this.pageIdOwner = pageIdOwner;
        this.stranger = pageIdStranger;
    }

    public int getPageIdOwner() {
        return pageIdOwner;
    }

    public String getName() {
        return name;
    }

    public void setPageIdOwner(int pageIdOwner) {
        this.pageIdOwner = pageIdOwner;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPageIdStranger() {
        return stranger;
    }

    public void setPageIdStranger(int pageIdStranger) {
        this.stranger = pageIdStranger;
    }
}
