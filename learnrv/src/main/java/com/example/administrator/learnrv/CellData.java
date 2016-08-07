package com.example.administrator.learnrv;

/**
 * Created by Administrator on 2016/3/16.
 */
public class CellData {

    public CellData(String title, String content) {
        this.title = title;
        this.content = content;
    }

    private String title = "title";
    private String content = "content";

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}
