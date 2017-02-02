package com.faqrulans.mybonusapp;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


public class HitFragment extends Fragment {

    ImageView imageURLIV;

    ImageView userIV;
    TextView userTV;
    TextView tagsTV;
    TextView viewsTV;
    TextView likesTV;
    TextView favoritesTV;

    private String webformatURL;
    private String user;
    private String userImageURL;
    private String tags;
    private String views;
    private String likes;
    private String favorites;
    private  int heightImage;

    public static HitFragment newInstance(String webformatURL, String user, String userImageURL, String tags, String views, String likes, String favorites,int heightImage){

        HitFragment myDialogFragment = new HitFragment();
        Bundle args = new Bundle();
        args.putString("param1",webformatURL);
        args.putString("param2",user);
        args.putString("param3",userImageURL);
        args.putString("param4",tags);
        args.putString("param5",views);
        args.putString("param6",likes);
        args.putString("param7",favorites);
        args.putInt("param8",heightImage);

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
            heightImage = getArguments().getInt("param8");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hit, container, false);


        imageURLIV = (ImageView) view.findViewById(R.id.imageURLTV);

        userIV = (ImageView) view.findViewById(R.id.userIV);
        userTV = (TextView) view.findViewById(R.id.userTV);
        tagsTV = (TextView) view.findViewById(R.id.tagsTV);
        viewsTV = (TextView) view.findViewById(R.id.viewsTV);
        likesTV = (TextView) view.findViewById(R.id.likesTV);
        favoritesTV = (TextView) view.findViewById(R.id.favoritesTV);

        userTV.setText(user);
        tagsTV.setText(tags);
        viewsTV.setText(views);
        likesTV.setText(likes);
        favoritesTV.setText(favorites);

        //imageURLIV.getLayoutParams().height = heightImage;
        //imageURLIV.requestLayout();

        /**
        Picasso.with(getActivity())
                .load(webformatURL)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(imageURLIV);
         **/
        int widthScreen = getScreenWidth();
        Glide.with(getActivity())
                .load(webformatURL)
                //.fitCenter()
                .override(widthScreen,widthScreen)
                .into(imageURLIV);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Dummy buat fragment
            }
        });

        return  view;
    }




    public int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }




}
