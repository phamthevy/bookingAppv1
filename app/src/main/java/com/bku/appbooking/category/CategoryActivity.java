package com.bku.appbooking.category;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
        categoryId = Integer.valueOf(getIntent().getStringExtra("categoryId"));
        gridView = findViewById(R.id.gridview);
        setupGridview();
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object o = gridView.getItemAtPosition(position);
                Product product = (Product) o;
                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                intent.putExtra("productId", String.valueOf(product.getId()));
                startActivity(intent);
            }
        });
    }

    private void setupGridview(){
        CategoryDataReceiver categoryDataReceiver = new CategoryDataReceiver(this, categoryId);
        CategoryAdapter categoryAdapter = new CategoryAdapter(this, categoryDataReceiver);
        categoryAdapter.updateNextPage();
        gridView.setAdapter(categoryAdapter);
    }
}
