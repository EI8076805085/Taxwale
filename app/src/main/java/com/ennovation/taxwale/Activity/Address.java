package com.ennovation.taxwale.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ennovation.taxwale.MainActivity;
import com.ennovation.taxwale.R;

public class Address extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
    }

    public void addaddress(View view) {
        Intent intent = new Intent(Address.this, AddAddress.class);
        startActivity(intent);

    }

    public void back(View view) {
        onBackPressed();
    }
}