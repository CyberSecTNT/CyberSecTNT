package com.cybersectnt.data;

public class ToolsUpgradeListData {
    /*
    This class is ued to structure our database, so every tool needs a price to be upgrading, an icon, what level will the user be upgrading to,
    a description of this tool, and the name of this tool.
     */
    String Name, Description;
    long IconID, Price;
    long Level;


    public ToolsUpgradeListData() {
        Name = "";
        Description = "";
        Price = 0;
        IconID = 0;
        Level = 0;
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

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public long getPrice() {
        return Price * (Level + 1);
    }

    public void setPrice(long price) {
        Price = price;
    }

    public long getIconID() {
        return IconID;
    }

    public void setIconID(long iconID) {
        IconID = iconID;
    }

    public long getLevel() {
        return Level;
    }

    public void setLevel(long level) {
        Level = level;
    }
}
