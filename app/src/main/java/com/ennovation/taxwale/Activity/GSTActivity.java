package com.ennovation.taxwale.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.ennovation.taxwale.Adapter.GSTAdapter;
import com.ennovation.taxwale.Adapter.ServiceAdapter;
import com.ennovation.taxwale.Model.GSTResponse;
import com.ennovation.taxwale.Model.ServiceResponse;
import com.ennovation.taxwale.R;

public class GSTActivity extends AppCompatActivity {

    RecyclerView gst_RecyclerView;
    LinearLayout back_Layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_g_s_t);

        gst_RecyclerView = findViewById(R.id.gst_RecyclerView);
        back_Layout = findViewById(R.id.back_Layout);
        back_Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        GSTResponse[] myListData = new GSTResponse[]{
                new GSTResponse("Sale Bill", "No file upload", R.drawable.ic_upload_list_icon),
                new GSTResponse("Purchase Bill", "Number of file ", R.drawable.ic_uploaded_list_icon),
                new GSTResponse("Sale Bill", "No file upload", R.drawable.ic_upload_list_icon),
                new GSTResponse("Sale Bill", "Number of file ", R.drawable.ic_uploaded_list_icon),
                new GSTResponse("Sale Bill", "No file upload", R.drawable.ic_upload_list_icon),

        };

        GSTAdapter adapter = new GSTAdapter(myListData,GSTActivity.this);
        gst_RecyclerView.setHasFixedSize(true);
        gst_RecyclerView.setLayoutManager(new LinearLayoutManager(this));
        gst_RecyclerView.setAdapter(adapter);

    }

    public void back(View view) {
        onBackPressed();
    }
}