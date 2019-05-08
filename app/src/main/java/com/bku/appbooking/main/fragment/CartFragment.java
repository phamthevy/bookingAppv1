package com.bku.appbooking.main.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bku.appbooking.R;
import com.bku.appbooking.cart.CartAdapter;
import com.bku.appbooking.category.CategoryDataReceiver;
import com.bku.appbooking.common.InCartProduct;
import com.bku.appbooking.common.Product;
import com.bku.appbooking.detail.DetailActivity;
import com.bku.appbooking.ultis.Cart;
import com.bku.appbooking.ultis.CentralRequestQueue;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class CartFragment extends Fragment {
    private CartAdapter myCartAdapter;
    Button btnOrder;
    CheckBox btnAllCheck;
    TextView txPrice, txDelete, txMyCart,txSumPrice;
    EditText edtName, edtPhone, edtAddress, edtEmail, edtNote;
    List<InCartProduct> inCartProductList;
    private long lastRequestTime = 0;
    private CentralRequestQueue rQueue = CentralRequestQueue.getInstance();

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
                if(isCheckSelectCheckBox()){
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
                    if(notSelectAllCheckBox()){
                        btnAllCheck.setChecked(false);
                    }
                    else {
                        btnAllCheck.setChecked(true);
                    }
                    getPrice();
                }
                else {
                    boolean newState = !inCartProductList.get(position).isChecked();
                    inCartProductList.get(position).setChecked(newState);
                    cb.setChecked(newState);
                    if(notSelectAllCheckBox()){
                        btnAllCheck.setChecked(false);
                    }
                    else {
                        btnAllCheck.setChecked(true);
                    }
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
                if(isCheckSelectCheckBox()){
                    final Dialog dialog = new Dialog(getContext());
                    dialog.setContentView(R.layout.dialog_order);
                    dialog.setCanceledOnTouchOutside(false);
                    edtName = (EditText) dialog.findViewById(R.id.edtName);
                    edtPhone = (EditText) dialog.findViewById(R.id.edtPhone);
                    edtEmail = (EditText) dialog.findViewById(R.id.edtEmail);
                    edtAddress = (EditText) dialog.findViewById(R.id.edtAddress);
                    edtNote = (EditText) dialog.findViewById(R.id.edtNote);
                    txSumPrice = (TextView)dialog.findViewById(R.id.txSumPrice) ;


                    txSumPrice.setText(txPrice.getText().toString());
                    Button btnCancel = (Button) dialog.findViewById(R.id.btnCancelOrder);
                    btnCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    final Button btnConfirm = (Button) dialog.findViewById(R.id.btnConfirmOrder);
                    btnConfirm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        final String name = edtName.getText().toString();
                        final String phone = edtPhone.getText().toString();
                        final String email = edtEmail.getText().toString();
                        final String address = edtAddress.getText().toString();
                        final String note = edtNote.getText().toString();
                        if (name.equals(null) || name.equals("") ){

                            Toast.makeText(getContext(), "Empty Name", Toast.LENGTH_SHORT).show();
                        }
                        else if (phone.equals(null) || phone.equals("") ){

                                Toast.makeText(getContext(), "Empty Phone", Toast.LENGTH_SHORT).show();

                            }
                        else if(phone.length()<9 || phone.length()>11 ){
                            Toast.makeText(getContext(), " Invalid Phone", Toast.LENGTH_SHORT).show();
                        }
                        else if (email.equals(null) || email.equals("") ){

                            Toast.makeText(getContext(), "Empty Email", Toast.LENGTH_SHORT).show();

                        }
                        else if (address.equals(null) || address.equals("") ){

                            Toast.makeText(getContext(), "Empty Address", Toast.LENGTH_SHORT).show();

                        }
                        else {

                            String urlString = "http://booking.vihey.com/api/booking.php";
                            int countCartSelect= 0;
                            int currentCartSelect = getSelectCart();
                            dialog.dismiss();
                            ProgressDialog progress = new ProgressDialog(getContext());
                            progress.setTitle("Đang thực hiện");
                            progress.setMessage("Vui lòng đợi trong giây lát...");
                            progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
                            progress.show();

                            for (int i=0; i<inCartProductList.size(); i++){
                                if (inCartProductList.get(i).isChecked()){
                                    countCartSelect++;
                                    String num = String.valueOf(inCartProductList.get(i).getNum());
                                    String id = String.valueOf(inCartProductList.get(i).getProduct().getId());
                                    submitRequestOrder(progress,countCartSelect, currentCartSelect,urlString,name,phone,email,address,note,num,id);

                                }

                            }

                            //Log.e("Status: ",String.valueOf(sub));



                            }




                        }
                    });
                    dialog.show();
                }
                else {
                    new AlertDialog.Builder(getContext())
                            .setMessage("Vui lòng chọn danh mục cần đặt hàng?")
                            .setPositiveButton(R.string.yes, null)
                            .show();
                }


            }
        });






        return view;
    }
    private  List<InCartProduct> getListCartData() {

        List<InCartProduct> inCartProductList = new ArrayList<InCartProduct>();//Cart.getInstance().getProducts();
        InCartProduct product1 = new InCartProduct(new Product(11, "title1","100000000","https://style.vihey.com/uploads/product/4.jpg","shortDescription1", "longDescription"),11,false);
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
    private int  getSelectCart(){
        int count = 0;
        for (int i=0; i<inCartProductList.size(); i++){
            if (inCartProductList.get(i).isChecked()){
                count++;
            }

        }
       return count;
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
    private  boolean isCheckSelectCheckBox(){
        for (int i=0; i<inCartProductList.size(); i++){
            if (inCartProductList.get(i).isChecked()){
                return true;

            }

        }
        return false;
    }
    private  boolean notSelectAllCheckBox(){
        for (int i=0; i<inCartProductList.size(); i++){
            if (!(inCartProductList.get(i).isChecked())){
                return true;

            }

        }
        return false;
    }
    private void submitRequestOrder(final ProgressDialog progress,final int countSelectCart, final int currentSelectCart, final String url, final String name, final String sdt, final String email, final String diachi, final String ghichu, final String soluong, final String productid){
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response: ", response);
                try {
                    response=new String(response.getBytes("ISO-8859-1"), "UTF-8");
                    JSONObject object = new JSONObject(response);
                    int status = object.optInt("status");
                    if(status == 1 && countSelectCart == currentSelectCart){
                        progress.dismiss();
                        new AlertDialog.Builder(getContext())
                                .setMessage("Đặt hàng thành công")
                                .setPositiveButton(R.string.yes, null)
                                .show();
                        ChangeInCartProductList(inCartProductList);
                        txMyCart.setText("Giỏ hàng của tôi("+String.valueOf(inCartProductList.size())+")");
                        getPrice();
                        btnAllCheck.setChecked(false);

                    }
                    else if(status == 0){
                        new AlertDialog.Builder(getContext())
                                .setMessage("Đặt hàng không thành công")
                                .setPositiveButton(R.string.yes, null)
                                .show();


                    }

                } catch (Exception e) {

                }


            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                new AlertDialog.Builder(getContext())
                        .setMessage("Không thể thực hiện đặt hàng")
                        .setPositiveButton(R.string.yes, null)
                        .show();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("name", name);
                MyData.put("sdt", sdt);
                MyData.put("email", email);
                MyData.put("diachi", diachi);
                MyData.put("ghichu", ghichu);
                MyData.put("soluong", soluong);
                MyData.put("productid", productid);
                return MyData;
            }

        };
        
        rQueue.add(MyStringRequest);
    }


}
