package com.ennovation.taxwale.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ennovation.taxwale.Activity.UploadGST;
import com.ennovation.taxwale.Interfaces.MonthListner;
import com.ennovation.taxwale.Model.MonthResponseData;
import com.ennovation.taxwale.R;

import java.util.List;

public class MonthAdapter extends RecyclerView.Adapter<MonthAdapter.ViewHolder>{
    List<String> monthlist;
    MonthListner monthListner;
    Context context;
    Dialog dialog;
    int numberofMonth;

    public MonthAdapter(List<String> monthsList, Context context, MonthListner monthListner, Dialog dialog, int numberOfMonth) {
        this.monthlist = monthsList;
        this.context =context;
        this.monthListner = monthListner;
        this.dialog = dialog;
        this.numberofMonth = numberOfMonth;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.month_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (numberofMonth==position+1){
            holder.txt_Month.setText(monthlist.get(position));
            holder.txt_Month.setTextColor(context.getResources().getColor(R.color.blue));

        } else {
            holder.txt_Month.setText(monthlist.get(position));

        }

        holder.monthLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monthListner.monthClickListner(monthlist.get(position));
                dialog.dismiss();
            }
        });
    }


    @Override
    public int getItemCount() {
        return monthlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txt_Month;
        LinearLayout monthLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_Month = itemView.findViewById(R.id.txt_Month);
            monthLayout = itemView.findViewById(R.id.monthLayout);
        }
    }
}
