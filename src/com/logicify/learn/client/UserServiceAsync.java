package com.logicify.learn.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: kostyak
 * Date: 11/1/13
 * Time: 5:06 PM
 */
public interface UserServiceAsync {

    void getUserList(String url, AsyncCallback<String> async);

    void saveUser(String url, String userRawData, AsyncCallback<String> async);

    void deleteUser(String url, AsyncCallback<String> async);

    void updateUser(String url, String userRawData, AsyncCallback<String> async);
}
