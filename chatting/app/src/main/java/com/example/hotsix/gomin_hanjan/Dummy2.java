package com.example.hotsix.gomin_hanjan;

public class Dummy2 {
    String id;
    String userkey;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserkey() {
        return userkey;
    }

    public void setUserkey(String userkey) {
        this.userkey = userkey;
    }

    @Override
    public String toString() {
        return id+","+userkey;
    }
}