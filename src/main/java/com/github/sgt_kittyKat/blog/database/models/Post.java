package com.github.sgt_kittyKat.blog.database.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Post {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private String content;
    @DatabaseField
    private String heading;
    @DatabaseField
    private String dateCreated;
    @DatabaseField
    private boolean isForVip;

    public Post() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDate_created(String date_created) {
        this.dateCreated = date_created;
    }

    public boolean isForVip() {
        return isForVip;
    }

    public void setForVip(boolean forVip) {
        isForVip = forVip;
    }
}
