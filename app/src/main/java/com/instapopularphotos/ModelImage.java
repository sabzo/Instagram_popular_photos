package com.instapopularphotos;

/**
 * Created by Sabelo on 9/10/15.
 * Instagram com.instapopularphotos.Image Model
 */
public class ModelImage {
    private String username, profilePhotoURL, imgURL, caption  = null;
    private Integer likes, imgHeight = null;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfilePhotoURL() {
        return profilePhotoURL;
    }

    public void setProfilePhotoURL(String profilePhotoURL) {
        this.profilePhotoURL = profilePhotoURL;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Integer getImgHeight() {
        return imgHeight;
    }

    public void setImgHeight(Integer imgHeight) {
        this.imgHeight = imgHeight;
    }

    public ModelImage(String username, String profilePhotoURL, String imgURL, String caption, Integer likes, Integer imgHeight) {

        this.username = username;
        this.profilePhotoURL = profilePhotoURL;
        this.imgURL = imgURL;
        this.caption = caption;
        this.likes = likes;
        this.imgHeight = imgHeight;
    }
}
