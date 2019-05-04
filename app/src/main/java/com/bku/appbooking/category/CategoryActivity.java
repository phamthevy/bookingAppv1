package com.bku.appbooking.category;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.ImageView;

import com.bku.appbooking.R;
import com.bku.appbooking.common.Category;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.bku.appbooking.R;
import com.bku.appbooking.common.Product;
import com.bku.appbooking.detail.DetailActivity;


public class CategoryActivity extends AppCompatActivity {
    private GridView gridView;
    int categoryId;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Gson gson = new Gson();
        Category category = gson.fromJson(getIntent().getStringExtra("category"), Category.class);
        categoryId = category.getId();
        gridView = findViewById(R.id.gridview );
        setupGridview();
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Object o = gridView.getItemAtPosition(position);
//                Product product = (Product) o;
//                Log.d("abcd1", String.valueOf(id));
//                Log.d("abcd2", String.valueOf(product.getId()));
                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                intent.putExtra("productId", String.valueOf(id));
                startActivity(intent);
            }
        });
    }

    private void setupGridview(){
        CategoryDataReceiver categoryDataReceiver = new CategoryDataReceiver(this, categoryId);
        final CategoryAdapter categoryAdapter = new CategoryAdapter(this, categoryDataReceiver);
        categoryAdapter.updateNextPage();
        gridView.setAdapter(categoryAdapter);
        gridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem + visibleItemCount >= totalItemCount - 2) {
                    categoryAdapter.updateNextPage();
                }
            }

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }
        });
    }
}
