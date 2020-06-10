package com.example.firebasep;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.firebasep.Model.Users;
import com.example.firebasep.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class login extends AppCompatActivity {
    private Button registertbutton, loginbutton;
    private EditText Phone, Passwordid;
    private ProgressDialog loadingBar;
    private String parentName = "Users";
    private CheckBox RememberMe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        registertbutton = (Button) findViewById(R.id.Registerbtn);
        loginbutton = (Button) findViewById(R.id.Loginbtn);
        Phone = (EditText) findViewById(R.id.phoneid);
        Passwordid = (EditText) findViewById(R.id.passwordid);
        loadingBar = new ProgressDialog(this);
    RememberMe = (CheckBox)findViewById(R.id.remenbermechkbx);
        Paper.init(this);
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LoginUser();
            }
        });
        registertbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login.this, register.class);
                startActivity(intent);
            }
        });


    }

    private void LoginUser() {
        String phone = Phone.getText().toString();
        String password = Passwordid.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "Please write your Phone Number..", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please create your password..", Toast.LENGTH_SHORT).show();
        } else {

            loadingBar.setTitle("Login Account");
            loadingBar.setMessage("Loading...");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            AllowAccess(phone, password);
        }
    }

    private void AllowAccess(final String phone, final String password) {
        if(RememberMe.isChecked()){
            Paper.book().write(Prevalent.UserPhoneKey,phone);
            Paper.book().write(Prevalent.UserPasswordKey,password);


        }
        else{

        }


        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(parentName).child(password).exists()) {
                    Users userData = dataSnapshot.child(parentName).child(phone).getValue(Users.class);

                    if (userData.getPhone().equals(phone)) {

                        if (userData.getPassword().equals(password)) {
                            Toast.makeText(login.this, " logged in successfully ....", Toast.LENGTH_LONG).show();
                            loadingBar.dismiss();
                            Intent intent = new Intent(login.this, Home.class);
                            startActivity(intent);
                        }else
                            {

                                Toast.makeText(login.this, " Pssss. wrong Password fella ....", Toast.LENGTH_LONG).show();

                            }
                    }

                } else {
                    Toast.makeText(login.this, "login error", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
