package com.cybersectnt.data;

public class PendingAttackListData implements Comparable<PendingAttackListData> {
    /*
    This class is responsible for structuring the data in database, every attack requires the username, the state of the attack, the id of the attacker, the attack id(document id), and which tool is used.
     */
    String UserName, State, StartTime, EndTime, UserID, ToolUsed, DocumentID;

    public PendingAttackListData() {
        UserName = "";
        State = "";
        StartTime = "";
        EndTime = "";
        UserID = "";
        DocumentID = "";
        ToolUsed = "";
    }

    /**
     * Those methods are getters and setters for the variables
     *
     */

    public String getDocumentID() {
        return DocumentID;
    }

    public void setDocumentID(String documentID) {
        DocumentID = documentID;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String startTime) {
        StartTime = startTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
    }

    public String getToolUsed() {
        return ToolUsed;
    }

    public void setToolUsed(String toolUsed) {
        ToolUsed = toolUsed;
    }

    /**
     * The method is used to convert the attacks to string
     */
    @Override
    public String toString() {
        return "com.example.data.PendingAttackListData{" +
                "UserName='" + UserName + '\'' +
                ", State='" + State + '\'' +
                ", StartTime='" + StartTime + '\'' +
                ", EndTime='" + EndTime + '\'' +
                ", UserID='" + UserID + '\'' +
                '}';
    }

    /**
     * This method is to compare
     * @param o
     */
    @Override
    public int compareTo(PendingAttackListData o) {
        if (getState().equals("Success")) {
            return 1;
        } else if (o.getState().equals("Success")) {
            return -1;
        }
        return getEndTime().compareTo(o.getEndTime());
    }
}
