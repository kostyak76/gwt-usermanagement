package com.logicify.learn.client;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Created with IntelliJ IDEA.
 * User: kostyak
 * Date: 11/1/13
 * Time: 5:27 PM
 */
public class UserServiceException extends Exception implements IsSerializable {

    private String message;

    public UserServiceException() {
        super();
    }

    public UserServiceException(String message) {
        super(message);
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}
