package com.bku.appbooking.detail;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpResponse;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bku.appbooking.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class DetailActivity extends AppCompatActivity {

    String productId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        productId = getIntent().getStringExtra("productId");

        getContentTextView(productId);
    }


    private void getContentTextView(String productId){
        String requestStr= "https://style.vihey.com/api/product.php?id="+"4"; //+productId
        StringRequest request = new StringRequest(requestStr, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    response=new String(response.getBytes("ISO-8859-1"), "UTF-8");
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
                            String shortDescription = product_json.optString("mieutangan");
                            String longDescription = product_json.optString("mieutadai");
                            long id = Long.valueOf(key);
                            Log.d("DetailLoad", tilte+" "+price+" "+imgUrl+" "+shortDescription+" "+longDescription);
                            setTextView(tilte, price, imgUrl, shortDescription, longDescription);

                        } else if (object.get(key) instanceof String){
                            String value = object.getString("type");
                            Log.v("key = type", "value = " + value);
                        }
                    }
                } catch (Exception e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(), "Some error occurred!!", Toast.LENGTH_SHORT).show();
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(
                3000,
                2,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue rQueue = Volley.newRequestQueue(getApplicationContext());
        rQueue.add(request);
    }

    private void setTextView(String title, String price, String imgUrl, String shortDescription, String longDescription) {
        ((TextView) findViewById(R.id.tvTitle)).setText(title);
        ((TextView) findViewById(R.id.tvPrice)).setText(price+" VND");
        ((TextView) findViewById(R.id.tvShortDescription)).setText(shortDescription);
        ((TextView) findViewById(R.id.tvLongDescription)).setText(longDescription);

        ImageView imProduct = (ImageView)findViewById(R.id.imProduct);
        Picasso.with(getApplicationContext()).load(imgUrl).into(imProduct);
    }


}
