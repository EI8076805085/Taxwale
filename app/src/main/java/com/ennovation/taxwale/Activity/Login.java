package com.ennovation.taxwale.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ennovation.taxwale.Interfaces.ServiceInterface;
import com.ennovation.taxwale.Model.loginResponse;
import com.ennovation.taxwale.R;
import com.ennovation.taxwale.Utils.ApiClient;
import com.ennovation.taxwale.Utils.Helper;
import com.ennovation.taxwale.Utils.YourPreference;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;

public class Login extends AppCompatActivity {

    TextView txt_getOTP,txt_signUp,txt_sendOTP;
    ImageView back_img;
    EditText ed_mobileNumber;
    ProgressBar mainProgressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txt_getOTP = findViewById(R.id.txt_getOTP);
        txt_signUp = findViewById(R.id.txt_signUp);
        txt_sendOTP = findViewById(R.id.txt_sendOTP);
        ed_mobileNumber = findViewById(R.id.ed_mobileNumber);
        mainProgressbar = findViewById(R.id.mainProgressbar);

        String sourceString = "We will send you an "+"<b>" + "<font color='#000000'>" + "OTP" + "</font>" + "</b> " + "<br>for Log In";
        txt_sendOTP.setText(Html.fromHtml(sourceString));

        back_img = findViewById(R.id.back_img);

        txt_getOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent intent = new Intent(Login.this, OTPActivity.class);
//                startActivity(intent);

                if (ed_mobileNumber.getText().toString().equalsIgnoreCase("")) {
                    ed_mobileNumber.setError("*Required");
                } else if(ed_mobileNumber.getText().toString().length() != 10) {
                    ed_mobileNumber.setError("Required 10 Digit Number");
                } else {
                    if (Helper.INSTANCE.isNetworkAvailable(Login.this)){
                        doLogin();
                    } else {
                        Helper.INSTANCE.Error(Login.this, getString(R.string.NOCONN));
                    }
                }

            }
        });

        txt_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, SignUpActivity.class);
                startActivity(intent);

            }
        });

        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }

    private void doLogin() {
        HashMap<String, String> map = new HashMap<>();
        mainProgressbar.setVisibility(View.VISIBLE);
        map.put("mobile", ed_mobileNumber.getText().toString());
        ServiceInterface serviceInterface = ApiClient.getClient().create(ServiceInterface.class);
        Call<loginResponse> call = serviceInterface.doLogin(map);
        call.enqueue(new Callback<loginResponse>() {
            @Override
            public void onResponse(Call<loginResponse> call, retrofit2.Response<loginResponse> response) {
                if (response.isSuccessful()) {
                    mainProgressbar.setVisibility(View.GONE);
                    String status = response.body().getStatus().toString();
                    if (status.equals("1")) {

//                        YourPreference yourPrefrence = YourPreference.getInstance(getApplicationContext());
//                        yourPrefrence.saveData("USERNAME",response.body().getName());
//                        Intent intent = new Intent(Login.this, OTPActivity.class);
//                        intent.putExtra("userName", response.body().getName());
//                        intent.putExtra("userNumber",ed_mobileNumber.getText().toString());
//                        startActivity(intent);
//                        finish();


                    } else {
                        Toast.makeText(Login.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    mainProgressbar.setVisibility(View.GONE);
                    Toast.makeText(Login.this, "Something is wrong try again later", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<loginResponse> call, Throwable t) {
                Log.d("ff", t.toString());
                mainProgressbar.setVisibility(View.GONE);
            }
        });
    }
}