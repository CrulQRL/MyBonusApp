package com.faqrulans.mybonusapp;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.media.Image;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by faqrulan on 1/31/17.
 */

public class RecyclerViewAdapt extends RecyclerView.Adapter<RecyclerViewAdapt.MyViewHolder> {

    private Context context;
    private FragmentManager fragmentManager;
    private LayoutInflater inflater;

    private List<Hit> arsHitLeft;
    private List<Hit> arsHitRight;

    public RecyclerViewAdapt(Context context, List<Hit> arsHit, FragmentManager fragmentManager){
        this.context = context;
        this.fragmentManager = fragmentManager;
        inflater = LayoutInflater.from(context);
        setArrayHit(arsHit);

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_image,parent,false);
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
                    ShowDialogLeft();
                }
            });

            imgRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShowDialogRight();
                }
            });




        }

        private void ShowDialogLeft(){
            FragmentManager fm = fragmentManager;
            ImageFragmentDialog imageFragmentDialog = ImageFragmentDialog.newInstance(hitLeft.getWebformatURL(), hitLeft.getUser(), hitLeft.getUserImageURL() ,hitLeft.getTags(), hitLeft.getViews(), hitLeft.getLikes(), hitLeft.getFavorites());
            imageFragmentDialog.show(fm, "fragment_image_fragment_dialog");
        }

        private void ShowDialogRight(){
            FragmentManager fm = fragmentManager;
            ImageFragmentDialog imageFragmentDialog = ImageFragmentDialog.newInstance(hitRight.getWebformatURL(), hitRight.getUser(), hitRight.getUserImageURL() ,hitRight.getTags(), hitRight.getViews(), hitRight.getLikes(), hitRight.getFavorites());
            imageFragmentDialog.show(fm, "fragment_image_fragment_dialog");
        }

    }

}
