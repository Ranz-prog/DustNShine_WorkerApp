package com.example.dnsworker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomerDetails extends AppCompatActivity {

    private ImageView arrowBack;
    private Button startWorkButton;

    private TextView tokenText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_details);

        arrowBack = findViewById(R.id.ic_arrowback);
        startWorkButton = findViewById(R.id.startWorkButton);


//        tokenText = findViewById(R.id.forToken);
//        Intent intent = getIntent();
//
//        if (intent.getExtras() != null){
//            String token = intent.getStringExtra("data");
//            tokenText.setText(token);
//
//            System.out.print("TOKEN: " + tokenText.getText().toString());
//        }


        startWorkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerDetails.this, ServiceDetails.class);
                startActivity(intent);
            }
        });

        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}