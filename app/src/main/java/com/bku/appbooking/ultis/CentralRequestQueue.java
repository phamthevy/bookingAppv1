package com.bku.appbooking.ultis;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class CentralRequestQueue {
    private static final CentralRequestQueue ourInstance = new CentralRequestQueue();
    private RequestQueue rQueue;

    public static CentralRequestQueue getInstance() {
        return ourInstance;
    }

    private CentralRequestQueue() {
    }

    public void setupAndStart(Context context){
        rQueue = Volley.newRequestQueue(context);
    }

    public void add(StringRequest stringRequest){
        rQueue.add(stringRequest);
    }
}
