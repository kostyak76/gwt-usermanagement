package com.logicify.learn.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.logicify.learn.shared.GeneralResponse;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: kostyak
 * Date: 11/1/13
 * Time: 5:06 PM
 */
public interface UserServiceAsync {

    void getUserList(String url, AsyncCallback<GeneralResponse> async);

    void saveUser(String url, String userRawData, AsyncCallback<GeneralResponse> async);

    void deleteUser(String url, AsyncCallback<GeneralResponse> async);

    void updateUser(String url, String userRawData, AsyncCallback<GeneralResponse> async);
}
