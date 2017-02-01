package com.faqrulans.mybonusapp;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;


public class ImageFragmentDialog extends DialogFragment {

    ImageView imageURLIV;
    private String webformatURL;
    private String user;
    private String userImageURL;
    private String tags;
    private String views;
    private String likes;
    private String favorites;

    public static ImageFragmentDialog newInstance(String webformatURL, String user, String userImageURL, String tags, String views, String likes, String favorites){

        ImageFragmentDialog myDialogFragment = new ImageFragmentDialog();
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
        View view = inflater.inflate(R.layout.fragment_image_fragment_dialog, container, false);

        imageURLIV = (ImageView) view.findViewById(R.id.imageURLTV);
        getDialog().setTitle("Details");

        Picasso.with(getActivity())
                .load(webformatURL)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(imageURLIV);

        return  view;
    }


}
