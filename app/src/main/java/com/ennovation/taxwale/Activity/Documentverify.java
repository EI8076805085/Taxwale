package com.ennovation.taxwale.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ennovation.taxwale.R;

public class Documentverify extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documentverify);
    }

    public void back(View view) {
        onBackPressed();
    }
    public void gst(View view) {
        Intent intent = new Intent(Documentverify.this, GSTFile.class);
        startActivity(intent);
    }

    public void itr(View view) {
        Intent intent = new Intent(Documentverify.this, ITRFile.class);
        startActivity(intent);    }
}