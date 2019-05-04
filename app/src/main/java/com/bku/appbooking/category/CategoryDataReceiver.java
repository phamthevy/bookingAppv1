package com.bku.appbooking.category;

import android.content.Context;
import android.util.Log;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bku.appbooking.common.Product;
import com.bku.appbooking.ultis.CentralRequestQueue;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class CategoryDataReceiver {
    private Context context;
    private String categoryRequestFormat;
    private String productRequestFormat;

    private ArrayList<Product> products;
    private BaseAdapter linkedAdapter;
    private CentralRequestQueue rQueue;
    private int responseCount = 0;

    private int currentPage = -1;
    private long lastRequestTime;

    public CategoryDataReceiver(Context context, String dataBaseURL, int categoryId) {
        this.context = context;
        //        this.linkedAdapter = linkedAdapter;
        products = new ArrayList<Product>();
        categoryRequestFormat = dataBaseURL + "/category.php?id=" + categoryId;
        productRequestFormat = dataBaseURL + "/product.php?id=%d";
        rQueue = CentralRequestQueue.getInstance();
        lastRequestTime = 0;
    }

    public CategoryDataReceiver(Context context, int categoryId) {
        this(context, "https://style.vihey.com/api" ,categoryId);
    }

    public void setLinkedAdapter(BaseAdapter linkedAdapter) {
        this.linkedAdapter = linkedAdapter;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void updateNextPage(){
        if (System.currentTimeMillis() - lastRequestTime < 1000)
            return;
        String requestStr = categoryRequestFormat;
        final int startCount = products.size();
        Log.e(CategoryDataReceiver.class.getName(), "send request: " + requestStr);
        StringRequest request = new StringRequest(requestStr, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e(CategoryDataReceiver.class.getName(),"recieved response number " + responseCount++);
                    JSONObject object = new JSONObject(response);
                    Iterator<String> keys = object.keys();
                    while (keys.hasNext()) {
                        String key = keys.next();
                        Log.v("list key", key);
                        if(object.get(key) instanceof JSONObject) {
                            JSONObject product_json = object.getJSONObject(key);
                            String tilte = product_json.optString("tieude");
                            String price = product_json.optString("price");
                            String imgUrl = product_json.optString("image");
                            products.add(new Product(tilte, price, imgUrl, ""));
                        } else if (object.get(key) instanceof String){
                            String value = object.getString("type");
                            Log.v("key = type", "value = " + value);
                        }
                    }
                    linkedAdapter.notifyDataSetChanged();
                } catch (JSONException e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
//                Toast.makeText(context, "Some error occurred!!", Toast.LENGTH_SHORT).show();
            }
        });
        rQueue.add(request);
        lastRequestTime = System.currentTimeMillis();
    }


}
