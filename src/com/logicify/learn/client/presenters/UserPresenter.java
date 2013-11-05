package com.logicify.learn.client.presenters;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.logicify.learn.client.AsyncCallbackJSON;
import com.logicify.learn.client.UserServiceAsync;
import com.logicify.learn.client.common.Config;
import com.logicify.learn.client.common.HasListeners;
import com.logicify.learn.client.common.Listener;
import com.logicify.learn.client.common.NotifyListenersCallback;
import com.logicify.learn.client.models.User;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: kostyak
 * Date: 11/4/13
 * Time: 10:11 PM
 */
public class UserPresenter extends HasListeners<UserPresenterListener> implements Presenter {

    View view;
    UserServiceAsync rpcService;
    HasWidgets holder;
    User user = null;

    public interface View<T extends Widget> {
        // need okButton
        public Button getOkButton();

        //need view as Widget
        public T getAsWidget();

        //need update button
        public Button getUpdateButton();

        //need data for form sending
        public String getFormData();

        //need if we have to edit user
        public void loadUSer(User user);

        //need add state view
        public void viewForAdd();

        //need update state view
        public void viewForUpdate();
    }

    public UserPresenter(View view, UserServiceAsync rpcService, HasWidgets holder) {
        this.view = view;
        this.rpcService = rpcService;
        this.holder = holder;

        //switch view to add state
        view.viewForAdd();
        bindEvents();
    }

    /**
     * set user if it should be updated
     *
     * @param user for updating
     */
    public void setUser(User user) {
        this.user = user;

        //change state
        view.loadUSer(user);
        view.viewForUpdate();
    }

    @Override
    public void present() {
        this.user = null;
        view.viewForAdd();
        holder.add(view.getAsWidget());
    }


    private void bindEvents() {

        //add user
        Button okButton = view.getOkButton();
        // bind click event
        okButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                // getData as formString
                String formString = view.getFormData();
                String url = Config.API_URL+"user";
                AsyncCallback asyncCallback = new AsyncCallbackJSON() {
                    @Override
                    public void doStuffWithObject(JSONObject obj) {

                        Window.alert("Added successfully");

                        // notify listeners
                        notifyListeners(new NotifyListenersCallback() {
                            @Override
                            public void onCallback(Listener listener) {
                                UserPresenterListener userListener = (UserPresenterListener) listener;
                                userListener.onAddButtonEvent();
                            }
                        });
                    }
                };

                // create a add request
                rpcService.saveUser(url, formString, asyncCallback);


            }
        });

        //* update user data
        Button updateButton = view.getUpdateButton();
        updateButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                //To change body of implemented methods use File | Settings | File Templates.
                User currentUser = getCurrentUSer();
                if (currentUser == null) {
                    return;
                }
                // perform request
                String url = Config.API_URL+"user/" + currentUser.getId();
                String userRawData = view.getFormData();

                AsyncCallback asyncCallback = new AsyncCallbackJSON() {
                    @Override
                    public void doStuffWithObject(JSONObject obj) {
                        Window.alert("Updated successfully");

                        //notify listeners
                        notifyListeners(new NotifyListenersCallback() {
                            @Override
                            public void onCallback(Listener listener) {
                                UserPresenterListener userListener = (UserPresenterListener) listener;
                                userListener.onUpdatedEvent();
                            }
                        });
                    }
                };

                rpcService.updateUser(url, userRawData, asyncCallback);
            }
        });
    }

    /**
     * to use with callback
     *
     * @return
     */
    private User getCurrentUSer() {
        return user;
    }
}
