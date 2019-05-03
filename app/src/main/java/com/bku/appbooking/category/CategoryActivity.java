package com.bku.appbooking.category;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

import com.bku.appbooking.R;


public class CategoryActivity extends AppCompatActivity {
    private GridView gridView;
    int categoryId;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        categoryId = Integer.valueOf(getIntent().getStringExtra("categoryId"));
        gridView = findViewById(R.id.gridview);
        setupGriview();
    }

    private void setupGriview(){
        CategoryDataReceiver categoryDataReceiver = new CategoryDataReceiver(this, 1);
        final CategoryAdapter categoryAdapter = new CategoryAdapter(this, categoryDataReceiver);
        categoryAdapter.updateNextPage();
        gridView.setAdapter(categoryAdapter);
    }
}
