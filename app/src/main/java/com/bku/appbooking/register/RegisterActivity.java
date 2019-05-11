package com.bku.appbooking.register;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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
import com.bku.appbooking.login.LoginActivity;
import com.bku.appbooking.main.MainActivity;
import com.bku.appbooking.ultis.CentralRequestQueue;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    EditText txUsername, txName, txPhone, txEmailR, txPasswordR, txPassAgain;
    Button btRegister;
    ActionBar toolbar;
    private CentralRequestQueue rQueue = CentralRequestQueue.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        setUpView();

        toolbar = getSupportActionBar();
        toolbar.setDisplayHomeAsUpEnabled(true);


        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register(txUsername.getText().toString(),
                        txName.getText().toString(),
                        txPhone.getText().toString(),
                        txEmailR.getText().toString(),
                        txPasswordR.getText().toString(),
                        txPassAgain.getText().toString());
            }
        });
    }

    private void register(final String username, final String name, final String phone, final String email, final String pass, final String passAgain) {
        //check null
        if (username.equals("") || name.equals("") || phone.equals("") || email.equals("") || pass.equals("") || passAgain.equals("")){
            Toast.makeText(this, "Vui long dien day du thong tin", Toast.LENGTH_SHORT).show();
            return;
        }

        //kiem tra mat khau
        if (!pass.equals(passAgain)){
            Toast.makeText(this, "Vui long nhap lai mat khau", Toast.LENGTH_SHORT).show();
            return;
        }

        //kiem tra email
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (!email.matches(emailPattern)) {
            Toast.makeText(getApplicationContext(),"Email khong hop le",Toast.LENGTH_SHORT).show();
            return;
        }

        String urlRegister = "http://booking.vihey.com/api/register.php";

        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, urlRegister, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    response=new String(response.getBytes("ISO-8859-1"), "UTF-8");
                    JSONObject object = new JSONObject(response);
                    int status = object.optInt("status");
                    Log.e("Status: ", String.valueOf(status));
                    if (status == 1) {
                        Toast.makeText(getApplicationContext(), "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);


                    }
                    else if (status == 2){
                        Toast.makeText(getApplicationContext(), "Tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Đăng ký không thành công", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Đăng ký không thành công", Toast.LENGTH_SHORT).show();

                }


            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Đăng ký không thành công", Toast.LENGTH_SHORT).show();

            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("username", username);
                MyData.put("password", passAgain);
                MyData.put("hoten", name);
                MyData.put("sdt", phone);
                MyData.put("email", email);

                return MyData;
            }

        };

        rQueue.add(MyStringRequest);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void setUpView() {
        txUsername = (EditText) findViewById(R.id.txUsername);
        txName = (EditText) findViewById(R.id.txName);
        txEmailR = (EditText) findViewById(R.id.txEmailR);
        txPassAgain = (EditText) findViewById(R.id.txPassAgain);
        txPhone = (EditText) findViewById(R.id.txPhone);
        txPasswordR = (EditText) findViewById(R.id.txPasswordR);
        btRegister = (Button) findViewById(R.id.btRegister);
    }
}
