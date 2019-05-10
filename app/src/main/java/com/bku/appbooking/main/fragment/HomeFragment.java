package com.bku.appbooking.main.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bku.appbooking.R;
import com.bku.appbooking.category.CategoryActivity;
import com.bku.appbooking.common.Category;
import com.google.gson.Gson;

public class HomeFragment extends Fragment {
    Button button;


    @Override
    public void onActivityCreated(Bundle saveInstanceState){
        super.onActivityCreated(saveInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_home, container, false);
        button = view.findViewById(R.id.start_category_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CategoryActivity.class);
                Gson gson = new Gson();
                String data = gson.toJson(this);
                Category category = new Category(1, "tit", "des", "http://booking.vihey.com/uploads/productcategory/1.jpg");
                intent.putExtra("category", gson.toJson(category));
                startActivity(intent);
            }
        });
        Intent intent = new Intent(getContext(), CategoryActivity.class);
        intent.putExtra("categoryId", String.valueOf(1));
//        startActivity(intent);
        return view;
    }


}
