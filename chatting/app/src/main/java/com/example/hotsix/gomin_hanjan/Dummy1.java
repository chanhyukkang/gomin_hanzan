package com.example.hotsix.gomin_hanjan;

public class Dummy1 {
    String id;
    String title;
    String maintext;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMaintext() {
        return maintext;
    }

    public void setMaintext(String maintext) {
        this.maintext = maintext;
    }

    @Override
    public String toString() {
        return id+","+title+","+maintext;
    }
}