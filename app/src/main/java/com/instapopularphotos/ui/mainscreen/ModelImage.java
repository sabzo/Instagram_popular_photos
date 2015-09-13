package com.instapopularphotos.ui.mainscreen;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Sabelo on 9/10/15.
 * Instagram com.instapopularphotos.Image Model
 */
public class ModelImage {
    public class Comment {

        private String username, commentString = null;
        public Comment(JSONObject comment) {
            try{
                username =  comment.getJSONObject("from").getString("username");
                this.commentString = comment.getString("text");
            } catch (JSONException e) {
                Log.i("Error", e.toString());
            }
        }

        public String getUsername() {
            return username;
        }

        public String getCommentString() {
            return commentString;
        }

    }
    private String username, profilePhotoURL, imgURL, caption  = null;
    private Integer likes, imgHeight = null;
    private ArrayList<Comment> comments;



    /* In case a User must be created manually */
    public ModelImage(String username, String profilePhotoURL, String imgURL, String caption, Integer likes, Integer imgHeight, ArrayList<Comment> comments) {

        this.username = username;
        this.profilePhotoURL = profilePhotoURL;
        this.imgURL = imgURL;
        this.caption = caption;
        this.likes = likes;
        this.imgHeight = imgHeight;
        this.comments = comments;
    }

    /* Port JSON user to Java Object */
    public ModelImage(JSONObject photo) {
        caption = ""; // default caption to empty string
        try {
            if (photo.optJSONObject("caption") != null) {
                caption = photo.getJSONObject("caption").getString("text");
            }
            this.imgURL = photo.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
            this.profilePhotoURL = photo.getJSONObject("user").getString("profile_picture");
            this.username = photo.getJSONObject("user").getString("username");
            this.imgHeight = photo.getJSONObject("images").getJSONObject("standard_resolution").getInt("height");
            this.likes = photo.getJSONObject("likes").getInt("count");
            JSONArray jsonComments = photo.getJSONObject("comments").getJSONArray("data");
            Integer count = photo.getJSONObject("comments").getInt("count");

            comments = new ArrayList<Comment>();

            // Let's retrieve last two comments
            if( count >= 2) {
                for(int j = 1; j <= 2; j++) {
                    Comment comment = new Comment(jsonComments.getJSONObject(j));
                    comments.add(comment);
                }
            } else if(count == 1) {
                comments.add( new Comment(jsonComments.getJSONObject(0)));
            }
        } catch (JSONException e) {
            Log.i("Error", e.toString());
        }
    }

    public String getUsername() {
        return username;
    }

    public String getProfilePhotoURL() {
        return profilePhotoURL;
    }

    public String getImgURL() {
        return imgURL;
    }

    public String getCaption() {
        return caption;
    }

    public Integer getLikes() {
        return likes;
    }

    public Integer getImgHeight() {
        return imgHeight;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }
}
