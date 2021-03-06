package com.bku.appbooking.main.fragment.person;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bku.appbooking.R;
import com.bku.appbooking.main.MainActivity;
import com.bku.appbooking.ultis.CentralRequestQueue;
import com.bku.appbooking.ultis.UserInfo;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class PersonFragment extends Fragment implements View.OnClickListener {

    EditText txPersonName, txPersonPhone, txPersonEmail, txNewPass, txConfirmPass, txPersonAddress;
    Button btChangePerson, btCancel, btSaveChange, btChangePass, btCanCelChangePass, btConfirmChangePass;
    LinearLayout layoutChangePass;
    private CentralRequestQueue rQueue = CentralRequestQueue.getInstance();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_person, container, false);
        setUpView(view);

        getUserInfo();

        btChangePerson.setOnClickListener(this);
        btChangePass.setOnClickListener(this);
        btCancel.setOnClickListener(this);
        btSaveChange.setOnClickListener(this);
        btConfirmChangePass.setOnClickListener(this);
        btCanCelChangePass.setOnClickListener(this);

        return view;
    }

    private void getUserInfo(){
        String url = "http://booking.vihey.com/api/getuserinfo.php";
        requestGetUserInfo(url,UserInfo.getInstance().getAccessToken());
        audoFillTextView(UserInfo.getInstance().getName(), UserInfo.getInstance().getMail(), UserInfo.getInstance().getPhone(), UserInfo.getInstance().getAddress());
    }

    private void audoFillTextView(String name, String email, String phone, String address){
        txPersonName.setText(name);
        txPersonEmail.setText(email);
        txPersonPhone.setText(phone);
        txPersonAddress.setText(address);
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.btChangePerson:
                setChangePersonInfo();
                break;
            case R.id.btChangePass:
                layoutChangePass.setVisibility(View.VISIBLE);
                btSaveChange.setEnabled(false);
                break;
            case R.id.btCancel:
                setCancel();
                break;
            case R.id.btConfirmChangePass:
                confirmChangePass(
                        txNewPass.getText().toString(),
                        txConfirmPass.getText().toString());
                break;
            case R.id.btCancelChangePass:
                cancelChangePass();
                break;
            case R.id.btSaveChange:
                saveChange(
                        txPersonName.getText().toString(),
                        txPersonPhone.getText().toString(),
                        txPersonEmail.getText().toString(),
                        txPersonAddress.getText().toString());
                break;
            default:
                break;
        }
    }



    private void saveChange(String name, String phone, String email, String diachi){
        //check null info
        if (name.equals("") || email.equals("")){
            Toast.makeText(getContext(), "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        //kiem tra email
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (!email.matches(emailPattern)) {
            Toast.makeText(getContext(),"Email không hợp lệ",Toast.LENGTH_SHORT).show();
            return;
        }

        String url = "http://booking.vihey.com/api/updateuser.php";
        requestUpdateUserInfo(url, UserInfo.getInstance().getAccessToken(), name, email, phone, diachi);

    }

    private void cancelChangePass(){
        layoutChangePass.setVisibility(View.GONE);
        btSaveChange.setEnabled(true);
    }

    private void confirmChangePass(String newPass, String confirmPass){
        if (newPass.equals("") && confirmPass.equals("") ){
            Toast.makeText(getContext(), "Mật khẩu không hợp lệ", Toast.LENGTH_SHORT).show();
            return;
        }

        //kiem tra mat khau
        if (!newPass.equals(confirmPass)){
            Toast.makeText(getContext(), "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = "http://booking.vihey.com/api/changepassword.php";
        requestChangePassword(url, UserInfo.getInstance().getAccessToken(), newPass);

    }

    private void setCancel(){
        btChangePass.setEnabled(false);
        layoutChangePass.setVisibility(View.GONE);
        btChangePerson.setVisibility(View.VISIBLE);
        btSaveChange.setVisibility(View.GONE);
        btCancel.setVisibility(View.GONE);
    }

    private void setChangePersonInfo(){
        btChangePass.setEnabled(true);
        btChangePerson.setVisibility(View.GONE);
        btSaveChange.setVisibility(View.VISIBLE);
        btCancel.setVisibility(View.VISIBLE);
    }

    private void setUpView(View view){
        txPersonName = (EditText) view.findViewById(R.id.txPersonName);
        txPersonPhone = (EditText) view.findViewById(R.id.txPersonPhone);
        txPersonEmail = (EditText) view.findViewById(R.id.txPersonEmail);
        txNewPass = (EditText) view.findViewById(R.id.txNewPass);
        txConfirmPass = (EditText) view.findViewById(R.id.txConfirmPass);
        btChangePerson = (Button) view.findViewById(R.id.btChangePerson);
        btSaveChange = (Button) view.findViewById(R.id.btSaveChange);
        btChangePass = (Button) view.findViewById(R.id.btChangePass);
        btCancel = (Button) view.findViewById(R.id.btCancel);
        btCanCelChangePass = view.findViewById(R.id.btCancelChangePass);
        btConfirmChangePass = view.findViewById(R.id.btConfirmChangePass);
        layoutChangePass = (LinearLayout) view.findViewById(R.id.layoutChangePass);
        txPersonAddress = (EditText) view.findViewById(R.id.txPersonAddress);
    }

    private void requestChangePassword(final String url, final String accesstoken, final String newPass){
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response: ", response);
                try {
                    response=new String(response.getBytes("ISO-8859-1"), "UTF-8");
                    JSONObject object = new JSONObject(response);
                    int status = object.optInt("status");
                    if (status == 1){
                        if (getContext()== null) {
                            return;
                        }
                        final Dialog dialog = new Dialog(getContext());
                        dialog.setContentView(R.layout.dialog_infomation);
                        dialog.setCanceledOnTouchOutside(false);

                        Button btnConfirm = (Button) dialog.findViewById(R.id.btnConfirm);
                        TextView content = dialog.findViewById(R.id.dialog_content);
                        content.setText("Đổi mật khẩu thành công");
                        btnConfirm.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                    } else {
                        if (getContext()== null) {
                            return;
                        }
                        final Dialog dialog = new Dialog(getContext());
                        dialog.setContentView(R.layout.dialog_infomation);
                        dialog.setCanceledOnTouchOutside(false);

                        Button btnConfirm = (Button) dialog.findViewById(R.id.btnConfirm);
                        TextView content = dialog.findViewById(R.id.dialog_content);
                        content.setText("Đổi mật khẩu thất bại");
                        btnConfirm.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                    }

                } catch (Exception e) {

                }


            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                if (getContext()== null) {
                    return;
                }
                final Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.dialog_infomation);
                dialog.setCanceledOnTouchOutside(false);

                Button btnConfirm = (Button) dialog.findViewById(R.id.btnConfirm);
                TextView content = dialog.findViewById(R.id.dialog_content);
                content.setText("Xảy ra lỗi");
                btnConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("accesstoken", accesstoken);
                MyData.put("password", txNewPass.getText().toString());
                return MyData;
            }

        };

        rQueue.add(MyStringRequest);
    }

    private void requestGetUserInfo(final String url, final String accesstoken){
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response: ", response);
                try {
                    response=new String(response.getBytes("ISO-8859-1"), "UTF-8");
                    JSONObject object = new JSONObject(response);
                    String message = object.optString("message");
                    if ("Access granted.".equals(message)){
                        JSONObject objectData = new JSONObject(object.optString("data"));
                        UserInfo.getInstance().setName(objectData.getString("hoten"));
                        UserInfo.getInstance().setMail(objectData.getString("email"));
                        UserInfo.getInstance().setPhone(objectData.getString("sdt"));
                        UserInfo.getInstance().setAddress(objectData.getString("diachi"));
                        Log.e("UserInfo","get user info successs");
                    } else {
                        Log.e("UserInfo","get user info fail");
                        if (getContext()== null) {
                            return;
                        }
                        final Dialog dialog = new Dialog(getContext());
                        dialog.setContentView(R.layout.dialog_infomation);
                        dialog.setCanceledOnTouchOutside(false);

                        Button btnConfirm = (Button) dialog.findViewById(R.id.btnConfirm);
                        TextView content = dialog.findViewById(R.id.dialog_content);
                        content.setText("Không thể lấy thông tin người dùng");
                        btnConfirm.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                    }

                } catch (Exception e) {
                    Log.e("UserInfo2", String.valueOf(e));
                    Log.e("UserInfo2","get user info successs");
                }


            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("UserInfo1","get user info fail");
                if (getContext()== null) {
                    return;
                }
                final Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.dialog_infomation);
                dialog.setCanceledOnTouchOutside(false);

                Button btnConfirm = (Button) dialog.findViewById(R.id.btnConfirm);
                TextView content = dialog.findViewById(R.id.dialog_content);
                content.setText("Không thể lấy thông tin người dùng");
                btnConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("accesstoken", accesstoken);
                return MyData;
            }

        };

        rQueue.add(MyStringRequest);
    }

    private void requestUpdateUserInfo(final String url, final String accesstoken, final String name, final String email, final String phone, final String diachi){
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response: ", response);
                try {
                    response=new String(response.getBytes("ISO-8859-1"), "UTF-8");
                    JSONObject object = new JSONObject(response);
                    int status = object.optInt("status");
                    if (status == 1){
                        if (getContext()== null) {
                            return;
                        }
                        final Dialog dialog = new Dialog(getContext());
                        dialog.setContentView(R.layout.dialog_infomation);
                        dialog.setCanceledOnTouchOutside(false);

                        Button btnConfirm = (Button) dialog.findViewById(R.id.btnConfirm);
                        TextView content = dialog.findViewById(R.id.dialog_content);
                        content.setText("Cập nhật thành công");
                        btnConfirm.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                    } else {
                        if (getContext()== null) {
                            return;
                        }
                        final Dialog dialog = new Dialog(getContext());
                        dialog.setContentView(R.layout.dialog_infomation);
                        dialog.setCanceledOnTouchOutside(false);

                        Button btnConfirm = (Button) dialog.findViewById(R.id.btnConfirm);
                        TextView content = dialog.findViewById(R.id.dialog_content);
                        content.setText("Cập nhật thất bại");
                        btnConfirm.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                    }

                } catch (Exception e) {

                }


            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                if (getContext()== null) {
                    return;
                }
                final Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.dialog_infomation);
                dialog.setCanceledOnTouchOutside(false);

                Button btnConfirm = (Button) dialog.findViewById(R.id.btnConfirm);
                TextView content = dialog.findViewById(R.id.dialog_content);
                content.setText("Không thể cập nhật");
                btnConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("accesstoken", accesstoken);
                MyData.put("ten", name);
                MyData.put("sdt", phone);
                MyData.put("email", email);
                MyData.put("diachi", diachi);
                return MyData;
            }

        };

        rQueue.add(MyStringRequest);
    }

}
