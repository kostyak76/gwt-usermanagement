package com.logicify.learn.client.presenters;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.logicify.learn.client.AsyncCallbackJSON;
import com.logicify.learn.client.common.AppUtils;
import com.logicify.learn.client.common.Config;
import com.logicify.learn.client.events.AddUserEvent;
import com.logicify.learn.client.events.EditUserEvent;
import com.logicify.learn.shared.GeneralResponse;
import com.logicify.learn.shared.User;
import com.logicify.learn.shared.UserList;
import com.logicify.learn.client.UserServiceAsync;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 *
 * @author : kostyak
 *         Date: 11/4/13
 *         Time: 11:07 AM
 */
public class UserListPresenter implements Presenter {
    private View view;
    private UserServiceAsync rpcService;
    /**
     * this is the holder for presenter view
     */
    private HasWidgets holder;
    private UserList userList;

    @Override
    public void present() {
        getUserListAndInitView();
    }

    public interface View<T extends Widget> {
        // interface to present
        //need method paint or fill or show or create or appear
        public void appear(UserList userList);

        //need a method which can take this view as Widget obj
        public T getAsWidget();

        //need to take access to ADD button
        public Button getAddButton();

        //need a list of all delete buttons
        public ArrayList<Button> getDeleteButtons();

        //need a whole table to attach an event
        public FlexTable getList();

        public int getClickedRow(ClickEvent event);

    }

    public UserListPresenter(View view, UserServiceAsync rpcService, HasWidgets holder) {
        // create presenter
        this.view = view;
        this.rpcService = rpcService;
        this.holder = holder;
    }

    private void getUserListAndInitView() {
        // get user data using callback function

        AsyncCallback<GeneralResponse> callback = new AsyncCallbackJSON() {
            @Override
            public void doStuffWithObject(GeneralResponse obj) {
                // convert data
                userList = (UserList) obj.data;
                initView();
            }
        };

        //get data
        String url = Config.API_URL + "users";
        rpcService.getUserList(url, callback);
    }

    private void initView() {
        // assign data to view
        holder.add(view.getAsWidget());
        view.appear(userList);
        bindEvents();
    }

    // bind events to view
    private void bindEvents() {

        //addButtonClick
        view.getAddButton().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(final ClickEvent event) {

                AppUtils.EVENT_BUS.fireEvent(new AddUserEvent());

            }
        });

        //deleteButtonClick
        ArrayList delButtons = view.getDeleteButtons();
        for (int i = 0; i < delButtons.size(); i++) {
            Button delButton = (Button) delButtons.get(i);
            defineClickHandler(delButton, i);
        }

        //edit click event
        view.getList().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                //define clicked index
                final int id = view.getClickedRow(event);

                //check if id is positive number
                if (id == -1) {
                    return;
                }

                AppUtils.EVENT_BUS.fireEvent(new EditUserEvent(userList.get(id)));

            }
        });
    }

    /**
     * first variant of defining ID in Button and sending directly to the click callback
     *
     * @param delButton button to be binded
     * @param i         id number of button
     */
    private void defineClickHandler(Button delButton, final int i) {
        delButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(final ClickEvent event) {
                deleteUser(i);
            }
        });
    }

    private void deleteUser(int i) {

        if (!Window.confirm("Are you sure about to delete an item?")) {
            return;
        }

        AsyncCallback<GeneralResponse> asyncCallback = new AsyncCallbackJSON() {
            @Override
            public void doStuffWithObject(GeneralResponse obj) {
                getUserListAndInitView();
            }
        };
        User user = userList.get(i);
        String userId = user._id;
        String url = Config.API_URL + "user/" + userId;

        rpcService.deleteUser(url, asyncCallback);
    }
}
