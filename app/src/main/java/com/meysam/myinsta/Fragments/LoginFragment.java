package com.meysam.myinsta.Fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.meysam.myinsta.Data.RetrofitClient;
import com.meysam.myinsta.HomeActivity;
import com.meysam.myinsta.Models.JsonResponseModel;
import com.meysam.myinsta.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    private MaterialButton login,signup;
    private EditText user,pass;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        init(v);
        return v;
    }

    private void init(View v){

        user=v.findViewById(R.id.username_login);
        pass=v.findViewById(R.id.pass_login);

        login = v.findViewById(R.id.login_btn);
        signup = v.findViewById(R.id.signup_btn);
        onClicks();

    }

    private void onClicks(){

        login.setOnClickListener(v->{
            String u = user.getText().toString();
            String p = pass.getText().toString();
            if (u.isEmpty() || p.isEmpty()){
                Toast.makeText(getContext(), "Please Complete The Fields", Toast.LENGTH_SHORT).show();
            }else {
                doLogin(u,p);
            }
        });
    }

    private void doLogin(String username, String password) {

        RetrofitClient.getInstance().getApi()
                .loginUser(username,password)
                .enqueue(new Callback<JsonResponseModel>() {
                    @Override
                    public void onResponse(Call<JsonResponseModel> call, Response<JsonResponseModel> response) {

                        if (response.isSuccessful()){
                            Toast.makeText(getContext(), "Welcome", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getActivity(), HomeActivity.class));
                            getActivity().finish();
                        }else {
                            switch (response.code()){
                                case 400:
                                    Toast.makeText(getContext(), "User not found", Toast.LENGTH_SHORT).show();
                                    user.setText("");
                                    pass.setText("");
                                    break;
                                case 406:
                                    Toast.makeText(getContext(), "Wrong Password", Toast.LENGTH_SHORT).show();
                                    pass.setText("");
                                    break;
                                case 500:
                                    Toast.makeText(getContext(), "Error, Try Again!", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(getContext(), "Error, Try Again!", Toast.LENGTH_SHORT).show();
                                    break;
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
