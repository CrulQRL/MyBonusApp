package com.faqrulans.mybonusapp;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;


/**
 * Created by faqrulan on 1/30/17.
 */
public class MySingleton {

    private static MySingleton theInstance;
    private static Context theContext;
    private RequestQueue theRequestQueue;

    private MySingleton(Context context) {

        theContext = context;
        theRequestQueue = getRequestQueue();

    }

    public RequestQueue getRequestQueue(){

        if(theRequestQueue == null){
            theRequestQueue = Volley.newRequestQueue(theContext.getApplicationContext());
        }

        return theRequestQueue;

    }

    public static synchronized MySingleton getInstance(Context context) {

        if(theInstance == null){
            theInstance = new MySingleton(context);
        }
        return theInstance;
    }

    public<T> void AddRequestQueue(Request<T> request){

        theRequestQueue.add(request);
    }



}
