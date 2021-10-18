package com.ennovation.taxwale.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ennovation.taxwale.Interfaces.ServiceInterface;
import com.ennovation.taxwale.Model.loginResponse;
import com.ennovation.taxwale.R;
import com.ennovation.taxwale.Utils.ApiClient;
import com.ennovation.taxwale.Utils.Helper;
import com.ennovation.taxwale.Utils.YourPreference;

import java.util.ArrayList;
import java.util.HashMap;

import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;
import retrofit2.Call;
import retrofit2.Callback;

public class OTPActivity extends AppCompatActivity {

    TextView btn_sendOTP,langugetext;
    ProgressBar mainProgressbar;
    EditText mobileNumber;

    SpinnerDialog spinnerDialog;
    ArrayList<String> list = new ArrayList<>();
    String language;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_t_p);

        mainProgressbar = findViewById(R.id.mainProgressbar);
        btn_sendOTP = findViewById(R.id.btn_sendOTP);
        mobileNumber = findViewById(R.id.mobileNumber);
        langugetext = findViewById(R.id.langugetext);

        btn_sendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mobileNumber.getText().toString().equalsIgnoreCase("")) {
                    mobileNumber.setError("*Required");
                } else if(mobileNumber.getText().toString().length() != 10) {
                    mobileNumber.setError("Required 10 Digit Number");
                } else {
                    if (Helper.INSTANCE.isNetworkAvailable(OTPActivity.this)){
                        doLogin();
                    } else {
                        Helper.INSTANCE.Error(OTPActivity.this, getString(R.string.NOCONN));
                    }
                }


            }
        });

        YourPreference yourPrefrence = YourPreference.getInstance(getApplicationContext());
        String language = yourPrefrence.getData("languagetext");
        if (language.equalsIgnoreCase("")) {
            langugetext.setText("English");
        } else {
            langugetext.setText(language);
        }

        list.add("English");
        list.add("Hindi");

        spinnerDialog = new SpinnerDialog(OTPActivity.this,
                list,
                "Select Language",
                R.style.DialogAnimations_SmileWindow,
                "Close  ");// With 	Animation
        spinnerDialog.setCancellable(true); // for cancellable
        spinnerDialog.setShowKeyboard(false);// for open keyboard by default

        languagechanger();

        langugetext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinnerDialog.showSpinerDialog();
            }
        });
    }

    private void doLogin() {
        HashMap<String, String> map = new HashMap<>();
        mainProgressbar.setVisibility(View.VISIBLE);
        map.put("mobile", mobileNumber.getText().toString());
        ServiceInterface serviceInterface = ApiClient.getClient().create(ServiceInterface.class);
        Call<loginResponse> call = serviceInterface.doLogin(map);
        call.enqueue(new Callback<loginResponse>() {
            @Override
            public void onResponse(Call<loginResponse> call, retrofit2.Response<loginResponse> response) {
                if (response.isSuccessful()) {
                    mainProgressbar.setVisibility(View.GONE);
                    String status = response.body().getStatus().toString();
                    if (status.equals("1")) {

                        YourPreference yourPrefrence = YourPreference.getInstance(getApplicationContext());
                        yourPrefrence.saveData("USERNAME",response.body().getName());
                        Intent intent = new Intent(OTPActivity.this, OTPVeryfication.class);
                        intent.putExtra("userName", response.body().getName());
                        intent.putExtra("userNumber",mobileNumber.getText().toString());
                        startActivity(intent);
                        finish();


                    } else {
                        Toast.makeText(OTPActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    mainProgressbar.setVisibility(View.GONE);
                    Toast.makeText(OTPActivity.this, "Something is wrong try again later", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<loginResponse> call, Throwable t) {
                Log.d("ff", t.toString());
                mainProgressbar.setVisibility(View.GONE);
            }
        });
    }

    private  void languagechanger() {
        spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, int position) {
                language = item;
                langugetext.setText(item);

                if (position==0) {
                    YourPreference yourPrefrence = YourPreference.getInstance(getApplicationContext());
                    yourPrefrence.saveData("languagetext",item);
                    yourPrefrence.saveData("language","en");
                    Toast.makeText(OTPActivity.this, "Selected Language is "+item, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), SplashActivity.class));
                    finishAffinity();
                } else if (position==1) {
                    YourPreference yourPrefrence = YourPreference.getInstance(getApplicationContext());
                    yourPrefrence.saveData("languagetext",item);
                    yourPrefrence.saveData("language","hi");
                    Toast.makeText(OTPActivity.this, "Selected Language is "+item, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), SplashActivity.class));
                    finishAffinity();
                }
//                else if (position==2)
//                {
//                    YourPreference yourPrefrence = YourPreference.getInstance(getApplicationContext());
//                    yourPrefrence.saveData("languagetext",item);
//                    yourPrefrence.saveData("language","ur");
//                    Toast.makeText(Profilemain.this, "Selected Language is "+item, Toast.LENGTH_SHORT).show();
//                    startActivity(new Intent(getApplicationContext(), Splashscreen.class));
//                    finishAffinity();
//                }
//                else if (position==3)
//                {
//                    YourPreference yourPrefrence = YourPreference.getInstance(getApplicationContext());
//                    yourPrefrence.saveData("languagetext",item);
//                    yourPrefrence.saveData("language","pa");
//                    Toast.makeText(Profilemain.this, "Selected Language is "+item, Toast.LENGTH_SHORT).show();
//                    startActivity(new Intent(getApplicationContext(), Splashscreen.class));
//                    finishAffinity();
//                }
//                else if (position==4)
//                {
//                    YourPreference yourPrefrence = YourPreference.getInstance(getApplicationContext());
//                    yourPrefrence.saveData("languagetext",item);
//                    yourPrefrence.saveData("language","bn");
//                    Toast.makeText(Profilemain.this, "Selected Language is "+item, Toast.LENGTH_SHORT).show();
//                    startActivity(new Intent(getApplicationContext(), Splashscreen.class));
//                    finishAffinity();
//                }
                else {
                    YourPreference yourPrefrence = YourPreference.getInstance(getApplicationContext());
                    yourPrefrence.saveData("languagetext",item);
                    yourPrefrence.saveData("language","en");
                    Toast.makeText(OTPActivity.this, "Selected Language is "+item, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), SplashActivity.class));
                    finishAffinity();
                }
            }
        });
    }
}