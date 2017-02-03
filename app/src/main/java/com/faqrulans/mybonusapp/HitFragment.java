package com.faqrulans.mybonusapp;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;


public class HitFragment extends Fragment {

    ImageView imageURLIV;
    ImageView userIV;
    TextView userTV;
    TextView tagsTV;
    TextView viewsTV;
    TextView likesTV;
    TextView favoritesTV;
    Button saveImageButton;
    ProgressBar loadingImagePB;

    private OnSaveButtonClickedListener mListener;

    private String webformatURL;
    private String user;
    private String userImageURL;
    private String tags;
    private String views;
    private String likes;
    private String favorites;


    public static HitFragment newInstance(Hit hit){

        HitFragment myDialogFragment = new HitFragment();
        Bundle args = new Bundle();
        args.putString("param1",hit.getWebformatURL());
        args.putString("param2",hit.getUser());
        args.putString("param3",hit.getUserImageURL());
        args.putString("param4",hit.getTags());
        args.putString("param5",hit.getViews());
        args.putString("param6",hit.getLikes());
        args.putString("param7",hit.getFavorites());

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
        userIV = (ImageView) view.findViewById(R.id.userIV);
        userTV = (TextView) view.findViewById(R.id.userTV);
        tagsTV = (TextView) view.findViewById(R.id.tagsTV);
        viewsTV = (TextView) view.findViewById(R.id.viewsTV);
        likesTV = (TextView) view.findViewById(R.id.likesTV);
        favoritesTV = (TextView) view.findViewById(R.id.favoritesTV);
        saveImageButton = (Button) view.findViewById(R.id.saveImageButton);
        loadingImagePB = (ProgressBar) view.findViewById(R.id.loadingImagePB);

        int widthScreen = getScreenWidth();
        int newButtonWidth = widthScreen/3;
        saveImageButton.getLayoutParams().width = newButtonWidth + 30;
        imageURLIV.requestLayout();

        userTV.setText(user);
        tagsTV.setText(tags);
        viewsTV.setText(views);
        likesTV.setText(likes);
        favoritesTV.setText(favorites);

        StartGLidePrecess(widthScreen);

        setFragmentListener(view);
        setSaveImageButtonListener(saveImageButton);

        return  view;
    }


    private void StartGLidePrecess(int widthScreen){

        if(webformatURL != null && !webformatURL.equals("")) {
            Glide.with(getActivity())
                    .load(webformatURL)
                    .override(widthScreen, widthScreen)
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            loadingImagePB.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(imageURLIV)
            ;
        }

        if(userImageURL != null && !userImageURL.equals("")) {
            Glide.with(getActivity())
                    .load(userImageURL)
                    .dontAnimate()
                    .into(userIV);
        }

    }

    private void setFragmentListener(View view){

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Dummy buat fragment
            }
        });

    }

    private void setSaveImageButtonListener(Button button){

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(),"Save button clicked..", Toast.LENGTH_LONG).show();
                onSaveButtonPressed(imageURLIV.getDrawable());
            }
        });

    }

    public void onSaveButtonPressed(Drawable savedImage) {
        if (mListener != null) {
            mListener.OnSaveButtonClicked(savedImage);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnSaveButtonClickedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnSaveButtonClickedListener");
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public interface OnSaveButtonClickedListener {
        public void OnSaveButtonClicked(Drawable imageSaved);
    }


}
