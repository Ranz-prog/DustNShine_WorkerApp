package com.example.dnsworker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.dnsworker.fragment.HomeFragment;

public class CustomerDetails extends AppCompatActivity {

    private ImageView arrowBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_details);

        arrowBack = findViewById(R.id.ic_arrowback);

/*        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerDetails.this, HomeFragment.class);
                startActivity(intent);
                finish();
            }
        });*/
    }
}