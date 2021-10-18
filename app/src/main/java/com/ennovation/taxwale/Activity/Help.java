package com.ennovation.taxwale.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ennovation.taxwale.R;

public class Help extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);


    }

    public void viewticket(View view) {
        Intent intent=new Intent(Help.this,Viewtickets.class);
        startActivity(intent);


    }

    public void back(View view) {
        onBackPressed();
    }
}