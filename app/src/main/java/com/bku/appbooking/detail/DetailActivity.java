package com.bku.appbooking.detail;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bku.appbooking.R;
import com.bku.appbooking.common.InCartProduct;
import com.bku.appbooking.common.Product;
import com.bku.appbooking.main.MainActivity;
import com.bku.appbooking.ultis.Cart;
import com.bku.appbooking.ultis.CountDrawable;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.Iterator;

public class DetailActivity extends AppCompatActivity {

    String productId;
    Product product;
    EditText edtNum;
    private Menu mOptionsMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        productId = getIntent().getStringExtra("productId");

        getContentTextView(productId);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        ((Button) findViewById(R.id.btnBuy)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                final Dialog dialog = new Dialog(DetailActivity.this);
                dialog.setContentView(R.layout.dialog_add_to_cart);
                dialog.setCanceledOnTouchOutside(false);
                edtNum = (EditText) dialog.findViewById(R.id.edtNumProduct);
                Button btnCancel = (Button) dialog.findViewById(R.id.btnCancalAddProduct);
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                Button btnConfirm = (Button) dialog.findViewById(R.id.btnConfirmAddProduct);
                btnConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (edtNum.getText().toString().equals("")){
                            return;
                        }
                        final int numberProduct = Integer.valueOf(edtNum.getText().toString());
                        if (numberProduct != 0){
                            Cart.getInstance().addProduct(new InCartProduct(product, numberProduct,false));
                            dialog.dismiss();
                            setCount(DetailActivity.this, mOptionsMenu, String.valueOf(Cart.getInstance().getProducts().size()));
                            Toast.makeText(DetailActivity.this, "Thêm vào giỏ hàng thành công", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                dialog.show();
            }
        });
    }

    private void getContentTextView(String productId){
        String requestStr= "https://style.vihey.com/api/product.php?id="+productId;
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
                            String title = product_json.optString("tieude");
                            String price = product_json.optString("price");
                            String imgUrl = product_json.optString("image");
                            String shortDescription = product_json.optString("mieutangan");
                            String longDescription = product_json.optString("mieutadai");
//                            long id = Long.valueOf(key);
                            Log.d("DetailLoad", title+" "+price+" "+imgUrl+" "+shortDescription+" "+longDescription);
                            product = new Product(Integer.valueOf(key), title, price, imgUrl, shortDescription, longDescription);
                            setTextView(title, price, imgUrl, shortDescription, longDescription);

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
                Toast.makeText(getApplicationContext(), "Lỗi!!", Toast.LENGTH_SHORT).show();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.cart_menu, menu);
        mOptionsMenu = menu;
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        setCount(this, menu, String.valueOf(Cart.getInstance().getProducts().size()));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ic_cart:
                Intent intent = new Intent(DetailActivity.this, MainActivity.class);
                intent.putExtra("fragment","cart");
                startActivity(intent);
                break;
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }

        return true;
    }

    public void setCount(Context context, Menu menu, String count) {
        MenuItem menuItem = menu.findItem(R.id.ic_cart);
        LayerDrawable icon = (LayerDrawable) menuItem.getIcon();

        CountDrawable badge;

        // Reuse drawable if possible
        Drawable reuse = icon.findDrawableByLayerId(R.id.ic_group_count);
        if (reuse != null && reuse instanceof CountDrawable) {
            badge = (CountDrawable) reuse;
        } else {
            badge = new CountDrawable(context);
        }

        badge.setCount(count);
        icon.mutate();
        icon.setDrawableByLayerId(R.id.ic_group_count, badge);
    }


}
