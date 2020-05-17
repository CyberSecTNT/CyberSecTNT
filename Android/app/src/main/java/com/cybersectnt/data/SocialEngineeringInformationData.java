package com.cybersectnt.data;

import android.util.Log;

public class SocialEngineeringInformationData {
    /*
    This method is used to structure data in our database, every scaremongering info is reburied to have a picture, the actual information and the title.
     */
    String title, information, img, by;
    boolean isSelected = false;

    public SocialEngineeringInformationData() {
        this.title = "";
        this.information = "";
        this.img = "";
    }

    /**
     * Those methods are getters and setters for the variables
     *
     */
    public String getBy() {
        return by;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public void setBy(String by) {
        this.by = by;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getImg() {
        String appended = title.toLowerCase().trim().replace(" ", "_") + "_" + information.toLowerCase().trim().replace(" ", "_");
        img = "https://firebasestorage.googleapis.com/v0/b/cybersectntdemo.appspot.com/o/Information%2F" + appended + ".jpg?alt=media";
        return img;
    }

}
