package com.example.firebasep;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Home extends AppCompatActivity {
    private Button DonateButton,OrderButton;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    DonateButton = (Button) findViewById(R.id.donatebtn);
    OrderButton = (Button) findViewById(R.id.orderbtn);

DonateButton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent =  new Intent (Home.this,Donate.class);
        startActivity(intent);
    }
});

        ///
        ///
        ///
//need to add logout button here .nodnjsncj
        ///
        ////
        /////
    OrderButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Home.this,Order.class);
            startActivity(intent);
        }
    });
    }



}
