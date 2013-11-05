package com.logicify.learn.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.core.client.GWT;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: kostyak
 * Date: 11/1/13
 * Time: 5:06 PM
 */
@RemoteServiceRelativePath("UserService")
public interface UserService extends RemoteService {
    /**
     * Utility/Convenience class.
     * Use UserService.App.getInstance() to access static instance of UserServiceAsync
     */
    public static class App {
        private static final UserServiceAsync ourInstance = (UserServiceAsync) GWT.create(UserService.class);

        public static UserServiceAsync getInstance() {
            return ourInstance;
        }
    }

    public String getUserList(String url) throws UserServiceException;

    public String saveUser(String url, String userRawData) throws UserServiceException;

    public String deleteUser(String url) throws UserServiceException;

    public String updateUser(String url, String userRawData) throws UserServiceException;
}
