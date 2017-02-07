package com.faqrulans.mybonusapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
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

import static android.content.Context.NOTIFICATION_SERVICE;


public class HitFragment extends Fragment {

    ImageView imageURLIV;
    ImageView userIV;
    TextView userTV;
    TextView tagsTV;
    TextView viewsTV;
    TextView likesTV;
    TextView favoritesTV;
    Button saveImageButton;
    Button deleteImageButton;
    ProgressBar loadingImagePB;

    private OnButtonClickedListener mListener;

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
    private int widthScreen;
    private int positionInRV;
    private boolean isLeftSide;
    boolean isInSavedImagePage = false;



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

    public static HitFragment newInstace(SavedHitInformation savedHitInformation, int positionInRV, boolean isLeftSide){

        HitFragment myDialogFragment = new HitFragment();
        Bundle args = new Bundle();

        args.putParcelable("sHI",savedHitInformation);
        args.putInt("position",positionInRV);
        args.putBoolean("isLeftSide",isLeftSide);

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
                isLeftSide = getArguments().getBoolean("isLeftSide");
                positionInRV = getArguments().getInt("position");
                isInSavedImagePage = true;
            }

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hit, container, false);

        widthScreen = getScreenWidth();
        imageURLIV = (ImageView) view.findViewById(R.id.imageURLTV);
        userIV = (ImageView) view.findViewById(R.id.userIV);
        userTV = (TextView) view.findViewById(R.id.userTV);
        tagsTV = (TextView) view.findViewById(R.id.tagsTV);
        viewsTV = (TextView) view.findViewById(R.id.viewsTV);
        likesTV = (TextView) view.findViewById(R.id.likesTV);
        favoritesTV = (TextView) view.findViewById(R.id.favoritesTV);
        saveImageButton = (Button) view.findViewById(R.id.saveImageButton);
        deleteImageButton = (Button) view.findViewById(R.id.deleteImageButton);
        loadingImagePB = (ProgressBar) view.findViewById(R.id.loadingImagePB);

        //imageURLIV.requestLayout();

        if(isInSavedImagePage == false){


            deleteImageButton.setVisibility(View.GONE);
            LayoutParams param = new LayoutParams(0, LayoutParams.WRAP_CONTENT, 2.0f);
            param.setMargins(50, 0, 50, 0);
            saveImageButton.setLayoutParams(param);

            userTV.setText(user);
            tagsTV.setText(tags);
            viewsTV.setText(views);
            likesTV.setText(likes);
            favoritesTV.setText(favorites);

            StartGLidePrecess();
            setFragmentListener(view);
        }else{
            int newButtonWidth = widthScreen/2;
            saveImageButton.getLayoutParams().width = newButtonWidth + 50;
            LoadFromSavedInfo();
        }

        return  view;
    }


    private void StartGLidePrecess(){

        if(webformatURL != null && !webformatURL.equals("")) {
            StartGlideConnectionImageURL(webformatURL);
        }

        if(userImageURL != null && !userImageURL.equals("")) {
            StartGlideConnectionUserImage(userImageURL);
        }


    }

    private void LoadFromSavedInfo(){
        saveImageButton.setText("Download");
        userTV.setText(savedHitInformation.getSavedHit().getUser());
        tagsTV.setText(savedHitInformation.getSavedHit().getTags());
        viewsTV.setText(savedHitInformation.getSavedHit().getViews());
        likesTV.setText(savedHitInformation.getSavedHit().getLikes());
        favoritesTV.setText(savedHitInformation.getSavedHit().getFavorites());
        if(savedHitInformation.getImageURLIV() == null){
            Log.d("lol","getImageURLnya null");
            StartGlideConnectionImageURL(savedHitInformation.getSavedHit().getWebformatURL());

        }else {
            Log.d("lol","getImageURLnya enggak null");
            imageURLBitmap = savedHitInformation.getImageURLIV();
            imageURLIV.setImageBitmap(savedHitInformation.getImageURLIV());
            LoadingFinished();
            setDownloadImageButtonListener();
            setDeleteImageButtonListener();
        }

        if(savedHitInformation.getUserIV() == null){
            Log.d("lol","getUsernya null");
            StartGlideConnectionUserImage(savedHitInformation.getSavedHit().getUserImageURL());
        }else{
            Log.d("lol","getUsernya enggak  null");
            imageUserBitmap = savedHitInformation.getUserIV();
            userIV.setImageBitmap(savedHitInformation.getUserIV());
        }

    }

    private void StartGlideConnectionImageURL(String url){
        Glide.with(getActivity())
                .load(url)
                .asBitmap()
                .dontAnimate()
                .into(new SimpleTarget<Bitmap>(widthScreen, widthScreen) {
                    @Override
                    public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                        // Do something with bitmap here.
                        imageURLBitmap = bitmap;
                        imageURLIV.setImageBitmap(bitmap);
                        if(isInSavedImagePage == false){
                            setSaveImageButtonListener();
                        }else{
                            setDownloadImageButtonListener();
                            setDeleteImageButtonListener();
                        }
                        LoadingFinished();

                    }
                });
    }

    private void StartGlideConnectionUserImage(String url){
        Glide.with(getActivity())
                .load(url)
                .asBitmap()
                .dontAnimate()
                .into(new SimpleTarget<Bitmap>(widthScreen, widthScreen) {
                    @Override
                    public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                        // Do something with bitmap here.
                        imageUserBitmap = bitmap;
                        userIV.setImageBitmap(bitmap);
                        if(isInSavedImagePage == false){
                            setSaveImageButtonListener();
                        }else{
                            setDownloadImageButtonListener();
                            setDeleteImageButtonListener();
                        }

                    }
                });
    }

    private void LoadingFinished(){

        saveImageButton.setEnabled(true);
        loadingImagePB.setVisibility(View.GONE);
        saveImageButton.setAlpha(1);
        deleteImageButton.setEnabled(true);
        deleteImageButton.setAlpha(1);

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

    private void setDeleteImageButtonListener(){

        deleteImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Delete", Toast.LENGTH_SHORT).show();
                onDeleteButtonPressed();
            }
        });

    }

    private void setDownloadImageButtonListener(){

        saveImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowAlertDialog();
            }
        });

    }


    public void onSaveButtonPressed(Bitmap savedImageURL, Bitmap savedUserImage) {

        if (mListener != null) {
            SavedHitInformation savedHitInformation = new SavedHitInformation(hit, savedImageURL, imagePreviewBitmap ,savedUserImage);
            mListener.OnSaveButtonClicked(savedHitInformation);
        }

    }

    public void onDeleteButtonPressed(){

        if (mListener != null) {
            mListener.OnDeleteButtonClicked(positionInRV, isLeftSide);
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnButtonClickedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnSaveButtonClickedListener");
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    private File SaveImageToSDCard(){

        Bitmap bitmap = imageURLBitmap;

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
            return outFile;
        }catch(Exception e){
            e.printStackTrace();
            Log.d("lol","download failed "+ e );
            return null;
        }
    }

    class DownloadAsyncTask extends AsyncTask<Void,Void,File>{

        @Override
        protected File doInBackground(Void... params) {
            return SaveImageToSDCard();
        }

        @Override
        protected void onPostExecute(File file) {
            if(file == null){
                Toast.makeText(getContext(),"Download failed, somethings wrong", Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(getContext(), "Image has been saved to internal storage", Toast.LENGTH_SHORT).show();
                ShowNotification(file);
            }
        }
    }

    public int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    private void ShowAlertDialog(){
        new AlertDialog.Builder(getContext())
                .setTitle("Download this Image?")
                .setMessage("Image will be saved in \"YourInternalStorage/MyBonusAppImage\" folder")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        new DownloadAsyncTask().execute();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void ShowNotification(File file){

        Intent intent = new Intent();
        intent.setAction(android.content.Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "image/*");

        PendingIntent pIntent = PendingIntent.getActivity(getContext(), 0, intent, 0);

        Notification noti = new NotificationCompat.Builder(getContext())
                .setContentTitle("Download completed")
                .setContentText("" + file.getName())
                .setSmallIcon(R.drawable.hover_button)
                .setContentIntent(pIntent).build();

        noti.defaults |= Notification.DEFAULT_VIBRATE;

        noti.flags |= Notification.FLAG_AUTO_CANCEL;
        NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, noti);
    }

    public interface OnButtonClickedListener {
        public void OnSaveButtonClicked(SavedHitInformation savedHitInformation);
        public void OnDeleteButtonClicked(int position, boolean isLeftSide);
    }


}
