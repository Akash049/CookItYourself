package com.curl.ciykit.curl.DataBase;

/**
 * Created by Akash Chandra on 08-02-2017.
 */

public class UserDataModel {
    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String UserID;
    public String UserName;
    public String UserEmail;
    public String ImageUrl;
    public int UserLoggedInState;

    public UserDataModel() {
    }

    public UserDataModel(String userName, String userEmail, String imageUrl, int userLoggedInState, String userId) {
        UserName = userName;
        UserEmail = userEmail;
        ImageUrl = imageUrl;
        UserLoggedInState = userLoggedInState;
        UserID = userId;
    }

    public String getUserName() {
        return UserName;
    }

    public String getUserEmail() {
        return UserEmail;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public int getUserLoggedInState() {
        return UserLoggedInState;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public void setUserEmail(String userEmail) {
        UserEmail = userEmail;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public void setUserLoggedInState(int userLoggedInState) {
        UserLoggedInState = userLoggedInState;
    }
}
