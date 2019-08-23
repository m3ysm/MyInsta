package com.meysam.myinsta.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.meysam.myinsta.Activities.HomeActivity;
import com.meysam.myinsta.Classes.MySharedPreference;
import com.meysam.myinsta.Data.RetrofitClient;
import com.meysam.myinsta.Models.JsonResponseModel;
import com.meysam.myinsta.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {

    MaterialButton signup;
    EditText username, password, confirm;
    RadioGroup radioGroup;
    RadioButton rb;
    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_register, container, false);
        init(v);
        return v;
    }

    private void init(View v) {
        signup = v.findViewById(R.id.signup_btn);
        username = v.findViewById(R.id.username_signup);
        password = v.findViewById(R.id.pass_signup);
        confirm = v.findViewById(R.id.passconfirm_signup);
        radioGroup = v.findViewById(R.id.signup_radiogroupbtn);

        onClicks();
    }

    private void onClicks() {
        signup.setOnClickListener(v -> {
            String u = username.getText().toString();
            String p = password.getText().toString();
            String cp = confirm.getText().toString();

           int rbid = radioGroup.getCheckedRadioButtonId();
           View radiobotton = radioGroup.findViewById(rbid);
           int radioId = radioGroup.indexOfChild(radiobotton);
            rb = (RadioButton) radioGroup.getChildAt(radioId);
           String gender = rb.getText().toString();

            if (u.isEmpty() || p.isEmpty()) {
                Toast.makeText(getContext(), "Please Complete The Fields", Toast.LENGTH_SHORT).show();
            } else if (!p.equals(cp)) {
                Toast.makeText(getContext(), "Password Not matching", Toast.LENGTH_SHORT).show();
                confirm.setText("");
            } else {
                doSignup(gender, u, p);
            }
        });
    }

    private void doSignup(String gender, String username, String password) {

        RetrofitClient.getInstance().getApi()
                .registerUser(gender, username, password)
                .enqueue(new Callback<JsonResponseModel>() {
                    @Override
                    public void onResponse(Call<JsonResponseModel> call, Response<JsonResponseModel> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getContext(), "Welcome"+username, Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getActivity(), HomeActivity.class));
                            MySharedPreference.getInstance(getContext()).setIsLogin();
                            MySharedPreference.getInstance(getContext()).setUser(username);
                            getActivity().finish();
                        } else {
                            switch (response.code()) {
                                case 409:
                                    Toast.makeText(getContext(), "User Exist", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonResponseModel> call, Throwable t) {
                        Toast.makeText(getContext(), "Error, Try Again!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
