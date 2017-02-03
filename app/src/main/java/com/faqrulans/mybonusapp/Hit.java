package com.faqrulans.mybonusapp;

import java.io.Serializable;

/**
 * Created by faqrulan on 1/30/17.
 */

public class Hit implements Serializable {

    private String webformatURL;
    private String previewURL;
    private String user;
    private String userImageURL;
    private String tags;
    private String views;
    private String likes;
    private String favorites;

    public Hit(){}


    public Hit(String webformatURL, String previewURL, String user, String userImageURL, String tags, String views, String likes, String favorites) {
        this.webformatURL = webformatURL;
        this.previewURL = previewURL;
        this.user = user;
        this.userImageURL = userImageURL;
        this.tags = tags;
        this.views = views;
        this.likes = likes;
        this.favorites = favorites;
    }

    public void setWebformatURL(String webformatURL) {
        this.webformatURL = webformatURL;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setUserImageURL(String userImageURL) {
        this.userImageURL = userImageURL;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public void setViews(String views) {
        this.views = views;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public void setFavorites(String favorites) {
        this.favorites = favorites;
    }

    public String getPreviewURL() {
        return previewURL;
    }

    public void setPreviewURL(String previewURL) {
        this.previewURL = previewURL;
    }

    public String getWebformatURL() {
        return webformatURL;

    }

    public String getUser() {
        return user;
    }

    public String getUserImageURL() {
        return userImageURL;
    }

    public String getTags() {
        return tags;
    }

    public String getViews() {
        return views;
    }

    public String getLikes() {
        return likes;
    }

    public String getFavorites() {
        return favorites;
    }
}
