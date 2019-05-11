package com.bku.appbooking.main.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bku.appbooking.R;
import com.bku.appbooking.category.CategoryActivity;
import com.bku.appbooking.common.Category;
import com.bku.appbooking.common.Product;
import com.bku.appbooking.home.HomeListAdapter;
import com.bku.appbooking.ultis.CentralRequestQueue;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import jp.wasabeef.picasso.transformations.internal.Utils;

public class HomeFragment extends Fragment {
    Button button;
    ListView listView;


    @Override
    public void onActivityCreated(Bundle saveInstanceState){
        super.onActivityCreated(saveInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_home, container, false);
//        Intent intent = new Intent(getContext(), CategoryActivity.class);
//        intent.putExtra("categoryId", String.valueOf(1));
//        startActivity(intent);
        listView = view.findViewById(R.id.home_list_view);
        ViewGroup header = (ViewGroup)inflater.inflate(R.layout.home_header, listView, false);
        listView.addHeaderView(header, null, false);
//        button = header.findViewById(R.id.start_category_btn);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getContext(), CategoryActivity.class);
//                Gson gson = new Gson();
//                String data = gson.toJson(this);
//                Category category = new Category(1, "tit", "des", "http://booking.vihey.com/uploads/productcategory/1.jpg");
//                intent.putExtra("category", gson.toJson(category));
//                startActivity(intent);
//            }
//        });
        HomeListAdapter homeListAdapter = new HomeListAdapter(getContext());
        listView.setAdapter(homeListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), CategoryActivity.class);
                Log.e("position", String.valueOf(position));
                Log.e("id", String.valueOf(id));
                Gson gson = new Gson();
                intent.putExtra("category", gson.toJson(parent.getAdapter().getItem(position), Category.class));
                startActivity(intent);
            }
        });
        return view;
    }
}
