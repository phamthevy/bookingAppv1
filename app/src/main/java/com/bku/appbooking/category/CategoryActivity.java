package com.bku.appbooking.category;

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
import android.view.MotionEvent;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.ImageView;

import com.bku.appbooking.R;
import com.bku.appbooking.common.Category;
import com.bku.appbooking.main.MainActivity;
import com.bku.appbooking.search.ExpandableHeightGridView;
import com.bku.appbooking.search.SearchActivity;
import com.bku.appbooking.ultis.Cart;
import com.bku.appbooking.ultis.CountDrawable;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.bku.appbooking.detail.DetailActivity;


public class CategoryActivity extends AppCompatActivity {
    private ExpandableHeightGridView gridView;
    private Category category;
    private int count = 0;
    TextView cateTitle;
    private Menu mOptionsMenu = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Gson gson = new Gson();
        category = gson.fromJson(getIntent().getStringExtra("category"), Category.class);
        gridView = (ExpandableHeightGridView) findViewById(R.id.gridview);
        gridView.setExpanded(true);
        ImageView imageView = findViewById(R.id.category_demo_img);
        Transformation transformation = new RoundedCornersTransformation(20, 0);
        Picasso.with(this).load(category.getImageUrl()).transform(transformation).into(imageView);
        cateTitle = findViewById(R.id.cate_title);
        cateTitle.setText(category.getTitle());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        setupGridview();
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                intent.putExtra("productId", String.valueOf(id));
                startActivity(intent);
            }
        });
    }

    private void setupGridview() {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_cart_menu, menu);
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
            case R.id.ic_cart_combine:
                Intent intent = new Intent(CategoryActivity.this, MainActivity.class);
                intent.putExtra("fragment", "cart");
                startActivity(intent);
                break;
            case R.id.ic_search_combine:
                Intent intentSearch = new Intent(CategoryActivity.this, SearchActivity.class);
                startActivity(intentSearch);
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
        MenuItem menuItem = menu.findItem(R.id.ic_cart_combine);
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

    @Override
    public void onResume() {
        super.onResume();
        if (mOptionsMenu != null) {
            setCount(this, mOptionsMenu, String.valueOf(Cart.getInstance().getProducts().size()));
        }
    }
}
