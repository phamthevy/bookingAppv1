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
        InCartProduct product1 = new InCartProduct(new Product(11, "title1","100000d","https://style.vihey.com/uploads/product/4.jpg","shortDescription1", "longDescription"),11);
        InCartProduct product2 = new InCartProduct(new Product(22, "title2","200000d","https://style.vihey.com/uploads/product/4.jpg","shortDescription2", "longDescription2"),12);
        InCartProduct product3 = new InCartProduct(new Product(33, "title3","300000d","https://style.vihey.com/uploads/product/4.jpg","shortDescription3", "longDescription3"),13);
        InCartProduct product4 = new InCartProduct(new Product(44, "title4","400000d","https://style.vihey.com/uploads/product/4.jpg","shortDescription4", "longDescription4"),14);

        inCartProductList.add(product1);
        inCartProductList.add(product2);
        inCartProductList.add(product3);
        inCartProductList.add(product4);


        return inCartProductList;
    }
}
