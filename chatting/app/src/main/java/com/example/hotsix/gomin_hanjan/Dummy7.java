package com.example.hotsix.gomin_hanjan;

public class Dummy7 {
    String category;
    String agemin;
    String agemax;
    String sex;
    String trust;
    String title;
    String maintext;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAgemin() {
        return agemin;
    }

    public void setAgemin(String agemin) {
        this.agemin = agemin;
    }

    public String getAgemax() {
        return agemax;
    }

    public void setAgemax(String agemax) {
        this.agemax = agemax;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getTrust() {
        return trust;
    }

    public void setTrust(String trust) {
        this.trust = trust;
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
        return category+","+agemin+","+agemax+","+sex+","+trust+","+title+","+maintext;
    }
}