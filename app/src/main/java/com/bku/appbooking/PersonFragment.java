package com.bku.appbooking;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class PersonFragment extends Fragment implements View.OnClickListener {

    EditText txPersonName, txPersonPhone, txPersonEmail, txCurrentPass, txNewPass, txConfirmPass;
    Button btChangePerson, btCancel, btSaveChange, btChangePass, btLogout;
    public boolean isChangePass = false;
    LinearLayout layoutChangePass;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_person, container, false);
        setUpView(view);

        //TODO: lay thong tin tu server de fill vao thong tin
        //fillInfo(view);

        btChangePerson.setOnClickListener(this);
        btChangePass.setOnClickListener(this);
        btCancel.setOnClickListener(this);
        btSaveChange.setOnClickListener(this);
        btLogout.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.btChangePerson:
                setChangePersonInfo();
                break;
            case R.id.btChangePass:
                layoutChangePass.setVisibility(View.VISIBLE);
                break;
            case R.id.btCancel:
                setCancel();
                break;
            case R.id.btlogOut:
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.btSaveChange:
                saveChange(
                        txPersonName.getText().toString(),
                        txPersonPhone.getText().toString(),
                        txPersonEmail.getText().toString(),
                        txCurrentPass.getText().toString(),
                        txNewPass.getText().toString(),
                        txConfirmPass.getText().toString());
                break;
            default:
                break;
        }
    }

    private void saveChange(String name, String phone, String email, String currentPass, String newPass, String confirmPass){
        //check null info
        if (name.equals("") || email.equals("")){
            Toast.makeText(getContext(), "Khong the de thong tin trong", Toast.LENGTH_SHORT).show();
            return;
        }

        //kiem tra email
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (!email.matches(emailPattern)) {
            Toast.makeText(getContext(),"Email khong hop le",Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isChangePass){
            //TODO: send to sever
            //send(name,phone,email)
        }
        else {
            if (currentPass.equals("") || newPass.equals("") || confirmPass.equals("")){
                Toast.makeText(getContext(), "Khong the de thong tin trong", Toast.LENGTH_SHORT).show();
                return;
            }

            //kiem tra mat khau
            if (!newPass.equals(confirmPass)){
                Toast.makeText(getContext(), "Vui long nhap lai mat khau", Toast.LENGTH_SHORT).show();
                return;
            }

            //TODO: send to sever
            //send(name,phone,email, currentPass, newPass, confirmPass)
        }
    }

    private void setCancel(){
        //fillInfo(view);
        txPersonName.setEnabled(false);
        txPersonName.setEnabled(false);
        txPersonName.setEnabled(false);
        btChangePass.setEnabled(false);
        layoutChangePass.setVisibility(View.GONE);
        btChangePerson.setVisibility(View.VISIBLE);
        btSaveChange.setVisibility(View.GONE);
        btCancel.setVisibility(View.GONE);
    }

    private void setChangePersonInfo(){
        txPersonName.setEnabled(true);
        txPersonName.setEnabled(true);
        txPersonName.setEnabled(true);
        btChangePass.setEnabled(true);
        btChangePerson.setVisibility(View.GONE);
        btSaveChange.setVisibility(View.VISIBLE);
        btCancel.setVisibility(View.VISIBLE);
    }

    private void setUpView(View view){
        txPersonName = (EditText) view.findViewById(R.id.txPersonName);
        txPersonPhone = (EditText) view.findViewById(R.id.txPersonPhone);
        txPersonEmail = (EditText) view.findViewById(R.id.txPersonEmail);
        txCurrentPass = (EditText) view.findViewById(R.id.txCurrentPass);
        txNewPass = (EditText) view.findViewById(R.id.txNewPass);
        txConfirmPass = (EditText) view.findViewById(R.id.txConfirmPass);
        btChangePerson = (Button) view.findViewById(R.id.btChangePerson);
        btSaveChange = (Button) view.findViewById(R.id.btSaveChange);
        btChangePass = (Button) view.findViewById(R.id.btChangePass);
        btCancel = (Button) view.findViewById(R.id.btCancel);
        btLogout = (Button)view.findViewById(R.id.btlogOut);
        layoutChangePass = (LinearLayout) view.findViewById(R.id.layoutChangePass);
    }

}
