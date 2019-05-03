package com.bku.appbooking;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    EditText txName, txPhone, txEmailR, txPasswordR, txPassAgain;
    Button btRegister;
    ActionBar toolbar;
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
                register(txName.getText().toString(),
                        txPhone.getText().toString(),
                        txEmailR.getText().toString(),
                        txPasswordR.getText().toString(),
                        txPassAgain.getText().toString());
            }
        });
    }

    private void register(String name, String phone, String email, String pass, String passAgain) {
        //check null
        if (name.equals("") || phone.equals("") || email.equals("") || pass.equals("") || passAgain.equals("")){
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

        //TODO: gui den server- tra ve true-false

        String message = true ? "dang ki thanh cong" : "Dang ki that bai, vui long thu lai";

        Toast.makeText(this, message, Toast.LENGTH_LONG).show();

        //chuyen ve login activity
        if (false) { //TODO: sua lai sau khi co ket qua tra ve
            finish();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void setUpView() {
        txName = (EditText) findViewById(R.id.txName);
        txEmailR = (EditText) findViewById(R.id.txEmailR);
        txPassAgain = (EditText) findViewById(R.id.txPassAgain);
        txPhone = (EditText) findViewById(R.id.txPhone);
        txPasswordR = (EditText) findViewById(R.id.txPasswordR);
        btRegister = (Button) findViewById(R.id.btRegister);
    }
}
