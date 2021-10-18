package com.ennovation.taxwale.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ennovation.taxwale.Interfaces.ServiceInterface;
import com.ennovation.taxwale.Model.SignUpResponse;
import com.ennovation.taxwale.R;
import com.ennovation.taxwale.Utils.ApiClient;
import com.ennovation.taxwale.Utils.Helper;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;

public class SignUpActivity extends AppCompatActivity {
    TextView btn_next;
    ImageView back_img;
    EditText ed_userName,ed_userMobileNumber;
    ProgressBar mainProgressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        btn_next = findViewById(R.id.btn_next);
         ed_userName = findViewById(R.id.ed_userName);
        ed_userMobileNumber = findViewById(R.id.ed_userMobileNumber);
        mainProgressbar = findViewById(R.id.mainProgressbar);


        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ed_userName.getText().toString().equalsIgnoreCase("")) {
                    ed_userName.setError("*Required");
                } else if (ed_userMobileNumber.getText().toString().equalsIgnoreCase("")) {
                    ed_userMobileNumber.setError("*Required");
                } else if(ed_userMobileNumber.getText().toString().length() != 10) {
                    ed_userMobileNumber.setError("Required 10 Digit Number");
                } else {
                    if (Helper.INSTANCE.isNetworkAvailable(getApplicationContext())){
                        doSignUp();
                    } else {
                        Helper.INSTANCE.Error(getApplicationContext(), getString(R.string.NOCONN));
                    }
                }
            }
        });





    }

    private void doSignUp() {

        HashMap<String, String> map = new HashMap<>();
        mainProgressbar.setVisibility(View.VISIBLE);
        map.put("mobile", ed_userMobileNumber.getText().toString());
        ServiceInterface serviceInterface = ApiClient.getClient().create(ServiceInterface.class);
        Call<SignUpResponse> call = serviceInterface.doSignup(map);
        call.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, retrofit2.Response<SignUpResponse> response) {
                if (response.isSuccessful()) {
                    mainProgressbar.setVisibility(View.GONE);
                    String status = response.body().getStatus().toString();
                    if (status.equals("1")) {
                        Intent intent = new Intent(SignUpActivity.this, OTPVeryfication.class);
                        intent.putExtra("userName",ed_userName.getText().toString());
                        intent.putExtra("userNumber",ed_userMobileNumber.getText().toString());
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(SignUpActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    mainProgressbar.setVisibility(View.GONE);
                    Toast.makeText(SignUpActivity.this, "Something is wrong try again later", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                Log.d("ff", t.toString());
                mainProgressbar.setVisibility(View.GONE);
            }
        });
    }




}