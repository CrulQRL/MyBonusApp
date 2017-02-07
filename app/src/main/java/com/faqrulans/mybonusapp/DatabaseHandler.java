package com.faqrulans.mybonusapp;


import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.content.ContentValues;
import android.os.Debug;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by faqrulan on 2/6/17.
 */


public class DatabaseHandler extends SQLiteOpenHelper {



    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "savedhit.db";
    private static final String TABLE_NAME = "savedhit";
    private static final String COLUMNN_ID = "_id";
    private static final String COLUMNN_WEB_FORMAT_URL = "webFormatURL";
    private static final String COLUMNN_PREVIEW_URL = "previewURL";
    private static final String COLUMNN_USER = "user";
    private static final String COLUMNN_USER_IMAGE_URL = "userImageURL";
    private static final String COLUMNN_TAGS = "tags";
    private static final String COLUMNN_VIEWS = "views";
    private static final String COLUMNN_LIKES = "likes";
    private static final String COLUMNN_FAVORITE = "favorite";


    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("lol","onCreate Database");
        String query = "CREATE TABLE " + TABLE_NAME + "(" +
                COLUMNN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMNN_WEB_FORMAT_URL + " TEXT, " +
                COLUMNN_PREVIEW_URL + " TEXT, " +
                COLUMNN_USER + " TEXT, " +
                COLUMNN_USER_IMAGE_URL + " TEXT, " +
                COLUMNN_TAGS + " TEXT, " +
                COLUMNN_VIEWS + " TEXT, " +
                COLUMNN_LIKES + " TEXT, " +
                COLUMNN_FAVORITE + " TEXT" +
                ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    public void addHit(Hit hit){
        ContentValues values = new ContentValues();
        values.put(COLUMNN_WEB_FORMAT_URL, hit.getWebformatURL());
        values.put(COLUMNN_PREVIEW_URL, hit.getPreviewURL());
        values.put(COLUMNN_USER, hit.getUser());
        values.put(COLUMNN_USER_IMAGE_URL, hit.getUserImageURL());
        values.put(COLUMNN_TAGS, hit.getTags());
        values.put(COLUMNN_VIEWS, hit.getViews());
        values.put(COLUMNN_LIKES, hit.getLikes());
        values.put(COLUMNN_FAVORITE, hit.getFavorites());

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_NAME,null,values);
        Log.d("lol","Nge add ke database");
        db.close();

    }


    public void addHitFromArrayList(ArrayList<SavedHitInformation> arrays){
        int jum = arrays.size();
        if(jum > 0){

            for(int i = 0 ; i < jum ; i++){
                addHit(arrays.get(i).getSavedHit());
            }

        }
    }

    /**
    public Cursor sortDatabseByName(){

        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " ORDER BY "+ COLUMNN_USER +" ASC";
        Cursor c = db.rawQuery(query,null);

        return c;

    }

    public ArrayList<String> DatabaseNamaToArrList(){
        ArrayList<String> ars = new ArrayList<String>();
        SQLiteDatabase db = getWritableDatabase();

        Cursor c = sortDatabseByName();

        c.moveToFirst();


        while(!c.isAfterLast()){
            Log.d("lol","masuk2" );

            if(c.getString(c.getColumnIndex("user"))!= null){
                ars.add(c.getString(c.getColumnIndex("user")));
            }
            c.moveToNext();
        }
        db.close();
        return ars;
    }
**/
    /**
    public Cursor sortDatabseByName(){

        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " ORDER BY "+ COLUMNN_NAMA +" ASC";
        Cursor c = db.rawQuery(query,null);

        return c;

    }
**/

    public void deleteHit(String imageWebURL){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE " + COLUMNN_WEB_FORMAT_URL + "=\"" + imageWebURL + "\";" );
        db.close();
    }

    public Cursor getCursor(){
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor c = db.rawQuery(query,null);

        return c;
    }
/**
    public ArrayList<Hit> getInfoArrayDatabase(){

        ArrayList<Hit> a = new ArrayList<>();

        SQLiteDatabase db = getWritableDatabase();

        Cursor c = getCursor();


        c.moveToFirst();
        Hit hit;

        Log.d("lol","masuk1" );
        Log.d("lol","c.isAfterLast : " + c.isAfterLast() + " , c.moveToNext : " + c.moveToNext());
        while(!c.isAfterLast()){
            Log.d("lol","masuk2" );

            hit = new Hit();

            if(c.getString(c.getColumnIndex(COLUMNN_WEB_FORMAT_URL))!= null){
                hit.setWebformatURL((c.getString(c.getColumnIndex(COLUMNN_WEB_FORMAT_URL))));
            }

            if(c.getString(c.getColumnIndex(COLUMNN_PREVIEW_URL))!= null){
                hit.setPreviewURL(c.getString(c.getColumnIndex(COLUMNN_PREVIEW_URL)));
            }

            if(c.getString(c.getColumnIndex(COLUMNN_USER))!= null){
                hit.setUser(c.getString(c.getColumnIndex(COLUMNN_USER)));
            }

            if(c.getString(c.getColumnIndex(COLUMNN_USER_IMAGE_URL))!= null){
                hit.setUserImageURL(c.getString(c.getColumnIndex(COLUMNN_USER_IMAGE_URL)));
            }

            if(c.getString(c.getColumnIndex(COLUMNN_TAGS))!= null){
                hit.setTags(c.getString(c.getColumnIndex(COLUMNN_TAGS)));
            }

            if(c.getString(c.getColumnIndex(COLUMNN_VIEWS))!= null){
                hit.setViews(c.getString(c.getColumnIndex(COLUMNN_VIEWS)));
            }

            if(c.getString(c.getColumnIndex(COLUMNN_LIKES))!= null){
                hit.setLikes(c.getString(c.getColumnIndex(COLUMNN_LIKES)));
            }

            if(c.getString(c.getColumnIndex(COLUMNN_FAVORITE))!= null){
                hit.setFavorites(c.getString(c.getColumnIndex(COLUMNN_FAVORITE)));
            }
            a.add(hit);
            c.moveToNext();
        }

        db.close();
        return a;

    }**/

    public ArrayList<SavedHitInformation> LoadFromDatabase(ArrayList<SavedHitInformation> arrayList){

        ArrayList<SavedHitInformation> ars = arrayList;
        SavedHitInformation savedHit;

        SQLiteDatabase db = getWritableDatabase();

        Cursor c = getCursor();

        c.moveToFirst();
        Hit hit;

        while(!c.isAfterLast()){
            savedHit = new SavedHitInformation();
            hit = new Hit();

            if(c.getString(c.getColumnIndex(COLUMNN_WEB_FORMAT_URL))!= null){
                hit.setWebformatURL((c.getString(c.getColumnIndex(COLUMNN_WEB_FORMAT_URL))));
            }

            if(c.getString(c.getColumnIndex(COLUMNN_PREVIEW_URL))!= null){
                hit.setPreviewURL(c.getString(c.getColumnIndex(COLUMNN_PREVIEW_URL)));
            }

            if(c.getString(c.getColumnIndex(COLUMNN_USER))!= null){
                hit.setUser(c.getString(c.getColumnIndex(COLUMNN_USER)));
            }

            if(c.getString(c.getColumnIndex(COLUMNN_USER_IMAGE_URL))!= null){
                hit.setUserImageURL(c.getString(c.getColumnIndex(COLUMNN_USER_IMAGE_URL)));
            }

            if(c.getString(c.getColumnIndex(COLUMNN_TAGS))!= null){
                hit.setTags(c.getString(c.getColumnIndex(COLUMNN_TAGS)));
            }

            if(c.getString(c.getColumnIndex(COLUMNN_VIEWS))!= null){
                hit.setViews(c.getString(c.getColumnIndex(COLUMNN_VIEWS)));
            }

            if(c.getString(c.getColumnIndex(COLUMNN_LIKES))!= null){
                hit.setLikes(c.getString(c.getColumnIndex(COLUMNN_LIKES)));
            }

            if(c.getString(c.getColumnIndex(COLUMNN_FAVORITE))!= null){
                hit.setFavorites(c.getString(c.getColumnIndex(COLUMNN_FAVORITE)));
            }
            savedHit.setSavedHit(hit);
            ars.add(savedHit);
            c.moveToNext();
        }

        db.close();
        return ars;
    }




}
