package com.faqrulans.mybonusapp;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
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
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;

import java.io.File;
import java.io.FileOutputStream;


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

    private Hit hit;
    private String webformatURL;
    private String user;
    private String userImageURL;
    private String tags;
    private String views;
    private String likes;
    private String favorites;
    private Bitmap imagePreviewBitmap;
    private Bitmap imageURLBitmap;
    private Bitmap imageUserBitmap;
    private SavedHitInformation savedHitInformation;


    public static HitFragment newInstance(Hit hit, Bitmap previewImageBitmap){

        HitFragment myDialogFragment = new HitFragment();
        Bundle args = new Bundle();
        args.putSerializable("param1", hit);
        args.putString("param2",hit.getWebformatURL());
        args.putString("param3",hit.getUser());
        args.putString("param4",hit.getUserImageURL());
        args.putString("param5",hit.getTags());
        args.putString("param6",hit.getViews());
        args.putString("param7",hit.getLikes());
        args.putString("param8",hit.getFavorites());
        args.putParcelable("param9", previewImageBitmap);


        myDialogFragment.setArguments(args);
        return myDialogFragment;
    }

    public static HitFragment newInstace(SavedHitInformation savedHitInformation){

        HitFragment myDialogFragment = new HitFragment();
        Bundle args = new Bundle();

        args.putParcelable("sHI",savedHitInformation);

        myDialogFragment.setArguments(args);
        return myDialogFragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {

            if(getArguments().getParcelable("sHI") == null) {
                hit = (Hit) getArguments().getSerializable("param1");
                webformatURL = getArguments().getString("param2");
                user = getArguments().getString("param3");
                userImageURL = getArguments().getString("param4");
                tags = getArguments().getString("param5");
                views = getArguments().getString("param6");
                likes = getArguments().getString("param7");
                favorites = getArguments().getString("param8");
                imagePreviewBitmap = getArguments().getParcelable("param9");
            }else{
                savedHitInformation = getArguments().getParcelable("sHI");
            }

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

        if(savedHitInformation == null){
            Log.d("lol", "Hit informationnya null");
            userTV.setText(user);
            tagsTV.setText(tags);
            viewsTV.setText(views);
            likesTV.setText(likes);
            favoritesTV.setText(favorites);

            StartGLidePrecess(widthScreen);
            setFragmentListener(view);
        }else{
            LoadFromSavedInfo();
        }


        return  view;
    }


    private void StartGLidePrecess(int widthScreen){

        if(webformatURL != null && !webformatURL.equals("")) {

            /**
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
            ;**/

            Glide.with(getActivity())
                    .load(webformatURL)
                    .asBitmap()
                    .into(new SimpleTarget<Bitmap>(widthScreen, widthScreen) {
                        @Override
                        public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                            // Do something with bitmap here.
                            loadingImagePB.setVisibility(View.GONE);
                            imageURLBitmap = bitmap;
                            imageURLIV.setImageBitmap(bitmap);
                            saveImageButton.setAlpha(1);
                            setSaveImageButtonListener();
                        }
                    });
        }

        if(userImageURL != null && !userImageURL.equals("")) {

            Glide.with(getActivity())
                    .load(userImageURL)
                    .asBitmap()
                    .dontAnimate()
                    .into(new SimpleTarget<Bitmap>(widthScreen, widthScreen) {
                        @Override
                        public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                            // Do something with bitmap here.
                            imageUserBitmap = bitmap;
                            userIV.setImageBitmap(bitmap);
                        }
                    });
        }

    }

    private void LoadFromSavedInfo(){
        saveImageButton.setText("Download");
        setDownloadImageButtonListener();
        loadingImagePB.setVisibility(View.GONE);
        userTV.setText(savedHitInformation.getSavedHit().getUser());
        tagsTV.setText(savedHitInformation.getSavedHit().getTags());
        viewsTV.setText(savedHitInformation.getSavedHit().getViews());
        likesTV.setText(savedHitInformation.getSavedHit().getLikes());
        favoritesTV.setText(savedHitInformation.getSavedHit().getFavorites());
        imageURLIV.setImageBitmap(savedHitInformation.getImageURLIV());
        userIV.setImageBitmap(savedHitInformation.getUserIV());
        saveImageButton.setAlpha(1);

    }

    private void setFragmentListener(View view){

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Dummy buat fragment
            }
        });

    }

    private void setSaveImageButtonListener(){

        saveImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Saved", Toast.LENGTH_SHORT).show();
                onSaveButtonPressed(imageURLBitmap, imageUserBitmap);

            }
        });

    }

    private void setDownloadImageButtonListener(){

        saveImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DownloadAsyncTask().execute();
            }
        });

    }

    public void onSaveButtonPressed(Bitmap savedImageURL, Bitmap savedUserImage) {

        if (mListener != null) {
            SavedHitInformation savedHitInformation = new SavedHitInformation(hit, savedImageURL, imagePreviewBitmap ,savedUserImage);
            mListener.OnSaveButtonClicked(savedHitInformation);
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


    private void SaveImageToSDCard(){

        Bitmap bitmap = savedHitInformation.getImageURLIV();

        FileOutputStream outStream = null;
        File sdCard = Environment.getExternalStorageDirectory();
        File dir = new File(sdCard.getAbsolutePath() + "/MyBonusAppImage");
        if(!dir.exists()){
            dir.mkdirs();
        }
        String fileName = String.format("%d.jpg", System.currentTimeMillis());
        File outFile = new File(dir, fileName);

        try {
            outStream = new FileOutputStream(outFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
            outStream.flush();
            outStream.close();
        }catch(Exception e){
            Toast.makeText(getContext(),"Download failed", Toast.LENGTH_LONG).show();
            e.printStackTrace();
            Log.d("lol","download failed "+ e );
        }
    }

    class DownloadAsyncTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... params) {
            SaveImageToSDCard();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(getContext(),"Download finished",Toast.LENGTH_SHORT).show();
        }
    }

    public int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public interface OnSaveButtonClickedListener {
        public void OnSaveButtonClicked(SavedHitInformation savedHitInformation);
    }


}
