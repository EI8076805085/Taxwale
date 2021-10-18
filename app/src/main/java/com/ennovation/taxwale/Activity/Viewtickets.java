package com.ennovation.taxwale.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.ennovation.taxwale.R;

public class Viewtickets extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewtickets);

    }

    public void back(View view) {
        onBackPressed();
    }
}