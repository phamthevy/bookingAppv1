package com.bku.appbooking.main.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bku.appbooking.R;
import com.bku.appbooking.cart.CartAdapter;
import com.bku.appbooking.common.InCartProduct;
import com.bku.appbooking.common.Product;
import com.bku.appbooking.detail.DetailActivity;
import com.bku.appbooking.ultis.Cart;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {
    private CartAdapter myCartAdapter;
    Button btnOrder;
    CheckBox btnAllCheck;
    TextView txPrice, txDelete, txMyCart;
    String productId;
    Product product;
    EditText edtNum;
    List<InCartProduct> inCartProductList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e(this.getClass().getName(), "onCreateView");
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        txPrice =  view.findViewById(R.id.txPrice);
        txDelete =  view.findViewById(R.id.txDelete);
        txMyCart =  view.findViewById(R.id.txMyCart);
        btnOrder = view.findViewById(R.id.btnOrder);
        btnAllCheck = view.findViewById(R.id.btnAllCheck);
        inCartProductList = getListCartData();
//        final List<InCartProduct> inCartProductList = getListCartData();
        getPrice();
        final ListView listView = (ListView)view.findViewById(R.id.listCart);
        myCartAdapter = new CartAdapter(this, getContext(), inCartProductList);
        myCartAdapter.notifyDataSetChanged();
        listView.setAdapter(myCartAdapter);
        txMyCart.setText("Giỏ hàng của tôi("+String.valueOf(inCartProductList.size())+")");
        txDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isCheckSelectCheckBox(inCartProductList)){
                    new AlertDialog.Builder(getContext())
                            .setTitle("Xóa danh mục")
                            .setMessage("Bạn chắc chắn xóa danh mục này?")
                            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    ChangeInCartProductList(inCartProductList);
                                    txMyCart.setText("Giỏ hàng của tôi("+String.valueOf(inCartProductList.size())+")");
                                }
                            })

                            // A null listener allows the button to dismiss the dialog and take no further action.
                            .setNegativeButton(R.string.no, null)
                            .setIcon(R.drawable.icon_warning)
                            .show();
                }
                else {
                    new AlertDialog.Builder(getContext())
                            .setMessage("Vui lòng chọn danh mục cần xóa?")
                            .setPositiveButton(R.string.yes, null)
                            .show();
                }
                }

        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckBox cb = (CheckBox) view.findViewById(R.id.btnCart);
                if (cb.isChecked()) {
                    boolean newState = !inCartProductList.get(position).isChecked();
                    inCartProductList.get(position).setChecked(newState);
                    cb.setChecked(newState);
                    getPrice();
                }
                else {
                    boolean newState = !inCartProductList.get(position).isChecked();
                    inCartProductList.get(position).setChecked(newState);
                    cb.setChecked(newState);
                    getPrice();


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
                       getPrice();
                    }
                    else {
                        inCartProductList.get(i).setChecked(false);
                        myCartAdapter.notifyDataSetChanged();
                        getPrice();
                    }
                }
            }


        });

        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.dialog_order);
                dialog.setTitle("Xac nhan");
                dialog.setCanceledOnTouchOutside(false);
                edtNum = (EditText) dialog.findViewById(R.id.edtNumProduct);
                Button btnCancel = (Button) dialog.findViewById(R.id.btnCancalAddProduct);
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                Button btnConfirm = (Button) dialog.findViewById(R.id.btnConfirmAddProduct);
                btnConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final int numberProduct = Integer.valueOf(edtNum.getText().toString());
                        if (numberProduct != 0){
                            Cart.getInstance().addProduct(new InCartProduct(product, numberProduct,false));
                            dialog.dismiss();
                            Toast.makeText(getContext(), "Add thanh cong", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                dialog.show();
            }
        });






        return view;
    }
    private  List<InCartProduct> getListCartData() {

        List<InCartProduct> inCartProductList = new ArrayList<InCartProduct>();//Cart.getInstance().getProducts();
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
    public void  getPrice(){
        long price =0;
        for (int i=0; i<inCartProductList.size(); i++){
            if (inCartProductList.get(i).isChecked()){
                String strPrice = inCartProductList.get(i).getProduct().getPrice();
                int num = inCartProductList.get(i).getNum();;
                price += (Long.valueOf(strPrice)* num);
            }

        }
        txPrice.setText(String.valueOf(price+" VND"));
    }
    private void ChangeInCartProductList(List<InCartProduct> inCartProductList){
        int i = 0;
        while (i < inCartProductList.size()){
            if (inCartProductList.get(i).isChecked()){
                inCartProductList.remove(i);
                i = 0;
            }
            else {
                i += 1;
            }
        }
        myCartAdapter.notifyDataSetChanged();
    }
    private  boolean isCheckSelectCheckBox(List<InCartProduct> inCartProductList){
        for (int i=0; i<inCartProductList.size(); i++){
            if (inCartProductList.get(i).isChecked()){
                return true;

            }

        }
        return false;
    }
}
