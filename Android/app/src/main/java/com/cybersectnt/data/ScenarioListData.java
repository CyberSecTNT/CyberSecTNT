package com.cybersectnt.data;

public class ScenarioListData {
    /*
    This class is used to structure our database every scenario will have a title and an id to be shown in the list for the user
     */

    String ID, Title;

    public ScenarioListData(String ID, String title) {
        this.ID = ID;
        Title = title;
    }

    public ScenarioListData() {
        this.ID = "";
        Title = "";
    }
    /**
     * Those methods are getters and setters for the variables
     *
     */

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
}
