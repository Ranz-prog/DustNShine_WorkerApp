package com.example.dnsworker.fragments;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.dnsworker.API.APIClient;
import com.example.dnsworker.LoginPage;
import com.example.dnsworker.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment  extends Fragment {

    private View view;

    private TextInputEditText worker_fname, worker_lname,
                              worker_email, worker_contact,
                              worker_housenumber, worker_street,
                              workker_brgy, worker_municipality,
                              worker_province, worker_zipcode;

    private Button btnLogout;

    private  String retrievedToken;
    SharedPreferences preferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_profile, container, false);

        btnLogout = view.findViewById(R.id.btnLogout);
        worker_fname = view.findViewById(R.id.worker_fname_ET);
        worker_lname = view.findViewById(R.id.worker_lname_ET);
        worker_email = view.findViewById(R.id.worker_email_ET);
        worker_contact = view.findViewById(R.id.worker_contact_ET);
        worker_housenumber = view.findViewById(R.id.worker_houseno_ET);
        worker_street = view.findViewById(R.id.worker_street_ET);
        workker_brgy = view.findViewById(R.id.worker_brgy_ET);
        worker_province = view.findViewById(R.id.worker_province_ET);
        worker_zipcode = view.findViewById(R.id.worker_zipcode_ET);


        preferences = getActivity().getSharedPreferences("AUTH_TOKEN", Context.MODE_PRIVATE);
        retrievedToken = preferences.getString("TOKEN", null);


        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });


        return view;

    }

    private void logout(){

        Call<Map<String, String>> logoutRequest =  APIClient.getUserService().userLogout("Bearer " + retrievedToken);
        logoutRequest.enqueue(new Callback<Map<String, String>>() {
            @Override
            public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                if (response.isSuccessful()){

                    SharedPreferences.Editor editor = preferences.edit();
                    editor.remove("TOKEN").apply();

                    Log.d(TAG, "onResponse: " +  response.body().get("message"));

                    startActivity(new Intent(getContext(), LoginPage.class));
                    getActivity().finish();
                }
            }

            @Override
            public void onFailure(Call<Map<String, String>> call, Throwable t) {
                Toast.makeText(getContext(), "Logout Failed", Toast.LENGTH_LONG);

            }
        });

    }
}
