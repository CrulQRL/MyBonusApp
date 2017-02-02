package com.faqrulans.mybonusapp;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;


public class HitFragment extends Fragment {

    ImageView imageURLIV;
    private String webformatURL;
    private String user;
    private String userImageURL;
    private String tags;
    private String views;
    private String likes;
    private String favorites;

    public static HitFragment newInstance(String webformatURL, String user, String userImageURL, String tags, String views, String likes, String favorites){

        HitFragment myDialogFragment = new HitFragment();
        Bundle args = new Bundle();
        args.putString("param1",webformatURL);
        args.putString("param2",user);
        args.putString("param3",userImageURL);
        args.putString("param4",tags);
        args.putString("param5",views);
        args.putString("param6",likes);
        args.putString("param6",favorites);

        myDialogFragment.setArguments(args);
        return myDialogFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            webformatURL = getArguments().getString("param1");
            user = getArguments().getString("param2");
            userImageURL = getArguments().getString("param3");
            tags = getArguments().getString("param4");
            views = getArguments().getString("param5");
            likes = getArguments().getString("param6");
            favorites = getArguments().getString("param7");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hit, container, false);


        imageURLIV = (ImageView) view.findViewById(R.id.imageURLTV);
        //getDialog().setTitle("Details");

        /**
        Picasso.with(getActivity())
                .load(webformatURL)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(imageURLIV);
         **/

        Glide.with(getActivity())
                .load(webformatURL)
                .fitCenter()
                .into(imageURLIV);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aaa();
            }
        });

        return  view;
    }

    public void aaa(){
        Log.d("lol","masuk aaa..");
    }

    public int getScreenWidth1() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }




}
