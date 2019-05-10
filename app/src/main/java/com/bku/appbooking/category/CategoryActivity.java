package com.bku.appbooking.category;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.ImageView;

import com.bku.appbooking.R;
import com.bku.appbooking.common.Category;
import com.bku.appbooking.ultis.CircleTransform;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.bku.appbooking.R;
import com.bku.appbooking.common.Product;
import com.bku.appbooking.detail.DetailActivity;


public class CategoryActivity extends AppCompatActivity {
    private GridView gridView;
    private Category category;
    private int count = 0;
    TextView cateTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Gson gson = new Gson();
        category = gson.fromJson(getIntent().getStringExtra("category"), Category.class);
        gridView = findViewById(R.id.gridview);
        ImageView imageView = findViewById(R.id.category_demo_img);
        Transformation transformation = new RoundedCornersTransformation(20, 0);
        Picasso.with(this).load(category.getImageUrl()).transform(transformation).into(imageView);
        cateTitle = findViewById(R.id.cate_title);
        cateTitle.setText(category.getTitle());
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
        CategoryDataReceiver categoryDataReceiver = new CategoryDataReceiver(this, category.getId());
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
