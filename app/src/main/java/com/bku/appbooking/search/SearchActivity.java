package com.bku.appbooking.search;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.bku.appbooking.R;
import com.bku.appbooking.common.Product;
import com.bku.appbooking.detail.DetailActivity;

public class SearchActivity extends AppCompatActivity {

    private GridView gridView;
    EditText edtSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        gridView = findViewById(R.id.searchGridview);
        edtSearch = findViewById(R.id.edtSearch);
        ((Button) findViewById(R.id.btnSearch)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupGridview();
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                intent.putExtra("productId", String.valueOf(id));
                startActivity(intent);
            }
        });
    }

    private void setupGridview(){
        final String params = edtSearch.getText().toString();
        final SearchDataReceiver searchDataReceiver = new SearchDataReceiver(this, params);
        final SearchListAdapter searchListAdapter = new SearchListAdapter(this, searchDataReceiver);
        searchListAdapter.updateNextPage();
        gridView.setAdapter(searchListAdapter);
        gridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem + visibleItemCount >= totalItemCount - 2) {
                    searchListAdapter.updateNextPage();
                }
            }

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }
        });
    }


}
