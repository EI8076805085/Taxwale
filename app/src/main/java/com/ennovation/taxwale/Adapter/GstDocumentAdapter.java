package com.ennovation.taxwale.Adapter;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ennovation.taxwale.Model.DocumentuploadModel;
import com.ennovation.taxwale.Model.ServiceResponse;
import com.ennovation.taxwale.R;

import java.util.ArrayList;
import java.util.List;

import tech.mingxi.mediapicker.models.ResultItem;

public class GstDocumentAdapter extends RecyclerView.Adapter<GstDocumentAdapter.ViewHolder>{
    private List<DocumentuploadModel> listdata;



    public GstDocumentAdapter(List<DocumentuploadModel> myListData ) {
        this.listdata = myListData;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.row_add_gstbill, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        int count=position+1;
        holder.txt_count.setText(""+count);


             holder.txt_name.setText(listdata.get(position).getPath());




        holder.img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listdata.size()!=0){
                    listdata.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position,listdata.size());
                }



            }
        });


    }

    @Override
    public int getItemCount() {

         return listdata.size();


    }

    public class ViewHolder extends RecyclerView.ViewHolder {
          public TextView txt_name,txt_count;
          ImageView img_delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_name = itemView.findViewById(R.id.txt_name);
            img_delete = itemView.findViewById(R.id.img_delete);
            txt_count = itemView.findViewById(R.id.txt_count);

        }
    }
}
