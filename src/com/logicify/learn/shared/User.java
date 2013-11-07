package com.logicify.learn.shared;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: kostyak
 * Date: 10/31/13
 * Time: 3:52 PM
 */
public class User implements Serializable  {

    public String firstName;
    public String lastName;
    public String email;
    public String _id;

    public User() {
    }
}
