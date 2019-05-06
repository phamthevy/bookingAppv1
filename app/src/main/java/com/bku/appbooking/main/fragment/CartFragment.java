package com.bku.appbooking.main.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.bku.appbooking.R;
import com.bku.appbooking.cart.CartAdapter;
import com.bku.appbooking.common.InCartProduct;
import com.bku.appbooking.common.Product;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e(this.getClass().getName(), "onCreateView");
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        List<InCartProduct> inCartProductList = getListCartData();
        final ListView listView = (ListView)view.findViewById(R.id.listCart);
        listView.setAdapter(new CartAdapter(getActivity(), inCartProductList));

        return view;
    }
    private  List<InCartProduct> getListCartData() {
        List<InCartProduct> inCartProductList = new ArrayList<InCartProduct>();
        InCartProduct product1 = new InCartProduct(new Product(123, "title1","100000d","fe","shortDescription1", "longDescription"),4);
        InCartProduct product2 = new InCartProduct(new Product(234, "title2","100000d","fe","shortDescription1", "longDescription"),2);

        inCartProductList.add(product1);
        inCartProductList.add(product1);
        inCartProductList.add(product1);
        inCartProductList.add(product2);


        return inCartProductList;
    }
}
