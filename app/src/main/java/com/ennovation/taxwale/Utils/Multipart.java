package com.ennovation.taxwale.Utils;

 import android.app.Dialog;
 import android.app.ProgressDialog;
 import android.content.Context;
 import android.content.Intent;
 import android.graphics.drawable.ColorDrawable;
 import android.util.Log;
 import android.view.View;
 import android.view.Window;
 import android.view.WindowManager;
 import android.widget.TextView;
 import android.widget.Toast;

 import com.android.volley.DefaultRetryPolicy;
 import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.SimpleMultiPartRequest;
import com.android.volley.toolbox.Volley;
 import com.ennovation.taxwale.Activity.UploadGST;
 import com.ennovation.taxwale.MainActivity;
 import com.ennovation.taxwale.R;

 import org.json.JSONException;
import org.json.JSONObject;

public class Multipart {
    public void uploadMedia(final Context context, String filePath,int position,int filesize,String month,String year) {

        ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage("Please wait while we Uploading Data...");
        pd.show();
        pd.setCancelable(false);

        String url = "http://tax-wale.com/app/api/user_document_upload";

        SimpleMultiPartRequest multiPartRequestWithParams = new SimpleMultiPartRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(String.valueOf(response));
                            if (position+1==filesize) {
                                final Dialog dialog = new Dialog(context);
                                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog.setContentView(R.layout.file_uploaded_successfully);
                                dialog.setCancelable(false);
                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                               TextView txt_Ok = dialog.findViewById(R.id.txt_Ok);
                                txt_Ok.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(context, MainActivity.class);
                                        context.startActivity(intent);
                                        dialog.dismiss();
                                    }
                                });

                                dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                                dialog.show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        pd.dismiss();
                        Log.d("Response", response);
                        // TODO: Do something on success
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: Handle your error here
                 Toast.makeText(context, ""+error, Toast.LENGTH_SHORT).show();
                pd.dismiss();

            }
        });

        YourPreference yourPrefrence = YourPreference.getInstance(context);
        String User_id=  yourPrefrence.getData("User_id");

        // Add the file here
        multiPartRequestWithParams.addFile("document", filePath);

        // Add the params here
        multiPartRequestWithParams.addStringParam("upload_month", month);
        multiPartRequestWithParams.addStringParam("upload_year", year);
        multiPartRequestWithParams.addStringParam("user_id", User_id);
        multiPartRequestWithParams.addStringParam("category", "GST");
        multiPartRequestWithParams.addStringParam("sub_category", "Sale Bill");

        RequestQueue queue = Volley.newRequestQueue(context);
        multiPartRequestWithParams.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(multiPartRequestWithParams);

    }
}