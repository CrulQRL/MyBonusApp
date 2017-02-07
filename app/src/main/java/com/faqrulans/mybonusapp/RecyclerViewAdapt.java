package com.faqrulans.mybonusapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by faqrulan on 1/31/17.
 */

public class RecyclerViewAdapt extends RecyclerView.Adapter<RecyclerViewAdapt.MyViewHolder> {

    private Context context;
    private FragmentManager fragmentManager;
    private LayoutInflater inflater;
    private Activity activity;

    private ArrayList<SavedHitInformation> savedHitInformations ;
    private List arsHitLeft;
    private List arsHitRight;
    private boolean inSavedImagePage;



    public RecyclerViewAdapt(List<Hit> arsHit, Activity activity, FragmentManager fragmentManager){
        inSavedImagePage = false;
        this.context = activity.getApplicationContext();
        this.fragmentManager = fragmentManager;
        this.activity = activity;
        this.arsHitLeft = new ArrayList<>();
        this.arsHitRight = new ArrayList<>();
        setArrayHit(arsHit);
        inflater = LayoutInflater.from(context);

    }

    public RecyclerViewAdapt(Activity activity, FragmentManager fragmentManager, ArrayList<SavedHitInformation> savedHitInformations){
        inSavedImagePage = true;
        this.context = activity.getApplicationContext();
        this.fragmentManager = fragmentManager;
        this.activity = activity;

        for(int i = 0 ; i < savedHitInformations.size() ; i++){
            Log.d("lol","iterasi isi array savedHitInformation.. : " + savedHitInformations.get(i).getSavedHit().getViews());
        }

        this.savedHitInformations = savedHitInformations;
        this.arsHitLeft = new ArrayList<>();
        this.arsHitRight = new ArrayList<>();
        setArrayHitFromSavedInform();


        Log.d("lol","panjang savedHitInformation : " + savedHitInformations.size());
        inflater = LayoutInflater.from(context);
    }

    /**
    public RecyclerViewAdapt(List<Hit> arsHit,Context context){
        this.context = context;
        //this.fragmentManager = context.get;
        //this.activity = activity;
        inflater = LayoutInflater.from(context);
        setArrayHit(arsHit);
    }**/

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_image,parent,false);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                ScreenWidth()/2);

        view.findViewById(R.id.rowImageLL).setLayoutParams(lp);


        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        PutImageToViewHolder(holder, position);
    }


    private void PutImageToViewHolder(final MyViewHolder holder, int position){


        int screenWidth = ScreenWidth();

        if(inSavedImagePage == false){
            Log.d("lol","Masuk if");
            Hit currentLeft = (Hit) arsHitLeft.get(position);
            Hit currentRight = (Hit) arsHitRight.get(position);
            holder.hitLeft = currentLeft;
            holder.hitRight = currentRight;

            String url1 = currentLeft.getPreviewURL();
            String url2 = currentRight.getPreviewURL();


            if (url1 != null && !url1.equals("")) {

                Glide.with(context)
                        .load(url1)
                        .asBitmap()
                        .dontAnimate()
                        .into(new SimpleTarget<Bitmap>(screenWidth / 2, screenWidth / 2) {
                            @Override
                            public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                                // Do something with bitmap here.
                                holder.imgLeft.setImageBitmap(bitmap);
                            }
                        });

            }

            if (url2 != null && !url2.equals("")) {
                Glide.with(context)
                        .load(url2)
                        .asBitmap()
                        .dontAnimate()
                        .into(new SimpleTarget<Bitmap>(screenWidth / 2, screenWidth / 2) {
                            @Override
                            public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                                // Do something with bitmap here.
                                holder.imgRight.setImageBitmap(bitmap);
                            }
                        });
            }

        }else {
            //Log.d("lol", "position : " + position + ", arsHitLeft : Sizenya " + arsHitLeft.size() +" Viewnya "+ ((SavedHitInformation)arsHitLeft.get(0)).getSavedHit().getViews());
            if(position < arsHitLeft.size()){
                SavedHitInformation currentLeft = (SavedHitInformation) arsHitLeft.get(position);
                holder.hitLeft = currentLeft.getSavedHit();
                holder.isLeftSide = true;

                if(currentLeft.getImagePreview() == null){

                    Glide.with(context)
                            .load(currentLeft.getSavedHit().getPreviewURL())
                            .asBitmap()
                            .dontAnimate()
                            .into(new SimpleTarget<Bitmap>(screenWidth / 2, screenWidth / 2) {
                                @Override
                                public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                                    // Do something with bitmap here.
                                    Log.d("lol","Left");
                                    holder.imgLeft.setImageBitmap(bitmap);
                                }
                            });

                }else{
                    holder.imgLeft.setImageBitmap(currentLeft.getImagePreview());
                }

                holder.savedHitInformationLeft = currentLeft;
            }else{
                holder.imgLeft.setBackgroundColor(Color.parseColor("#1A1A1A"));
                holder.imgLeft.invalidate();
            }

            //Log.d("lol", "position : " + position + ", arsHitRight : Sizenya " + arsHitRight.size()+" Viewnya " + ((SavedHitInformation)arsHitRight.get(0)).getSavedHit().getViews());

            if(position < arsHitRight.size()) {
                SavedHitInformation currentRight = (SavedHitInformation) arsHitRight.get(position);
                holder.hitRight = currentRight.getSavedHit();
                holder.isLeftSide = false;

                if(currentRight.getImagePreview() == null) {
                    Glide.with(context)
                            .load(currentRight.getSavedHit().getPreviewURL())
                            .asBitmap()
                            .dontAnimate()
                            .into(new SimpleTarget<Bitmap>(screenWidth / 2, screenWidth / 2) {
                                @Override
                                public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                                    // Do something with bitmap here.
                                    Log.d("lol","Right");
                                    holder.imgRight.setImageBitmap(bitmap);
                                }
                            });
                }else{
                    holder.imgRight.setImageBitmap(currentRight.getImagePreview());
                }

                holder.savedHitInformationRight = currentRight;
            }else{
                holder.imgRight.setBackgroundColor(Color.parseColor("#1A1A1A"));
                holder.imgRight.invalidate();
            }

        }

    }

    private void setArrayHit(List ars){

        for (int i = 0; i < ars.size(); i++) {

            if (i % 2 == 0) {
                arsHitLeft.add(ars.get(i));
            } else {
                arsHitRight.add(ars.get(i));
            }

        }

    }

    private void setArrayHitFromSavedInform(){

        for(int i = 0 ; i < savedHitInformations.size(); i++){

            if(i % 2 == 0){
                arsHitLeft.add(savedHitInformations.get(i));
            }else{
                arsHitRight.add(savedHitInformations.get(i));
            }

        }
    }

    @Override
    public int getItemCount() {
        return arsHitLeft.size();
    }

    private int ScreenWidth(){
        Display display = activity.getWindowManager().getDefaultDisplay();
        int stageWidth = display.getWidth();
        return stageWidth;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        SavedHitInformation savedHitInformationLeft = null;
        SavedHitInformation savedHitInformationRight = null;
        ImageView imgLeft;
        ImageView imgRight;
        Hit hitLeft;
        Hit hitRight;
        boolean isLeftSide;


        public MyViewHolder(View itemView) {

            super(itemView);

            imgLeft = (ImageView) itemView.findViewById(R.id.img1);
            imgRight = (ImageView) itemView.findViewById(R.id.img2);


            imgLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HideKeyboard();
                    hideSearchButton();
                    hideSavedItemButton();
                    ShowDialogLeft();
                }
            });

            imgRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HideKeyboard();
                    hideSearchButton();
                    hideSavedItemButton();
                    ShowDialogRight();
                }
            });

        }

        private void hideSearchButton(){
            if(activity.findViewById(R.id.action_search) != null){
                activity.findViewById(R.id.action_search).setVisibility(View.INVISIBLE);
            }
        }

        private void hideSavedItemButton(){
            if(activity.findViewById(R.id.savedItem) != null){
                activity.findViewById(R.id.savedItem).setVisibility(View.INVISIBLE);
            }
        }

        private void ShowDialogLeft(){


            HitFragment hitFragment;
            isLeftSide = true;

            if(inSavedImagePage == false) {
                if( imgLeft.getDrawable() != null){
                    hitFragment = HitFragment.newInstance(hitLeft, ((BitmapDrawable) imgLeft.getDrawable()).getBitmap());
                }else{
                    hitFragment = HitFragment.newInstance(hitLeft, null);
                }
                ReplaceWithHitDialog(hitFragment);
            }else{
                if(savedHitInformationLeft != null) {
                    if(savedHitInformationLeft.getImagePreview() == null){
                        savedHitInformationLeft.setImagePreview(((BitmapDrawable) imgLeft.getDrawable()).getBitmap());
                    }

                    hitFragment = HitFragment.newInstace(savedHitInformationLeft, getAdapterPosition(), isLeftSide);
                    ReplaceWithHitDialog(hitFragment);
                }
            }



        }

        private void ShowDialogRight(){
            HitFragment hitFragment;
            isLeftSide = false;

            if(inSavedImagePage == false) {
                if( imgRight.getDrawable() != null) {
                    hitFragment = HitFragment.newInstance(hitRight, ((BitmapDrawable) imgRight.getDrawable()).getBitmap());
                }else{
                    hitFragment = HitFragment.newInstance(hitRight, null);
                }
                ReplaceWithHitDialog(hitFragment);
            }else{
                if(savedHitInformationRight != null){
                    if(savedHitInformationRight.getImagePreview() == null){
                        savedHitInformationRight.setImagePreview(((BitmapDrawable) imgRight.getDrawable()).getBitmap());
                    }
                    hitFragment = HitFragment.newInstace(savedHitInformationRight, getAdapterPosition(), isLeftSide);
                    ReplaceWithHitDialog(hitFragment);
                }
            }


        }

        private void ReplaceWithHitDialog(HitFragment hitFragment){
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.setCustomAnimations(R.anim.fade_in,0,0,R.anim.fade_out);
            ft.replace(R.id.containerFragment, hitFragment);
            ft.addToBackStack(null);
            ft.commit();
        }

        private void HideKeyboard(){

            InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);

        }


    }







}
