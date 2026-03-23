package com.example.devvault.db.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.io.Serializable;

@Entity(tableName = "resources")
public class Resource implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String title;
    public String description;
    public String url;
    public String mediaType;
    public String language;
    public long createdAt;
}
