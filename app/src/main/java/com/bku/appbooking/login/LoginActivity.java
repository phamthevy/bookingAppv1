package com.bku.appbooking.login;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bku.appbooking.R;
import com.bku.appbooking.register.RegisterActivity;
import com.bku.appbooking.main.MainActivity;
import com.bku.appbooking.ultis.CentralRequestQueue;
import com.bku.appbooking.ultis.UserInfo;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText txEmail, txPassword;
    Button btLogin;
    private CentralRequestQueue rQueue = CentralRequestQueue.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setUpView();
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLogin(txEmail.getText().toString(), txPassword.getText().toString());
            }
        });

    }

    public void onLogin(final String email, final String pass) {
        if (email.equals("") || pass.equals("")) {
            Toast.makeText(this, "Vui long dien day du thong tin", Toast.LENGTH_SHORT).show();
            return;
        }

        String urlLogin = "http://booking.vihey.com/api/login.php";

            StringRequest MyStringRequest = new StringRequest(Request.Method.POST, urlLogin, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        response=new String(response.getBytes("ISO-8859-1"), "UTF-8");
                        JSONObject object = new JSONObject(response);
                        int status = object.optInt("status");
                        if (status == 1) {
                            String accessToken = object.optString("accesstoken");
                            String name = object.optString("hoten");
                            String email = object.optString("email");
                            Toast.makeText(getApplicationContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                            UserInfo.getInstance().setAccessToken(accessToken);
                            UserInfo.getInstance().setName(name);
                            UserInfo.getInstance().setPass(pass);
                            UserInfo.getInstance().setMail(email);
                            LoginActivity.super.onBackPressed();
                        }
                        if(status == 0){
                            Toast.makeText(getApplicationContext(), "Tên tài khoản hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();

                    }


                }
            }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();

                }
            }) {
                protected Map<String, String> getParams() {
                    Map<String, String> MyData = new HashMap<String, String>();
                    MyData.put("username", email);
                    MyData.put("password", pass);

                    return MyData;
                }

            };

            rQueue.add(MyStringRequest);

    }

    public void onRegister(View v) {
        // Chuyen den Register Activity

        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    private void setUpView(){
        txEmail = (EditText) findViewById(R.id.txEmail);
        txPassword = (EditText) findViewById(R.id.txPassword);
        btLogin = (Button) findViewById(R.id.btLogin);
    }
}
