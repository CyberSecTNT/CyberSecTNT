package com.cybersectnt.data;

public class AntiVirusListData {
    /*
    This class is used to structure the database, every attack required the victim id, the attacker id, and a name of that virus.
     */



    String DocumentID, AttackerID, Name, AttackerDocumentID;

    /**
     * This method return the document id
     *
     */
    public String getDocumentID() {
        return DocumentID;
    }

    /**
     * This method change the document ID
     * @param documentID
     */
    public void setDocumentID(String documentID) {
        DocumentID = documentID;
    }

    /**
     * This method returns the attacker ID
     * @return
     */

    public String getAttackerID() {
        return AttackerID;
    }

    /**
     * This method changes the attacker ID
     * @param attackerID
     */
    public void setAttackerID(String attackerID) {
        AttackerID = attackerID;
    }

    /**
     * This method return the name
     * @return
     */
    public String getName() {
        return Name;
    }

    /**
     * This method changes the name
     * @param name
     */
    public void setName(String name) {
        Name = name;
    }

    /**
     * This method returns attacker ID
     * @return
     */
    public String getAttackerDocumentID() {
        return AttackerDocumentID;
    }

    /**
     * This method changed the attacker ID
     * @param attackerDocumentID
     */
    public void setAttackerDocumentID(String attackerDocumentID) {
        AttackerDocumentID = attackerDocumentID;
    }
}
