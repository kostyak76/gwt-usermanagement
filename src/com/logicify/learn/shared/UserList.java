package com.logicify.learn.shared;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: kostyak
 * Date: 10/31/13
 * Time: 3:51 PM
 */
public class UserList extends ArrayList<User> implements Serializable, RpcResolver{

    public UserList() {
    }
}
