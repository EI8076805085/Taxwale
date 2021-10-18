package com.ennovation.taxwale.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.ennovation.taxwale.Adapter.GSTAdapter;
import com.ennovation.taxwale.Adapter.ITRAdapter;
import com.ennovation.taxwale.Model.GSTResponse;
import com.ennovation.taxwale.Model.ITRResponse;
import com.ennovation.taxwale.R;

public class ITRActivity extends AppCompatActivity {
    RecyclerView itr_RecyclerView;
    LinearLayout back_Layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_i_t_r);

        itr_RecyclerView = findViewById(R.id.itr_RecyclerView);
        back_Layout = findViewById(R.id.back_Layout);
        back_Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ITRResponse[] myListData = new ITRResponse[]{
                new ITRResponse("Salary/ Pension/ From 16", "", R.drawable.ic_upload_list_icon),
                new ITRResponse("House Property / Rental Income","" , R.drawable.ic_uploaded_list_icon),
                new ITRResponse("Business/ Professional Income", "P&L, Balance Sheet etc", R.drawable.ic_upload_list_icon),
                new ITRResponse("Foriegn Income", "No file Uploaded" ,R.drawable.ic_uploaded_list_icon),
                new ITRResponse("Capital Gain / Property Sale","Property Sale & Purchase Documents",  R.drawable.ic_upload_list_icon),

        };

        ITRAdapter adapter = new ITRAdapter(myListData);
        itr_RecyclerView.setHasFixedSize(true);
        itr_RecyclerView.setLayoutManager(new LinearLayoutManager(this));
        itr_RecyclerView.setAdapter(adapter);

    }

    public void back(View view) {
        onBackPressed();
    }
}