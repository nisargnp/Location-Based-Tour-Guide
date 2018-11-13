package edu.umd.cs.cmsc436.location_basedtourguide.Firebase.DTO;

import java.io.Serializable;
import java.util.List;

public class Comment implements Serializable {

    private String id;
    private String author;
    private String text;

    public void updateComment(Comment c){
        setAuthor(c.getAuthor());
        setText(c.getText());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
