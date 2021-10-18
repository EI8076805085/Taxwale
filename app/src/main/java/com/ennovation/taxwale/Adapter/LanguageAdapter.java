package com.ennovation.taxwale.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ennovation.taxwale.Activity.Language;
import com.ennovation.taxwale.Activity.OTPActivity;
import com.ennovation.taxwale.Activity.SplashActivity;
import com.ennovation.taxwale.Interfaces.LanguageListner;
import com.ennovation.taxwale.Model.LanguageModel;
import com.ennovation.taxwale.R;
import com.ennovation.taxwale.Utils.YourPreference;

public class LanguageAdapter extends RecyclerView.Adapter<LanguageAdapter.ViewHolder>{

    private LanguageModel[] listdata;
    Context context;
    int lastposition = -1;
    LanguageListner languageListner;

    public LanguageAdapter(LanguageModel[] myListData, Context applicationContext, LanguageListner languageListner) {
        this.listdata = myListData;
        this.context = applicationContext;
        this.languageListner = languageListner;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.language_list_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final LanguageModel myListData = listdata[position];
        holder.txt_languageName.setText(myListData.getLangName());
        holder.img_language.setImageResource(myListData.getImage());

        YourPreference yourPrefrence = YourPreference.getInstance(context);
        String langCode = yourPrefrence.getData("language");

        if(langCode.equalsIgnoreCase(myListData.getLanguageCode())){
            holder.languageListRowLayout.setBackground(context.getResources().getDrawable(R.drawable.white_background));
        }

        holder.languageListRowLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lastposition = holder.getAdapterPosition();
                notifyDataSetChanged();

                if(lastposition==position){
                    languageListner.languageClickListner(myListData.getLangName(),myListData.getLanguageCode());
                } else {
                    languageListner.languageClickListner(myListData.getLangName(),"");
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return listdata.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView img_language;
        public TextView txt_languageName;
        public LinearLayout languageListRowLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_language = itemView.findViewById(R.id.img_language);
            txt_languageName = itemView.findViewById(R.id.txt_languageName);
            languageListRowLayout = itemView.findViewById(R.id.languageListRowLayout);

        }
    }
}
