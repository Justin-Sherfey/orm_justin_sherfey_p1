package com.revature.testing;

import com.revature.annotations.*;

public class User {
    @PK(serial = false)
    private String name;

    @Column(notNull = false, unique = false)
    private int age;

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }
}