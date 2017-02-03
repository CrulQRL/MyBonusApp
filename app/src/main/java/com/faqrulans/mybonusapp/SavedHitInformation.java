package com.faqrulans.mybonusapp;

import android.graphics.drawable.Drawable;

/**
 * Created by faqrulan on 2/3/17.
 */

public class SavedHitInformation {

    private Hit savedHit;
    private Drawable imageURLIV;
    private Drawable userIV;

    public SavedHitInformation(Hit savedHit, Drawable imageURLIV, Drawable userIV) {
        this.savedHit = savedHit;
        this.imageURLIV = imageURLIV;
        this.userIV = userIV;
    }

    public Hit getSavedHit(){
        return savedHit;
    }

    public Drawable getImageURLIV() {
        return imageURLIV;
    }

    public Drawable getUserIV() {
        return userIV;
    }
}
