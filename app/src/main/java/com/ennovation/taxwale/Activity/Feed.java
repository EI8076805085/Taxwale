package com.ennovation.taxwale.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ennovation.taxwale.MainActivity;
import com.ennovation.taxwale.R;

public class Feed extends AppCompatActivity {
    ImageView img_taxwale;
    LinearLayout feedLayout,notificationLayout,historyLayout,documentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        img_taxwale = findViewById(R.id.img_taxwale);
        feedLayout = findViewById(R.id.feedLayout);
        notificationLayout = findViewById(R.id.notificationLayout);
        historyLayout = findViewById(R.id.historyLayout);
        documentLayout = findViewById(R.id.documentLayout);

        img_taxwale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                overridePendingTransition(R.transition.fade_in, R.transition.fade_out);
                finish();
            }
        });

//        feedLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Feed.this, Feed.class);
//                startActivity(intent);
//                finish();
//            }
//        });

        notificationLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Notification.class));
                overridePendingTransition(R.transition.fade_in, R.transition.fade_out);
                finish();
            }
        });

        historyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), History.class));
                overridePendingTransition(R.transition.fade_in, R.transition.fade_out);
                finish();
            }
        });

        documentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Document.class));
                overridePendingTransition(R.transition.fade_in, R.transition.fade_out);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        overridePendingTransition(R.transition.fade_in, R.transition.fade_out);
        finish();
    }


}