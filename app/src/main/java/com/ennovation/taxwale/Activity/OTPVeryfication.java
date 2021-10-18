package com.ennovation.taxwale.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ennovation.taxwale.Interfaces.ServiceInterface;
import com.ennovation.taxwale.Model.loginResponse;
import com.ennovation.taxwale.Model.otpverifymodel;
import com.ennovation.taxwale.R;
import com.ennovation.taxwale.Utils.ApiClient;
import com.ennovation.taxwale.Utils.YourPreference;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;

public class OTPVeryfication extends AppCompatActivity {
    TextView txt_verify,txt_timeCounter;

    ProgressBar mainProgressbar;
    EditText ed1, ed2, ed3, ed4;
    String otp="0000";
     String userName;
    String userNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_t_p_veryfication);
        mainProgressbar = findViewById(R.id.mainProgressbar);
        ed1 = findViewById(R.id.ed1);
        ed2 = findViewById(R.id.ed2);
        ed3 = findViewById(R.id.ed3);
        ed4 = findViewById(R.id.ed4);
        txt_verify = findViewById(R.id.txt_verify);
        txt_timeCounter = findViewById(R.id.txt_timeCounter);
        ed1.addTextChangedListener(new GenericTextWatcher(ed2, ed1));
        ed2.addTextChangedListener(new GenericTextWatcher(ed3, ed1));
        ed3.addTextChangedListener(new GenericTextWatcher(ed4, ed2));
        ed4.addTextChangedListener(new GenericTextWatcher(ed4, ed3));

          userName   =getIntent().getExtras().getString("userName");
          userNumber  =getIntent().getExtras().getString("userNumber");

        verifyotp();

        new CountDownTimer(30000, 1000) {
            public void onTick(long millisUntilFinished) {
                txt_timeCounter.setText("otp auto resend in "+millisUntilFinished / 1000+" sec");
            }
            public void onFinish() {

            }
        }.start();


        txt_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otp=ed1.getText().toString()+ed2.getText().toString()+ed3.getText().toString()+ed4.getText().toString();
                if (otp.length()!=4)
                {
                    Toast.makeText(OTPVeryfication.this, "Required 4 Digit OTP", Toast.LENGTH_SHORT).show();
                }else
                {

                    verifyotp();

                }



            }
        });



    }

    private void verifyotp() {
        HashMap<String, String> map = new HashMap<>();
        mainProgressbar.setVisibility(View.VISIBLE);
        map.put("otp", otp);
        map.put("name", userName);
        map.put("mobile", userNumber);
        map.put("device_id", "N/A");
        ServiceInterface serviceInterface = ApiClient.getClient().create(ServiceInterface.class);
        Call<otpverifymodel> call = serviceInterface.verifyotp(map);
        call.enqueue(new Callback<otpverifymodel>() {
            @Override
            public void onResponse(Call<otpverifymodel> call, retrofit2.Response<otpverifymodel> response) {
                if (response.isSuccessful()) {
                    mainProgressbar.setVisibility(View.GONE);
                    String status = response.body().getStatus().toString();
                    if (status.equals("1")) {
                        YourPreference yourPrefrence = YourPreference.getInstance(getApplicationContext());
                        yourPrefrence.saveData("USERNAME",response.body().getName());
                        String user_id=response.body().getId();
                        yourPrefrence.saveData("User_id",user_id);

                        Intent intent = new Intent(OTPVeryfication.this, IntroSliderActivity.class);
                        startActivity(intent);



                    } else {
                        Toast.makeText(OTPVeryfication.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    mainProgressbar.setVisibility(View.GONE);
                    Toast.makeText(OTPVeryfication.this, "Something is wrong try again later", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<otpverifymodel> call, Throwable t) {
                Log.d("ff", t.toString());
                mainProgressbar.setVisibility(View.GONE);
            }
        });
    }

    public class GenericTextWatcher implements TextWatcher {
        private EditText etPrev;
        private EditText etNext;

        public GenericTextWatcher(EditText etNext, EditText etPrev) {
            this.etPrev = etPrev;
            this.etNext = etNext;
        }

        @Override
        public void afterTextChanged(Editable editable) {
            String text = editable.toString();
            if (text.length() == 1)
                etNext.requestFocus();
            else if (text.length() == 0)
                etPrev.requestFocus();

        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
        }

        @Override
        public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

        }

    }

}