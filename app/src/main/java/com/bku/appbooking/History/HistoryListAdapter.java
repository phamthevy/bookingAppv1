package com.bku.appbooking.History;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bku.appbooking.R;
import com.bku.appbooking.common.Category;
import com.bku.appbooking.common.HistoryItem;
import com.bku.appbooking.common.Product;
import com.bku.appbooking.ultis.CentralRequestQueue;
import com.bku.appbooking.ultis.UserInfo;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HistoryListAdapter extends BaseAdapter {
    private List<HistoryItem> historyItems;
    private String historyUrl = "http://booking.vihey.com/api/history.php?accesstoken=";
    private Context context;

    public HistoryListAdapter(final Context context) {
        this.context = context;
        historyItems = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(historyUrl + UserInfo.getInstance().getAccessToken(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    response=new String(response.getBytes("ISO-8859-1"), "UTF-8");
                    JSONObject object =  new JSONObject(response);
                    Iterator<String> keys = object.keys();
                    while (keys.hasNext()) {
                        String key = keys.next();
                        Log.v("list key", key);
                        if(object.get(key) instanceof JSONObject) {
                            JSONObject product_json = object.getJSONObject(key);
                            int productId = Integer.valueOf(key);
                            String createDate = product_json.optString("ngaytao");
                            String description = product_json.optString("ghichu");
                            String status = product_json.optString("trangthai");
                            completeLoadHistoryItem(new HistoryItem(new Product(productId), createDate, description, status));
                        } else if (object.get(key) instanceof String){
                            String value = object.getString("type");
                            Log.v("key = type", "value = " + value);
                        }
                    }
                    notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            private void completeLoadHistoryItem(final HistoryItem historyItem){
                final Product product = historyItem.getProduct();
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
                                    String title = product_json.optString("tieude");
                                    product.setTitle(title);
                                    String price = product_json.optString("price");
                                    product.setPrice(price);
                                    String imageUrl = product_json.optString("image");
                                    product.setImageUrl(imageUrl);
                                    String shortDescription = product_json.optString("mieutangan");
                                    product.setShortDescription(shortDescription);
                                    String longDescription = product_json.optString("mieutadai");
                                    product.setLongDescription(longDescription);
                                } else if (object.get(key) instanceof String){
                                    String value = object.getString("type");
                                    Log.v("key = type", "value = " + value);
                                }
                            }
                            historyItems.add(historyItem);
                            notifyDataSetChanged();
                            Log.e("hiiiiiiiiiiii", historyItem.getProduct().getTitle());
                        } catch (Exception e) {

                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(context, "cannot history home data111", Toast.LENGTH_SHORT).show();
                    }
                });
                CentralRequestQueue.getInstance().add(request);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "cannot history home data", Toast.LENGTH_SHORT).show();
            }
        });
        CentralRequestQueue.getInstance().add(stringRequest);
    }

    @Override


    public int getCount() {
        return historyItems.size();
    }

    @Override
    public Object getItem(int position) {
        return historyItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return historyItems.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HistoryItem historyItem = historyItems.get(position);
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        HistoryItemHodler historyItemHodler;
        if (convertView == null){
            convertView = layoutInflater.inflate(R.layout.layout_history_item, null);
            ImageView imageView = convertView.findViewById(R.id.history_item_image);
            TextView itemTitleView = convertView.findViewById(R.id.history_item_title);
            TextView itemPriceView = convertView.findViewById(R.id.history_item_price);
            TextView dateCreatedView = convertView.findViewById(R.id.history_item_date);
            TextView statusView = convertView.findViewById(R.id.history_item_status);
            historyItemHodler = new HistoryItemHodler(imageView, itemTitleView, itemPriceView, dateCreatedView, statusView);
            convertView.setTag(historyItemHodler);
        }
        else {
            historyItemHodler = (HistoryItemHodler) convertView.getTag();
        }
        Log.e("aaaaaaaaaaaaaaa", "aaaaaaaaaaaaaaaaaa");
        ImageView imageView = historyItemHodler.getImageView();
        TextView itemTitleView = historyItemHodler.getProductTitle();
        TextView itemPriceView = historyItemHodler.getProductPrice();
        TextView dateCreatedView = historyItemHodler.getDateCreated();
        TextView statusView = historyItemHodler.getStatus();
        Picasso.with(context).load(historyItem.getProduct().getImageUrl()).into(imageView);
        itemTitleView.setText(historyItem.getProduct().getTitle());
        itemPriceView.setText("Giá bán: " + historyItem.getProduct().getPrice());
        dateCreatedView.setText("Thời gian đặt hàng: " + historyItem.getCreateDate());
        statusView.setText(historyItem.getStatus());
        return convertView;
    }

    private class HistoryItemHodler {
        private ImageView imageView;
        private TextView productTitle;
        private TextView productPrice;
        private TextView dateCreated;
        private TextView status;

        public HistoryItemHodler(ImageView imageView, TextView productTitle, TextView productPrice, TextView dateCreated, TextView status) {
            this.imageView = imageView;
            this.productTitle = productTitle;
            this.productPrice = productPrice;
            this.dateCreated = dateCreated;
            this.status = status;
        }

        public ImageView getImageView() {
            return imageView;
        }

        public TextView getProductTitle() {
            return productTitle;
        }

        public TextView getProductPrice() {
            return productPrice;
        }

        public TextView getDateCreated() {
            return dateCreated;
        }

        public TextView getStatus() {
            return status;
        }
    }
}
