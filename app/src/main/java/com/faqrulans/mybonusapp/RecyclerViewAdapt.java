package com.faqrulans.mybonusapp;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by faqrulan on 1/31/17.
 */

public class RecyclerViewAdapt extends RecyclerView.Adapter<RecyclerViewAdapt.MyViewHolder> {

    private Context context;
    private FragmentManager fragmentManager;
    private LayoutInflater inflater;
    private AppCompatActivity activity;


    private List<Hit> arsHitLeft;
    private List<Hit> arsHitRight;

    public RecyclerViewAdapt(List<Hit> arsHit,AppCompatActivity activity){
        this.context = context = activity.getApplicationContext();
        this.fragmentManager = activity.getSupportFragmentManager();
        this.activity = activity;
        inflater = LayoutInflater.from(context);
        setArrayHit(arsHit);

    }

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
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Hit currentLeft = arsHitLeft.get(position);
        Hit currentRight = arsHitRight.get(position);


        String url1 = currentLeft.getPreviewURL();
        String url2 = currentRight.getPreviewURL();

        holder.hitLeft = currentLeft;
        holder.hitRight = currentRight;

        /**
        Picasso.with(context)
                .load(url1)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(holder.imgLeft);

        Picasso.with(context)
                .load(url2)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(holder.imgRight);
        **/

        int screenWidth = ScreenWidth();

        Glide
                .with(context)
                .load(url1)
                .override(screenWidth/2,screenWidth/2)
                .into(holder.imgLeft);

        Glide
                .with(context)
                .load(url2)
                .override(screenWidth/2,screenWidth/2)
                .into(holder.imgRight);

    }

    private void setArrayHit(List<Hit> arsHit){

        arsHitLeft = new ArrayList<>();
        arsHitRight = new ArrayList<>();

        for(int i = 0 ; i < arsHit.size() ; i++){

            if(i % 2 == 0){
                arsHitLeft.add(arsHit.get(i));
            }else{
                arsHitRight.add(arsHit.get(i));
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
            //FragmentManager fm = fragmentManager;

            HitFragment hitFragment = HitFragment.newInstance(hitLeft.getWebformatURL(), hitLeft.getUser(), hitLeft.getUserImageURL() ,hitLeft.getTags(), hitLeft.getViews(), hitLeft.getLikes(), hitLeft.getFavorites(), ScreenWidth());
            //hitFragment.show(fm, "fragment_hit");

            //FragmentTransaction ft = fragmentManager.beginTransaction();
            //ft.show(hitFragment);
            //ft.commit();
            //Log.d("lol","Masuk...");

            //fragmentManager.beginTransaction()
             //       .setCustomAnimations(R.anim.fade_in,R.anim.fade_out)
             //       .show(hitFragment)
             //       .commit();



           // FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            //fragmentTransaction.add(R.id.activity_main, hitFragment, "HELLO");
            //fragmentTransaction.commit();
            //Log.d("lol","Masuk...");

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
            //FragmentManager fm = fragmentManager;
            Fragment imageFragmentDialog = HitFragment.newInstance(hitRight.getWebformatURL(), hitRight.getUser(), hitRight.getUserImageURL() ,hitRight.getTags(), hitRight.getViews(), hitRight.getLikes(), hitRight.getFavorites(), ScreenWidth());
            //imageFragmentDialog.show(fm, "fragment_hit");
            //FragmentTransaction ft = fragmentManager.beginTransaction();
            //ft.show(imageFragmentDialog);
            //ft.commit();

            //FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            //fragmentTransaction.add(R.id.activity_main, imageFragmentDialog, "HELLO");
            //fragmentTransaction.commit();
            //Log.d("lol","Masuk...");

            if(fragmentManager.getBackStackEntryCount() == 0) {

                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.setCustomAnimations(R.anim.fade_in,0,0,R.anim.fade_out);
                ft.replace(R.id.containerFragment, imageFragmentDialog);
                ft.addToBackStack(null);
                ft.commit();

            }else if(fragmentManager.getBackStackEntryCount() == 1){
                fragmentManager.popBackStack();
            }

        }

    }







}
