package com.logicify.learn.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.*;
import com.logicify.learn.shared.User;
import com.logicify.learn.client.presenters.UserListPresenter;
import com.logicify.learn.client.presenters.UserListPresenterListener;
import com.logicify.learn.client.presenters.UserPresenter;
import com.logicify.learn.client.presenters.UserPresenterListener;
import com.logicify.learn.client.views.UserListView;
import com.logicify.learn.client.views.UserView;

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
        userListPresenter.addListener(new UserListPresenterListener() {
            @Override
            public void onAddButtonEvent() {
                //userHolder.setVisible(true);
                userHolder.setVisible(true);
                userPresenter.present();
            }

            @Override
            public void onEditEvent(User user) {
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

        //userPresenter.present();

        userPresenter.addListener(new UserPresenterListener() {
            @Override
            public void onAddButtonEvent() {
                userHolder.setVisible(false);
                userListPresenter.present();
            }

            @Override
            public void onUpdatedEvent() {
                userHolder.setVisible(false);
                userListPresenter.present();
            }
        });
    }
}
