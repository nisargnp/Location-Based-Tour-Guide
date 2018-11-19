package edu.umd.cs.cmsc436.location_basedtourguide.Firebase.DTO;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import edu.umd.cs.cmsc436.location_basedtourguide.Util.Utils;

public class Comment implements Serializable {

    private String id;
    private String author;
    private String text;

    private DatabaseReference thisComment;

    /**
     * Don't use this constructor, this is for Firebase only.
     */
    public Comment(){}

    public static Comment createComment() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference firebaseComments = database.getReference("Comments");

        Comment comment = new Comment("","","");
        comment.id = firebaseComments.push().getKey();
        comment.thisComment = firebaseComments.child(comment.id);
        comment.setId(comment.id);
        comment.thisComment.setValue(comment);
        return comment;
    }

    private Comment(String id, String author, String text) {
        this.id = id;
        this.author = author;
        this.text = text;
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
        this.id = id;
        thisComment.updateChildren(Utils.generatePair("id", id));
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
        thisComment.updateChildren(Utils.generatePair("author", author));
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        thisComment.updateChildren(Utils.generatePair("text", text));
    }

}
