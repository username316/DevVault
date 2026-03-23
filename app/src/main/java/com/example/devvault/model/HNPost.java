package com.example.devvault.model;

import com.google.gson.annotations.SerializedName;

public class HNPost {
    private long id;
    private String title;
    private String url;
    private String by;
    private long time;
    private int score;

    public long getId() { return id; }
    public String getTitle() { return title; }
    public String getUrl() { return url; }
    public String getBy() { return by; }
    public long getTime() { return time; }
    public int getScore() { return score; }
}
