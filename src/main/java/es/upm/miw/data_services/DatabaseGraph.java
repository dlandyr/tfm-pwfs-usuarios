package es.upm.miw.data_services;

import es.upm.miw.documents.User;

import java.util.ArrayList;
import java.util.List;

public class DatabaseGraph {

    private List<User> userList;

    public DatabaseGraph() {
        this.userList = new ArrayList<>();
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

}
