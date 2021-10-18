package com.ennovation.taxwale.Model;

public class GSTResponse {
    private String name;
    private String subName;
    private int img;

    public GSTResponse(String name, String subName, int img) {
        this.name = name;
        this.subName = subName;
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
