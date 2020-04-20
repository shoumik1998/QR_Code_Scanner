package com.example.qr_code_scanner;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
@Entity
public class Task implements Serializable {
    @PrimaryKey(autoGenerate = true)
    protected   int id;
    @ColumnInfo(name = "title")
    private  String title;
    @ColumnInfo(name = "content")
    String content;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


}
