package com.ennovation.taxwale.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ennovation.taxwale.Adapter.LanguageAdapter;
import com.ennovation.taxwale.Adapter.ServiceAdapter;
import com.ennovation.taxwale.Interfaces.LanguageListner;
import com.ennovation.taxwale.Model.LanguageModel;
import com.ennovation.taxwale.Model.ServiceResponse;
import com.ennovation.taxwale.R;
import com.ennovation.taxwale.Utils.YourPreference;

public class Language extends AppCompatActivity implements LanguageListner {

    RecyclerView lanuageRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);
        lanuageRecyclerView = findViewById(R.id.lanuageRecyclerView);

        LanguageModel[] myListData = new LanguageModel[] {
                new LanguageModel("in","English", R.drawable.ic_green_tick_uploaded),
                new LanguageModel("hi","Hindi" , R.drawable.ic_green_tick_uploaded),


        };

        LanguageAdapter adapter = new LanguageAdapter(myListData,getApplicationContext(),this);
        lanuageRecyclerView.setHasFixedSize(true);
        lanuageRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        lanuageRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


    }

    public void back(View view) {
        onBackPressed();
    }

    @Override
    public void languageClickListner(String name,String code) {
        YourPreference yourPrefrence = YourPreference.getInstance(getApplicationContext());

        if(code.equalsIgnoreCase("")){
            yourPrefrence.saveData("languagetext", String.valueOf(name));
            yourPrefrence.saveData("language","en");
            Intent intent = new Intent(Language.this,SplashActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();

        } else {
            yourPrefrence.saveData("languagetext", String.valueOf(name));
            yourPrefrence.saveData("language",code);
            Intent intent = new Intent(Language.this,SplashActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();

        }
    }
}