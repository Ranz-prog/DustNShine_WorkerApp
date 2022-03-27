package com.example.dnsworker;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.material.textfield.TextInputLayout;

public class ReceivedPayment extends AppCompatActivity {


    TextView receive_clientname, receive_schedule, receive_location, receive_contact, recieve_total;
    String client_firstname, client_lastname, clientschedule, clientlocation, clientcontact, client_totalcost;
    TextInputLayout receive_amount_ET;
    SharedPreferences preferences;
    Button received_btn;
    private Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_received_payment);

        receive_clientname = findViewById(R.id.received_customername);
        receive_schedule = findViewById(R.id.received_customerschedule);
        receive_location = findViewById(R.id.received_customeraddress);
        receive_contact = findViewById(R.id.received_customernumber);
        recieve_total = findViewById(R.id.received_totalcost_value);
        receive_amount_ET = findViewById(R.id.amountInputLayout);
        received_btn = findViewById(R.id.btn_ReceivedPayment);

        dialog = new Dialog(this);

        preferences = getSharedPreferences("CUSTOMER_DATA", MODE_PRIVATE);

        client_firstname = preferences.getString("first_name", null);
        client_lastname = preferences.getString("last_name", null);
        clientschedule = preferences.getString("sched_datetime", null);
        clientlocation = preferences.getString("address", null);
        clientcontact = preferences.getString("mobile_number", null);
        client_totalcost = preferences.getString("total", null);

        receive_clientname.setText(client_firstname + " " + client_lastname);
        receive_schedule.setText(clientschedule);
        receive_location.setText(clientlocation);
        receive_contact.setText(clientcontact);
        recieve_total.setText(client_totalcost);

        String valuecost  = client_totalcost.replace(".0", "");
        received_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (receive_amount_ET.getEditText().getText().toString().equals(valuecost)){
                    Log.d(TAG, "onClick: Matched!");
                    confirmationDialog();

                } else if (receive_amount_ET.getEditText().getText().toString().isEmpty()){
                    receive_amount_ET.setErrorEnabled(true);
                    receive_amount_ET.setError("Enter amount value!");
                }
                else{
                    receive_amount_ET.setErrorEnabled(true);
                    receive_amount_ET.setError("Amount not matched! Please enter exact amount");
                }
            }
        });

    }

    private void confirmationDialog() {
        dialog.setContentView(R.layout.dialog_confirmation);
        dialog.getWindow()
                .setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView btnDone = (TextView) dialog.findViewById(R.id.confirmButton);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReceivedPayment.this, MainMenu.class);
                startActivity(intent);
                finish();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void warningDialog(){
        dialog.setContentView(R.layout.dialog_error_email);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView btnDone = (TextView) dialog.findViewById(R.id.dismissButton);
        TextView warningMessage = (TextView) dialog.findViewById(R.id.warningMessage);
        warningMessage.setText("Please Enter the received amount to proceed and exit.");
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void onBackPressed(){
        warningDialog();
    }


}