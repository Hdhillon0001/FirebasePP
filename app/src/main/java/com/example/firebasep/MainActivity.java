package com.example.firebasep;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.firebasep.Model.Users;
import com.example.firebasep.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {
    public Button loginBtn, register;
    private ProgressDialog loadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        register = (Button) findViewById(R.id.Joinbtn);
        loginBtn = (Button) findViewById(R.id.Loginbtn1);
loadingBar  = new ProgressDialog(this);
        Paper.init(this);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, login.class);
                startActivity(intent);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, register.class);
                startActivity(intent);
            }
        });

    String UserPhoneKey = Paper.book().read(Prevalent.UserPhoneKey);
    String UserPasswordKey = Paper.book().read(Prevalent.UserPasswordKey);

    if(UserPhoneKey != null && UserPasswordKey != null)
    {
        if (!TextUtils.isEmpty(UserPhoneKey) && !TextUtils.isEmpty(UserPasswordKey)){

AllowAccess(UserPhoneKey,UserPasswordKey);
            loadingBar.setTitle("Already Logged in");
            loadingBar.setMessage("Loading...");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            }
        }
    }
    private void AllowAccess(final String phone, final String password){

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("Users").child(password).exists()) {
                    Users userData = dataSnapshot.child("Users").child(phone).getValue(Users.class);

                    if (userData.getPhone().equals(phone)) {

                        if (userData.getPassword().equals(password)) {
                            Toast.makeText(MainActivity.this, " logged in successfully ....", Toast.LENGTH_LONG).show();
                            loadingBar.dismiss();
                            Intent intent = new Intent(MainActivity.this, Home.class);
                            startActivity(intent);
                        }else
                        {

                            Toast.makeText(MainActivity.this, " Pssss. wrong Password fella ....", Toast.LENGTH_LONG).show();

                        }
                    }

                } else {
                    Toast.makeText(MainActivity.this, "Logged out", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
