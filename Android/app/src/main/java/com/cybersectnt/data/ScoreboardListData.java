package com.cybersectnt.data;

public class ScoreboardListData implements Comparable<ScoreboardListData> {

    String Name, ID;
    int score;

    /**
     * This class is used for structuring our database, every user showing in the scoreboards we require their name, id and their score
     * @param name
     * @param ID
     * @param score
     */
    public ScoreboardListData(String name, String ID, int score) {
        Name = name;
        this.ID = ID;
        this.score = score;
    }

    public ScoreboardListData() {
        Name = "";
        this.ID = "";
        this.score = 0;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public int compareTo(ScoreboardListData scoreboardListData) {
        return scoreboardListData.getScore() - getScore();
    }
}
