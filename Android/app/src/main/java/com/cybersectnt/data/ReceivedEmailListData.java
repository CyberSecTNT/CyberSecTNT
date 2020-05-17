package com.cybersectnt.data;

import java.io.Serializable;

public class ReceivedEmailListData implements Serializable, Comparable<ReceivedEmailListData> {
    /*
    This class is responsible for structuring data in our database every email will require an id for it,
    email subject, the content of the email, who it was sent from, the id of the sender, if it a fake email or not
     */


    private String ID, Subject, Body, From, PhisherID, Date;
    private boolean isFake, isPhishing;

    public ReceivedEmailListData() {
        this.ID = "";
        Subject = "";
        Body = "";
        From = "";
        PhisherID = "";
        isFake = false;
        isPhishing = false;
    }

    /**
     * Those methods are getters and setters for the variables
     *
     */
    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public String getBody() {
        return Body;
    }

    public void setBody(String body) {
        Body = body;
    }

    public String getFrom() {
        return From;
    }

    public void setFrom(String from) {
        From = from;
    }

    public String getPhisherID() {
        return PhisherID;
    }

    public void setPhisherID(String phisherID) {
        PhisherID = phisherID;
    }

    public boolean isFake() {
        return isFake;
    }

    public void setFake(boolean fake) {
        isFake = fake;
    }

    public boolean isPhishing() {
        return isPhishing;
    }

    public void setPhishing(boolean phishing) {
        isPhishing = phishing;
    }

    @Override
    public int compareTo(ReceivedEmailListData o) {
        return o.getDate().compareTo(getDate());
    }
}
