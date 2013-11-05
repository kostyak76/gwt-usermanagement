package com.logicify.learn.client.views;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.*;
import com.logicify.learn.client.models.User;
import com.logicify.learn.client.models.UserList;
import com.logicify.learn.client.presenters.UserListPresenter;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 *
 * @author : kostyak
 *         Date: 11/4/13
 *         Time: 2:05 PM
 */
public class UserListView extends Composite implements UserListPresenter.View {

    FlexTable flexTable;
    Button addButton;
    ArrayList<Button> deleteButtons;
    FlowPanel contentDecorator;

    public UserListView() {
        contentDecorator = new FlowPanel();
        initWidget(contentDecorator);
    }

    @Override
    public void appear(UserList userList) {
        //safe clear an outer decorator
        contentDecorator.clear();

        //create new data
        flexTable = new FlexTable();
        addButton = new Button("Add");
        contentDecorator.add(flexTable);
        contentDecorator.add(addButton);

        deleteButtons = new ArrayList<Button>();
        //create header
        flexTable.setText(0, 0, "ID");
        flexTable.setText(0, 1, "Firts Name");
        flexTable.setText(0, 2, "Second Name");
        flexTable.setText(0, 3, "E-mail");
        flexTable.setText(0, 4, "Actions");

        for (int i = 0; i < userList.length(); i++) {
            //fill flextable
            User user = (User) userList.get(i);
            int rowMove = i + 1;
            flexTable.setText(rowMove, 0, user.getId());
            flexTable.setText(rowMove, 1, user.getFirstName());
            flexTable.setText(rowMove, 2, user.getLastName());
            flexTable.setText(rowMove, 3, user.getEmail());

            Button delButton = new Button("x");
            deleteButtons.add(delButton);
            flexTable.setWidget(rowMove, 4, delButton);

        }

    }

    @Override
    public Widget getAsWidget() {
        return super.asWidget();
    }

    @Override
    public Button getAddButton() {
        return addButton;
    }

    @Override
    public ArrayList<Button> getDeleteButtons() {
        return deleteButtons;
    }

    @Override
    public FlexTable getList() {
        return flexTable;
    }

    @Override
    public int getClickedRow(ClickEvent event) {
        int rowIndex = -1;
        HTMLTable.Cell clickedCell = flexTable.getCellForEvent(event);

        //check if we clocked on deleteButton
        if(clickedCell.getCellIndex() != 4){
            rowIndex = clickedCell.getRowIndex()-1;
        }

        return rowIndex;
    }
}
