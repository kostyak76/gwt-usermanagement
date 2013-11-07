package com.logicify.learn.client.presenters;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.logicify.learn.client.AsyncCallbackJSON;
import com.logicify.learn.client.UserServiceAsync;
import com.logicify.learn.client.common.AppUtils;
import com.logicify.learn.client.common.Config;
import com.logicify.learn.client.events.UserAddedEvent;
import com.logicify.learn.client.events.UserUpdatedEvent;
import com.logicify.learn.shared.GeneralResponse;
import com.logicify.learn.shared.User;

/**
 * Created with IntelliJ IDEA.
 * User: kostyak
 * Date: 11/4/13
 * Time: 10:11 PM
 */
public class UserPresenter implements Presenter {

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

        //need to check form data
        public boolean isDataValid();
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
                //check for data valid
                if(!view.isDataValid()){
                    Window.alert("Correct some data and try again...");
                    return;
                }

                // getData as formString
                String formString = view.getFormData();
                String url = Config.API_URL+"user";
                AsyncCallback<GeneralResponse> asyncCallback = new AsyncCallbackJSON() {
                    @Override
                    public void doStuffWithObject(GeneralResponse obj) {

                        Window.alert("Added successfully");

                        AppUtils.EVENT_BUS.fireEvent(new UserAddedEvent());

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
                //check for data valid
                if(!view.isDataValid()){
                    Window.alert("Correct some data and try again...");
                    return;
                }

                //To change body of implemented methods use File | Settings | File Templates.
                User currentUser = getCurrentUSer();
                if (currentUser == null) {
                    return;
                }
                // perform request
                String url = Config.API_URL+"user/" + currentUser._id;
                String userRawData = view.getFormData();

                AsyncCallback<GeneralResponse> asyncCallback = new AsyncCallbackJSON() {
                    @Override
                    public void doStuffWithObject(GeneralResponse obj) {
                        Window.alert("Updated successfully");

                        AppUtils.EVENT_BUS.fireEvent(new UserUpdatedEvent());

                    }
                };

                rpcService.updateUser(url, userRawData, asyncCallback);
            }
        });
    }

    /**
     * to use with callback
     *
     * @return  return current user
     */
    private User getCurrentUSer() {
        return user;
    }
}
