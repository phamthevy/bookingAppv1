package com.bku.appbooking.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bku.appbooking.R;
import com.bku.appbooking.register.RegisterActivity;
import com.bku.appbooking.main.BookingActivity;

public class LoginActivity extends AppCompatActivity {

    EditText txEmail, txPassword;
    Button btLogin;
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

    public void onLogin(String email, String pass) {
        if (email.equals("") || pass.equals("")) {
            Toast.makeText(this, "Vui long dien day du thong tin", Toast.LENGTH_SHORT).show();
            return;
        }

        //TODO: Gui den sever, tra ve true - false

        String message = true ? "Dang nhap thanh cong" : "Dang nhap that bai, vui long thu lai";

        Toast.makeText(this, message, Toast.LENGTH_LONG).show();

        if (true){  //TODO: gui den server- tra ve true-false
            Intent intent = new Intent(this, BookingActivity.class);
            startActivity(intent);
        }
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
