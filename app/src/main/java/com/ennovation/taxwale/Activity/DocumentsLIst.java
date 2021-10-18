package com.ennovation.taxwale.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.ennovation.taxwale.R;

public class DocumentsLIst extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documents_list);
    }

    public void back(View view) {
        onBackPressed();
    }
}