package com.cybersectnt.data;

public class PossibleTargetUserData {
    /*
    This class is responsible for structure the information in our database, in this method we require the victim's id, username and their current points.
     */

    String UserID, Username;
    int Points;

    public PossibleTargetUserData(String userID, String username, int points) {
        UserID = userID;
        Username = username;
        Points = points;
    }
    /**
     * Those methods are getters and setters for the variables
     *
     */
    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getUsername() {
        return Username;
    }

    public int getPoints() {
        return Points;
    }

    public int getLevel() {
        return getPoints() / 100 + 1;
    }

    public void setPoints(int points) {
        Points = points;
    }

    public void setUsername(String username) {
        Username = username;
    }
}
