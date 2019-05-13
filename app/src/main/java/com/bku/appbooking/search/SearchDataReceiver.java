package com.bku.appbooking.search;

import android.content.Context;
import android.util.Log;
import android.widget.BaseAdapter;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bku.appbooking.R;
import com.bku.appbooking.common.Product;
import com.bku.appbooking.ultis.CentralRequestQueue;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SearchDataReceiver {
    private Context context;
    private String searchRequestURL;
    private String params;

    private ArrayList<Product> products;
    private BaseAdapter linkedAdapter;
    private CentralRequestQueue rQueue;


    public SearchDataReceiver(Context context, String params) {
        this.context = context;
        products = new ArrayList<Product>();
        this.params = params;
        searchRequestURL = "https://booking.vihey.com/api/searchPost.php";
        rQueue = CentralRequestQueue.getInstance();
    }

    public void setLinkedAdapter(BaseAdapter linkedAdapter) {
        this.linkedAdapter = linkedAdapter;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void updateNextPage(){
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, searchRequestURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response: ", response);
                try {
//                    response=new String(response.getBytes("ISO-8859-1"), "UTF-8");
                    JSONObject object = new JSONObject(response);
                    int status = object.optInt("status");
                    if (status == 1){
                        Iterator<String> keys = object.keys();
                        while (keys.hasNext()) {
                            String key = keys.next();
                            Log.v("list key", key);
                            if(object.get(key) instanceof JSONObject) {
                                JSONObject product_json = object.getJSONObject(key);
                                String tilte = product_json.optString("tieude");
                                String price = product_json.optString("price");
                                String imgUrl = product_json.optString("image");
                                int id = Integer.valueOf(key);
                                products.add(new Product(id, tilte, price, imgUrl, "", ""));
                            } else if (object.get(key) instanceof String){
                                String value = object.getString("type");
                                Log.v("key = type", "value = " + value);
                            }
                        }
                        linkedAdapter.notifyDataSetChanged();
                    } else {
                        new AlertDialog.Builder(context)
                                .setMessage("Lấy thông tin user thất bại")
                                .setPositiveButton(R.string.yes, null)
                                .show();
                    }

                } catch (Exception e) {

                }


            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                new AlertDialog.Builder(context)
                        .setMessage("Không thể lấy thông tin user")
                        .setPositiveButton(R.string.yes, null)
                        .show();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("q", params);
                return MyData;
            }

        };

        rQueue.add(MyStringRequest);
    }


}
