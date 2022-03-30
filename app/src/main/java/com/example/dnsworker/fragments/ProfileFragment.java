package com.example.dnsworker.fragments;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import com.example.dnsworker.LoginPage;
import com.example.dnsworker.Model.User.User;
import com.example.dnsworker.R;
import com.example.dnsworker.ViewModel.UserViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Map;

public class ProfileFragment  extends Fragment {

    private View view;
    private TextInputEditText worker_fname,
                              worker_email, worker_contact;
    private AppCompatButton btnLogout;
    private  String retrievedToken;
    LinearLayout appIcon;
    SharedPreferences preferences;
    UserViewModel userViewModel;
    TextView noInternetProfileTV, profileSubtitle;
    ImageView noInternetProfileImage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_profile, container, false);

        btnLogout = view.findViewById(R.id.btnLogout);
        worker_fname = view.findViewById(R.id.worker_fname_ET);
        worker_email = view.findViewById(R.id.worker_email_ET);
        worker_contact = view.findViewById(R.id.worker_contact_ET);
        profileSubtitle = view.findViewById(R.id.profile_subtitle);
        noInternetProfileTV = view.findViewById(R.id.noInternetConnectionProfileTV);
        noInternetProfileImage = view.findViewById(R.id.noInternetProfileImage);
        appIcon = view.findViewById(R.id.appIconProfile);

        userViewModel = new UserViewModel();

        preferences = getActivity().getSharedPreferences("AUTH_TOKEN", Context.MODE_PRIVATE);
        retrievedToken = preferences.getString("TOKEN", null);

        if (!isConnected()){
            noInternetProfileImage.setVisibility(View.VISIBLE);
            noInternetProfileTV.setVisibility(View.VISIBLE);
            worker_fname.setVisibility(View.GONE);
            worker_email.setVisibility(View.GONE);
            worker_contact.setVisibility(View.GONE);
            appIcon.setVisibility(View.GONE);
            btnLogout.setVisibility(View.GONE);
            profileSubtitle.setVisibility(View.GONE);
        }
        else{
            noInternetProfileImage.setVisibility(View.GONE);
            noInternetProfileTV.setVisibility(View.GONE);
            worker_fname.setVisibility(View.VISIBLE);
            worker_email.setVisibility(View.VISIBLE);
            worker_contact.setVisibility(View.VISIBLE);
            appIcon.setVisibility(View.VISIBLE);
            btnLogout.setVisibility(View.VISIBLE);
            profileSubtitle.setVisibility(View.VISIBLE);
        }

        getUserInfo();

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Are you sure you want to log out?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                logout();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();

            }
        });

        return view;

    }

    private void getUserInfo(){
        userViewModel.getUserDataResponse(retrievedToken).observe(getActivity(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                worker_fname.setText(user.getFirst_name() + " " + user.getLast_name());
                worker_email.setText(user.getEmail());
                worker_contact.setText(user.getMobile_number());
            }
        });
    }

    private void logout(){
        userViewModel.getSignoutResponse(retrievedToken).observe(getActivity(), new Observer<Map<String, String>>() {
            @Override
            public void onChanged(Map<String, String> stringStringMap) {

                SharedPreferences.Editor editor = preferences.edit();
                editor.remove("TOKEN").apply();
                Intent intent = new Intent(getContext(), LoginPage.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                getActivity().finish();

            }
        });
    }

    public boolean isConnected(){
        ConnectivityManager manager = (ConnectivityManager) getActivity().getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        return manager.getActiveNetworkInfo()!= null && manager.getActiveNetworkInfo().isConnectedOrConnecting();
    }

}
