package com.faqrulans.mybonusapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;


public class SavedImageFragment extends Fragment {

    private ArrayList<SavedHitInformation> savedInformations;

    public SavedImageFragment() {
        // Required empty public constructor
    }


    public static SavedImageFragment newInstance(ArrayList<SavedHitInformation> savedInformations) {
        SavedImageFragment fragment = new SavedImageFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList("param1", savedInformations);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            savedInformations =  getArguments().getParcelableArrayList("param1");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //Toast.makeText(getActivity(),"Saved Clicked..", Toast.LENGTH_LONG).show();
        return inflater.inflate(R.layout.fragment_saved_image, container, false);
    }

}
