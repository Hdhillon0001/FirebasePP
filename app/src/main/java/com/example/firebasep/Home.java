package com.example.firebasep;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.firebasep.Prevalent.Prevalent;

import io.paperdb.Paper;

public class Home extends AppCompatActivity {
    private Button DonateButton,OrderButton,LogoutButton;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    DonateButton =  findViewById(R.id.donatebtn);
    OrderButton =  findViewById(R.id.orderbtn);
    LogoutButton =  findViewById(R.id.logoutbtn);

DonateButton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent =  new Intent (Home.this,Donate.class);
        startActivity(intent);
    }
});
LogoutButton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Paper.book().destroy();
        Intent intent= new Intent(Home.this,MainActivity.class);
        startActivity(intent);}

});


    OrderButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Home.this,Order.class);
            startActivity(intent);
        }
    });

        TextView userNameTextView =  findViewById(R.id.profileName);

        userNameTextView.setText(Prevalent.currentOnlineUser.getName());

    }



}
