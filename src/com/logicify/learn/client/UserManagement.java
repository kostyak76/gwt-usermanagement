package com.logicify.learn.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.*;
import com.logicify.learn.client.common.AppUtils;
import com.logicify.learn.client.events.*;
import com.logicify.learn.shared.User;
import com.logicify.learn.client.presenters.UserListPresenter;
import com.logicify.learn.client.presenters.UserPresenter;
import com.logicify.learn.client.views.*;

public class UserManagement implements EntryPoint {
    /**
     * The message displayed to the user when the server cannot be reached or
     * returns an error.
     */
    private static final String SERVER_ERROR = "An error occurred while "
            + "attempting to contact the server. Please check your network "
            + "connection and try again.";
    final UserServiceAsync rpcService = UserService.App.getInstance();
//    final PopupPanel userHolder = new PopupPanel();
//    final DialogBox userHolder = new DialogBox();
    final VerticalPanel userHolder = new VerticalPanel();

    VerticalPanel mainHolder;
    UserPresenter userPresenter;
    UserListPresenter userListPresenter;


    @Override
    public void onModuleLoad() {

        mainHolder = new VerticalPanel();
        // create a main views
        RootPanel.get("main-content").add(mainHolder);

        //## create user list presenter
        //* define placeholder for them
        VerticalPanel listHolder = new VerticalPanel();
        mainHolder.add(listHolder);

        //* init userList presenter
        initUserListView(listHolder);

        //## init user view
        //* define placeholder for them
        mainHolder.add(userHolder);

        //* init user presenter
        initAddUserView();
    }

    /**
     * create and init UserListPresenter
     * @param listHolder holder for presenters' view
     */
    private void initUserListView(final VerticalPanel listHolder) {

        //create first part of UI
        UserListView listView = new UserListView();
        userListPresenter = new UserListPresenter(listView, rpcService, listHolder);

        AppUtils.EVENT_BUS.addHandler(AddUserEvent.TYPE, new AddUserEventHandler() {
            @Override
            public void onAddUserEvent(AddUserEvent event) {
                userHolder.setVisible(true);
                userPresenter.present();
            }
        });

        AppUtils.EVENT_BUS.addHandler(EditUserEvent.TYPE, new EditUserEventHandler() {
            @Override
            public void onEditEvent(EditUserEvent event, User user) {
                //clear userHolder
                userHolder.setVisible(true);
                userPresenter.present();

                //create a new UserView
                userPresenter.setUser(user);
            }
        });

        userListPresenter.present();
    }

    private void initAddUserView() {

        final UserView userView = new UserView();

        //init different presenters
        userPresenter = new UserPresenter(userView, rpcService, userHolder);

        //added event handlers
        AppUtils.EVENT_BUS.addHandler(UserAddedEvent.TYPE, new UserAddedEventHandler(){
            @Override
            public void onUserAdded(UserAddedEvent event) {
                userHolder.setVisible(false);
                userListPresenter.present();
            }
        });

        AppUtils.EVENT_BUS.addHandler(UserUpdatedEvent.TYPE, new UserUpdatedEventHandler() {
            @Override
            public void onUserUpdated(UserUpdatedEvent event) {
                userHolder.setVisible(false);
                userListPresenter.present();
            }
        });

    }
}
