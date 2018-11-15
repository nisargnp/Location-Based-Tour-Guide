package edu.umd.cs.cmsc436.location_basedtourguide.Firebase.DTO;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.List;

public class Comment implements Serializable {

    private String id;
    private String author;
    private String text;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference firebaseComments = database.getReference("Comments");//.child("Tours");//is this the right ref?
    private DatabaseReference thisComment;

    public Comment(){

    }
    public Comment(int n){
        this(null,null,null);
        id = firebaseComments.push().getKey();
        thisComment = firebaseComments.child(id);
        setId(id);
        thisComment.setValue(this);
    }
    public Comment(String id, String Author, String Text){
        this.id = id;
        this.author = Author;
        this.text = Text;
    }


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
