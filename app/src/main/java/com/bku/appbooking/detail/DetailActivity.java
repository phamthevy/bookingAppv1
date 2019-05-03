package com.bku.appbooking.detail;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bku.appbooking.R;
import com.bku.appbooking.category.CategoryAdapter;
import com.bku.appbooking.category.CategoryDataReceiver;

public class DetailActivity extends AppCompatActivity {

    String productId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        productId = getIntent().getStringExtra("productId");

        //TODO: get link san pham roi gan cac gia tri vao view
//        CategoryDataReceiver categoryDataReceiver = new CategoryDataReceiver(this, categoryId);
//        CategoryAdapter categoryAdapter = new CategoryAdapter(this, categoryDataReceiver);
//        categoryAdapter.updateNextPage();
    }


}
