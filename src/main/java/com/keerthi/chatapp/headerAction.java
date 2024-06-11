package com.keerthi.chatapp;

import java.util.List;
import com.keerthi.datalayer.DatabaseManager;
import com.opensymphony.xwork2.ActionSupport;

public class headerAction extends ActionSupport {
    
    private static int loginUserId;
    private String search;
    static private List<UserWithResponse> searchResults;

    public String execute() {
        setLoginUserId(new LoginAction().getLoginUserId());
        if (search != null) {
            System.out.println(loginUserId + "loginUserId");
            searchResults = DatabaseManager.getInstance().searchFriends(search, loginUserId);
            System.out.println(searchResults.size() + ".....");
            return SUCCESS;
        } else {
            return ERROR;
        }
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public List<UserWithResponse> getSearchResults() {
        return searchResults;
    }

    public int getLoginUserId() {
        return loginUserId;
    }

    public void setLoginUserId(int loginUserId) {
        this.loginUserId = loginUserId;
    }
}
