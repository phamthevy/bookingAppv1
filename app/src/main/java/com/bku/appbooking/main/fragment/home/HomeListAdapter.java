package com.bku.appbooking.main.fragment.home;

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
import com.bku.appbooking.ultis.CentralRequestQueue;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class HomeListAdapter extends BaseAdapter {
    private Context context;
    private List<Category> categories;
    private String homeApiUrl = "http://booking.vihey.com/api/home.php";

    HomeListAdapter getHomeListAdapterInstance(){
        return this;
    }

    public HomeListAdapter(final Context context) {
        this.context = context;
        this.categories = new LinkedList<>();
        StringRequest stringRequest = new StringRequest(homeApiUrl, new Response.Listener<String>() {
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
                            String tilte = product_json.optString("tieude");
                            String description = product_json.optString("mieutangan");
                            String imgUrl = product_json.optString("image");
                            long id = Long.valueOf(key);
                            categories.add(new Category(Integer.valueOf(key), tilte, description, imgUrl));
                        } else if (object.get(key) instanceof String){
                            String value = object.getString("type");
                            Log.v("key = type", "value = " + value);
                        }
                    }
                    getHomeListAdapterInstance().notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Không tải được dữ liệu", Toast.LENGTH_SHORT).show();
            }
        });
        CentralRequestQueue.getInstance().add(stringRequest);
    }

    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public Object getItem(int position) {
        return categories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return categories.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Category category = categories.get(position);
        if (convertView == null){
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.layout_category_item, null);
            TextView titleView = convertView.findViewById(R.id.home_cate_title);
            TextView desView = convertView.findViewById(R.id.home_cate_des);
            ImageView imageView = (ImageView)convertView.findViewById(R.id.home_cate_image);
            CategoryViewHolder categoryViewHolder = new CategoryViewHolder(titleView, desView, imageView);
            convertView.setTag(categoryViewHolder);
        }
        CategoryViewHolder categoryViewHolder = (CategoryViewHolder)convertView.getTag();
        categoryViewHolder.getTitle().setText(category.getTitle());
        categoryViewHolder.getDescription().setText(category.getDescription());
        ImageView imageView = categoryViewHolder.getImageView();
        Picasso.with(context).load(category.getImageUrl()).into(imageView);
        return convertView;
    }

    private class CategoryViewHolder {
        private TextView title;
        private TextView description;
        private ImageView imageView;

        public CategoryViewHolder(TextView title, TextView description, ImageView imageView) {
            this.title = title;
            this.description = description;
            this.imageView = imageView;
        }

        public TextView getTitle() {
            return title;
        }

        public TextView getDescription() {
            return description;
        }

        public ImageView getImageView() {
            return imageView;
        }
    }
}
