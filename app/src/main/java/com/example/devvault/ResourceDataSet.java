package com.example.devvault;

public class ResourceDataSet {
    private String title;
    private String type;
    private String textContent = "";

    //TODO: video

    public ResourceDataSet(String title, String type, String textContent) {
        this.title = title;
        this.type = type;
        this.textContent = textContent;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public String getTextContent() {
        return textContent;
    }
}
