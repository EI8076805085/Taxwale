package com.ennovation.taxwale.Activity;

import static com.ennovation.taxwale.Utils.Constants.BASE_URL;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ennovation.taxwale.Adapter.SentandReciverDoucmentgAdapter;
import com.ennovation.taxwale.Model.SentandReciveDocumentModel;
import com.ennovation.taxwale.R;
import com.ennovation.taxwale.Utils.YourPreference;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SentReciveDcoument extends AppCompatActivity {

    RecyclerView recyclerview;
    ArrayList<SentandReciveDocumentModel> arraylist=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sent_recive_dcoument);
        recyclerview=findViewById(R.id.gstRecyclerView);
        String type=getIntent().getExtras().getString("type");
        GetDocuments(type);
    }

    public   void GetDocuments(String type) {
        YourPreference yourPrefrence = YourPreference.getInstance(getApplicationContext());
        String User_id=  yourPrefrence.getData("User_id");
        String url = BASE_URL + type;
        RequestQueue requestQueue = Volley.newRequestQueue(SentReciveDcoument.this);
        Map<String, String> params = new HashMap();
        params.put("user_id", User_id);
        JSONObject parameters = new JSONObject(params);
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject obj = new JSONObject(String.valueOf(response));
                    String r_code = obj.getString("status");
                    if (r_code.equalsIgnoreCase("1")) {
                        JSONArray jsonArray = obj.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            SentandReciveDocumentModel transactionModel=new SentandReciveDocumentModel();
                            JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                            String id = jsonObject2.getString("id");
                            String category = jsonObject2.getString("category");
                            String sub_category = jsonObject2.getString("sub_category");
                            String document = jsonObject2.getString("document");
                            String date = jsonObject2.getString("date");


                            transactionModel.setId(id);
                            transactionModel.setCategory(category);
                            transactionModel.setSub_category(sub_category);
                            transactionModel.setDocument(document);
                            transactionModel.setDate(date);
                            arraylist.add(transactionModel);
                        }

                        SentandReciverDoucmentgAdapter newsAdapter = new SentandReciverDoucmentgAdapter(arraylist, SentReciveDcoument.this);
                        recyclerview.setLayoutManager(new LinearLayoutManager(SentReciveDcoument.this, LinearLayoutManager.VERTICAL, false));
                        recyclerview.setAdapter(newsAdapter);
                        newsAdapter.notifyDataSetChanged();
                    } else {
                        recyclerview.setVisibility(View.GONE);
                        Toast.makeText(SentReciveDcoument.this, "No Data Found", Toast.LENGTH_SHORT).show();
                     }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {

        };
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);
    }

     public void back(View view) {
        onBackPressed();
    }
}