package com.cybersectnt.data;


public class TaskListData implements Comparable<TaskListData> {
    /*
    This method is used to structure our database, every task showing in the list would require a title, subtitle, a picture,
     Query(how to earn this badge), the actual badge, claimtype (what type of reward will the user get), description(a description about each badge)
     */

    String Title, SubTitle, Picture, Query, Badge, ClaimType, Description;
    int QueryValue, ClaimValue, QueryResult;

    public TaskListData(String title, String subTitle, String picture, String query, String badge, String claimType, String description, int queryValue, int claimValue, int queryResult) {
        Title = title;
        SubTitle = subTitle;
        Picture = picture;
        Query = query;
        Badge = badge;
        ClaimType = claimType;
        Description = description;
        QueryValue = queryValue;
        ClaimValue = claimValue;
        QueryResult = queryResult;
    }

    public TaskListData() {
        Title = "";
        SubTitle = "";
        Picture = "";
        Query = "";
        QueryValue = 0;
        Badge = "";
        ClaimType = "";
        Description = "";
        ClaimValue = 0;
        QueryResult = 0;
    }

    /**
     * Those methods are getters and setters for the variables
     *
     */
    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setQueryValue(int queryValue) {
        QueryValue = queryValue;
    }

    public void setClaimValue(int claimValue) {
        ClaimValue = claimValue;
    }

    public int getQueryResult() {
        return QueryResult;
    }

    public void setQueryResult(int queryResult) {
        QueryResult = queryResult;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getSubTitle() {
        return SubTitle;
    }

    public void setSubTitle(String subTitle) {
        SubTitle = subTitle;
    }

    public String getPicture() {
        return Picture;
    }

    public void setPicture(String picture) {
        Picture = picture;
    }

    public String getQuery() {
        return Query;
    }

    public void setQuery(String query) {
        Query = query;
    }

    public int getQueryValue() {
        return QueryValue;
    }

    public void setQueryValue(String queryValue) {
        QueryValue = Integer.parseInt(queryValue);
    }


    public String getBadge() {
        return Badge;
    }

    public void setBadge(String badge) {
        Badge = badge;
    }

    public String getClaimType() {
        return ClaimType;
    }

    public void setClaimType(String claimType) {
        ClaimType = claimType;
    }

    public int getClaimValue() {
        return ClaimValue;
    }

    public void setClaimValue(String claimValue) {
        ClaimValue = Integer.parseInt(claimValue);
    }

    public int getPercentage() {
        return (int)((double) getQueryResult() / getQueryValue() * 100);
    }

    @Override
    public String toString() {
        return "TaskListData{" +
                "Title='" + Title + '\'' +
                ", Query='" + Query + '\'' +
                ", ClaimType='" + ClaimType + '\'' +
                ", QueryValue=" + QueryValue +
                ", ClaimValue=" + ClaimValue +
                ", QueryResult=" + QueryResult +
                ", Percentage=" + getPercentage() +
                '}';
    }

    @Override
    public int compareTo(TaskListData taskListData) {
        double percentage1 = getPercentage();
        double percentage2 = taskListData.getPercentage();
        if (percentage1 < percentage2) return -1;
        if (percentage1 > percentage2) return 1;
        else return 0;
    }
}
