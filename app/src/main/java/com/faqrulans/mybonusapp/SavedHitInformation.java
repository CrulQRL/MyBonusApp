package com.faqrulans.mybonusapp;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by faqrulan on 2/3/17.
 */

public class SavedHitInformation implements Parcelable {


    private Hit savedHit;
    private Bitmap imagePreview;
    private Bitmap imageURLIV;
    private Bitmap userIV;


    public SavedHitInformation(Hit savedHit, Bitmap imageURLIV, Bitmap imagePreview, Bitmap userIV) {
        this.savedHit = savedHit;
        this.imagePreview = imagePreview;
        this.imageURLIV = imageURLIV;
        this.userIV = userIV;
    }

    public Bitmap getImagePreview() {
        return imagePreview;
    }

    public Bitmap getImageURLIV() {
        return imageURLIV;
    }

    public Bitmap getUserIV() {
        return userIV;
    }

    public Hit getSavedHit() {

        return savedHit;
    }

    protected SavedHitInformation(Parcel in) {
        savedHit = (Hit) in.readValue(Hit.class.getClassLoader());
        imagePreview = (Bitmap) in.readValue(Bitmap.class.getClassLoader());
        imageURLIV = (Bitmap) in.readValue(Bitmap.class.getClassLoader());
        userIV = (Bitmap) in.readValue(Bitmap.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(savedHit);
        dest.writeParcelable(imagePreview,flags);
        dest.writeParcelable(imageURLIV,flags);
        dest.writeParcelable(userIV,flags);
    }

    public static final Parcelable.Creator<SavedHitInformation> CREATOR = new Parcelable.Creator<SavedHitInformation>() {
        @Override
        public SavedHitInformation createFromParcel(Parcel in) {
            return new SavedHitInformation(in);
        }

        @Override
        public SavedHitInformation[] newArray(int size) {
            return new SavedHitInformation[size];
        }
    };

}
