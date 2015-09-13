package com.instapopularphotos;

import java.util.ArrayList;

/**
 * Created by Sabelo on 9/10/15.
 * Instagram com.instapopularphotos.Image Model
 */
public class ModelImage {
    private String username, profilePhotoURL, imgURL, caption  = null;
    private Integer likes, imgHeight = null;
    private ArrayList<String> comments;

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

    public ArrayList<String> getComments() {
        return comments;
    }

    public ModelImage(String username, String profilePhotoURL, String imgURL, String caption, Integer likes, Integer imgHeight, ArrayList<String> comments) {

        this.username = username;
        this.profilePhotoURL = profilePhotoURL;
        this.imgURL = imgURL;
        this.caption = caption;
        this.likes = likes;
        this.imgHeight = imgHeight;
        this.comments = comments;
    }
}
