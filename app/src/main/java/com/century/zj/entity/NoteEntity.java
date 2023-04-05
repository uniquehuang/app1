package com.century.zj.entity;

public class NoteEntity {
    /**
     *  "note": "string",
     *   "title": "string",
     *   "userphone": "string"
     */
    private String id;



    private String note;
    private String title;
    private String userphone;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserphone() {
        return userphone;
    }

    public void setUserphone(String userphone) {
        this.userphone = userphone;
    }

    public NoteEntity(String note, String title, String userphone) {
        this.note = note;
        this.title = title;
        this.userphone = userphone;
    }
}
