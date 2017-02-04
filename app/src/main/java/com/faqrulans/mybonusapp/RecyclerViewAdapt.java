package com.faqrulans.mybonusapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

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
    private Bitmap previewImageBitmap;


    private ArrayList<SavedHitInformation> savedHitInformations ;
    private List arsHitLeft;
    private List arsHitRight;

    public RecyclerViewAdapt(List<Hit> arsHit,Activity activity, FragmentManager fragmentManager){
        this.context = activity.getApplicationContext();
        this.fragmentManager = fragmentManager;
        this.activity = activity;
        inflater = LayoutInflater.from(context);
        setArrayHit(arsHit);
    }

    public RecyclerViewAdapt(Activity activity, FragmentManager fragmentManager, ArrayList<SavedHitInformation> savedHitInformations){
        this.context = activity.getApplicationContext();
        this.fragmentManager = fragmentManager;
        this.activity = activity;
        this.savedHitInformations = savedHitInformations;
        Log.d("lol","panjang savedHitInformation : " + savedHitInformations.size());
        inflater = LayoutInflater.from(context);
        setArrayHit(savedHitInformations);
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



        if(savedHitInformations == null){
            Log.d("lol","Masuk if");
            Hit currentLeft = (Hit) arsHitLeft.get(position);
            Hit currentRight = (Hit) arsHitRight.get(position);
            holder.hitLeft = currentLeft;
            holder.hitRight = currentRight;

            String url1 = currentLeft.getPreviewURL();
            String url2 = currentRight.getPreviewURL();

            int screenWidth = ScreenWidth();

            if (url1 != null && !url1.equals("")) {

                Glide.with(context)
                        .load(url1)
                        .asBitmap()
                        .dontAnimate()
                        .into(new SimpleTarget<Bitmap>(screenWidth / 2, screenWidth / 2) {
                            @Override
                            public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                                // Do something with bitmap here.
                                previewImageBitmap = bitmap;
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
                                previewImageBitmap = bitmap;
                                holder.imgRight.setImageBitmap(bitmap);
                            }
                        });
            }

        }else {
            Log.d("lol","Masuk else");

            if(position < arsHitLeft.size()){
                SavedHitInformation currentLeft = (SavedHitInformation) arsHitLeft.get(position);
                holder.hitLeft = currentLeft.getSavedHit();
                holder.imgLeft.setImageBitmap(currentLeft.getImagePreview());
            }

            if(position < arsHitRight.size()) {
                SavedHitInformation currentRight = (SavedHitInformation) arsHitRight.get(position);
                holder.hitRight = currentRight.getSavedHit();
                holder.imgRight.setImageBitmap(currentRight.getImagePreview());
            }

        }

    }

    private void setArrayHit(List ars){

        arsHitLeft = new ArrayList<>();
        arsHitRight = new ArrayList<>();

        if(savedHitInformations == null) {
            Log.d("lol","Masuk if_V2");


            for (int i = 0; i < ars.size(); i++) {

                if (i % 2 == 0) {
                    arsHitLeft.add(ars.get(i));
                } else {
                    arsHitRight.add(ars.get(i));
                }

            }
        }else{
            Log.d("lol","Masuk else_V2");

            for(int i = 0 ; i < savedHitInformations.size(); i++){

                if(i % 2 == 0){
                    arsHitLeft.add(savedHitInformations.get(i));
                }else{
                    arsHitLeft.add(savedHitInformations.get(i));
                }

            }

        }

    }

    @Override
    public int getItemCount() {
        return arsHitLeft.size();
    }

    private void HideKeyboard(){

        InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);

    }

    private int ScreenWidth(){
        Display display = activity.getWindowManager().getDefaultDisplay();
        int stageWidth = display.getWidth();
        return stageWidth;
    }



    class MyViewHolder extends RecyclerView.ViewHolder{


        ImageView imgLeft;
        ImageView imgRight;
        Hit hitLeft;
        Hit hitRight;


        public MyViewHolder(View itemView) {

            super(itemView);

            imgLeft = (ImageView) itemView.findViewById(R.id.img1);
            imgRight = (ImageView) itemView.findViewById(R.id.img2);


            imgLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.findViewById(R.id.action_search).setVisibility(View.INVISIBLE);
                    ShowDialogLeft();
                }
            });

            imgRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.findViewById(R.id.action_search).setVisibility(View.INVISIBLE);
                    ShowDialogRight();
                }
            });


        }

        private void ShowDialogLeft(){

            HitFragment hitFragment = HitFragment.newInstance(hitLeft, previewImageBitmap);

            if(fragmentManager.getBackStackEntryCount() == 0) {

                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.setCustomAnimations(R.anim.fade_in,0,0,R.anim.fade_out);
                ft.replace(R.id.containerFragment, hitFragment);
                ft.addToBackStack(null);
                ft.commit();

            }else if(fragmentManager.getBackStackEntryCount() == 1){
                fragmentManager.popBackStack();
            }

        }

        private void ShowDialogRight(){

            HitFragment hitFragment = HitFragment.newInstance(hitRight, previewImageBitmap);

            if(fragmentManager.getBackStackEntryCount() == 0) {

                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.setCustomAnimations(R.anim.fade_in,0,0,R.anim.fade_out);
                ft.replace(R.id.containerFragment, hitFragment);
                ft.addToBackStack(null);
                ft.commit();

            }else if(fragmentManager.getBackStackEntryCount() == 1){
                fragmentManager.popBackStack();
            }

        }

    }







}
