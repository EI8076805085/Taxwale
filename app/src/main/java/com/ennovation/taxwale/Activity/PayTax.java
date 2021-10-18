package com.ennovation.taxwale.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.ennovation.taxwale.MainActivity;
import com.ennovation.taxwale.R;

public class PayTax extends AppCompatActivity {

    ImageView img_payChallan,img_payOnline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_tax);


        img_payChallan = findViewById(R.id.img_payChallan);
        img_payOnline = findViewById(R.id.img_payOnline);


        img_payChallan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PayTax.this, ManualPayChallanActivity.class);
                startActivity(intent);
            }
        });


        img_payOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PayTax.this, PayOnlineActivity.class);
                startActivity(intent);
            }
        });

    }

    public void back(View view) {
        onBackPressed();
    }
}