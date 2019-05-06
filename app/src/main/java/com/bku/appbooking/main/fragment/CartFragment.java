package com.bku.appbooking.main.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bku.appbooking.R;
import com.bku.appbooking.cart.CartAdapter;
import com.bku.appbooking.common.InCartProduct;
import com.bku.appbooking.common.Product;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {
    private CartAdapter myCartAdapter;
    Button btnOrder, btnSumPrice;
    CheckBox btnAllCheck;
    TextView txPrice;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e(this.getClass().getName(), "onCreateView");
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        txPrice =  view.findViewById(R.id.txPrice);

        btnOrder = view.findViewById(R.id.btnOrder);
        btnSumPrice = view.findViewById(R.id.btnSumPrice);
        btnAllCheck = view.findViewById(R.id.btnAllCheck);
        final List<InCartProduct> inCartProductList = getListCartData();
        txPrice.setText(getPrice(inCartProductList));
        final ListView listView = (ListView)view.findViewById(R.id.listCart);
        myCartAdapter = new CartAdapter(getContext(), inCartProductList);
        myCartAdapter.notifyDataSetChanged();
        listView.setAdapter(myCartAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckBox cb = (CheckBox) view.findViewById(R.id.btnCart);
                if (cb.isChecked()) {
                    txPrice.setText(getPrice(inCartProductList));
                }

            }
        });
        btnAllCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < inCartProductList.size(); i++) {

                    if (btnAllCheck.isChecked()) {

                        inCartProductList.get(i).setChecked(true);
                        myCartAdapter.notifyDataSetChanged();
                        txPrice.setText(getPrice(inCartProductList));
                    }
                    else {
                        inCartProductList.get(i).setChecked(false);
                        myCartAdapter.notifyDataSetChanged();
                        txPrice.setText(getPrice(inCartProductList));
                    }
                }
            }


        });
        btnSumPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txPrice.setText(getPrice(inCartProductList));
            }
        });

        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Invalid", Toast.LENGTH_LONG).show();

            }
        });






        return view;
    }
    private  List<InCartProduct> getListCartData() {
        List<InCartProduct> inCartProductList = new ArrayList<InCartProduct>();
        InCartProduct product1 = new InCartProduct(new Product(11, "title1","100000000","https://style.vihey.com/uploads/product/4.jpg","shortDescription1", "longDescription"),11,true);
        InCartProduct product2 = new InCartProduct(new Product(22, "title2","200000","https://style.vihey.com/uploads/product/4.jpg","shortDescription2", "longDescription2"),12,false);
        InCartProduct product3 = new InCartProduct(new Product(33, "title3","300000","https://style.vihey.com/uploads/product/4.jpg","shortDescription3", "longDescription3"),13,false);
        InCartProduct product4 = new InCartProduct(new Product(44, "title4","400000","https://style.vihey.com/uploads/product/4.jpg","shortDescription4", "longDescription4"),14,false);

        inCartProductList.add(product1);
        inCartProductList.add(product2);
        inCartProductList.add(product3);
        inCartProductList.add(product4);


        return inCartProductList;
    }
    private String getPrice(List<InCartProduct> inCartProductList){
        long price =0;
        for (int i=0; i<inCartProductList.size(); i++){
            if (inCartProductList.get(i).isChecked()){
                String strPrice = inCartProductList.get(i).getProduct().getPrice();
                int num = inCartProductList.get(i).getNum();
                Log.e("num: ",String.valueOf(num));
                price += (Long.valueOf(strPrice)* num);
            }

        }
        return String.valueOf(price+" VND");
    }
}
