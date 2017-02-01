package com.faqrulans.mybonusapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerViewAdapt recyclerViewAdapt;
    ArrayList<Hit> arsHit ;
    String frontURL;
    String backURL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InitVar();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.reload_button, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView)item.getActionView();


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                StartConnection(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.savedItem:

                return true;

        }
        return super.onOptionsItemSelected(item);
    }


    private void StartConnection(String query){

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET
                ,frontURL + query + backURL ,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        FillArrayList(response);

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),"Connection Error...",Toast.LENGTH_LONG).show();
                    }
                }

        );

        MySingleton.getInstance(getApplicationContext()).AddRequestQueue(jsonObjectRequest);

    }


    private void FillArrayList(JSONObject response){

        try {

            JSONArray jsonArray = response.getJSONArray("hits");

            if(jsonArray != null && jsonArray.length() > 0) {

                arsHit = new ArrayList<>();
                Hit hit = null;
                String favorites = "";
                String likes = "";
                String tags = "";
                String views = "";
                String userImageURL = "";
                String user = "";
                String webFormatURL = "";
                String previewURL = "";

                for (int i = 0; i < jsonArray.length(); i++) {

                    favorites = jsonArray.getJSONObject(i).getString("favorites");
                    likes = jsonArray.getJSONObject(i).getString("likes");
                    tags = jsonArray.getJSONObject(i).getString("tags");
                    user = jsonArray.getJSONObject(i).getString("user");
                    views = jsonArray.getJSONObject(i).getString("views");
                    userImageURL = jsonArray.getJSONObject(i).getString("userImageURL");
                    webFormatURL = jsonArray.getJSONObject(i).getString("webformatURL");
                    previewURL = jsonArray.getJSONObject(i).getString("previewURL");

                    hit = new Hit(webFormatURL, previewURL, user, userImageURL, tags, views, likes, favorites);
                    arsHit.add(hit);

                }

                recyclerViewAdapt = new RecyclerViewAdapt(getApplicationContext(), arsHit, getSupportFragmentManager());
                recyclerView.setAdapter(recyclerViewAdapt);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            }else{
                Toast.makeText(getApplicationContext(),"Query Error...", Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void InitVar(){
        recyclerView = (RecyclerView) findViewById(R.id.imageRV);
        arsHit = new ArrayList<>();
        frontURL = "https://pixabay.com/api/?key=4403161-ec08857d06dd86d0b4023a0e8&q=";
        backURL = "&image_type=photo&pretty=true";
    }


    public void Coba(){

    }
}
