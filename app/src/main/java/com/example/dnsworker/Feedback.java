package com.example.dnsworker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dnsworker.R;
import com.example.dnsworker.fragment.BookingFragment;

public class Feedback extends AppCompatActivity {

    private ImageView arrowback;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        arrowback = findViewById(R.id.ic_arrowbackFeeback);

/*        arrowback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BookingFragment.class);
                getApplicationContext().startActivity(intent);
                finish();
            }
        });*/
    }
}
