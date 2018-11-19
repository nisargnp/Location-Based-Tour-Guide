package edu.umd.cs.cmsc436.location_basedtourguide.Firebase.DTO;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Comment implements Serializable {

    private String id;
    private String author;
    private String text;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference firebaseComments = database.getReference("Comments");//.child("Tours");//is this the right ref?
    private DatabaseReference thisComment;

    /**
     * Don't use this constructor, this is for Firebase only.
     */
    public Comment(){}

    public Comment(String author){
        this(null,author,null);
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
        setId(c.getId());
        setAuthor(c.getAuthor());
        setText(c.getText());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        Map<String, Object> ups = new HashMap<>();
        ups.put(this.id,id);
        firebaseComments.updateChildren(ups);
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        Map<String, Object> ups = new HashMap<>();
        ups.put("author",author);
        firebaseComments.updateChildren(ups);
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        Map<String, Object> ups = new HashMap<>();
        ups.put("text",text);
        firebaseComments.updateChildren(ups);
        this.text = text;
    }

}
