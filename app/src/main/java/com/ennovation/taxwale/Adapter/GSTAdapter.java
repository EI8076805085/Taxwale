package com.ennovation.taxwale.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ennovation.taxwale.Activity.UploadGST;
import com.ennovation.taxwale.Model.GSTResponse;
import com.ennovation.taxwale.Model.ITRFileResponse;
import com.ennovation.taxwale.Model.ITRResponse;
import com.ennovation.taxwale.Model.ServiceResponse;
import com.ennovation.taxwale.R;

public class GSTAdapter extends RecyclerView.Adapter<GSTAdapter.ViewHolder>{

    private GSTResponse[] listdata;
    Context context;

    public GSTAdapter(GSTResponse[] myListData, Context context) {
        this.listdata = myListData;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.gst_filling_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final GSTResponse myListData = listdata[position];
        holder.txt_name.setText(myListData.getName());
        holder.txt_subName.setText(myListData.getSubName());
        holder.img_Action.setImageResource(myListData.getImg());
        holder.gst_Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UploadGST.class);
                intent.putExtra("name",myListData.getName());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return listdata.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView img_Action;
        public TextView txt_name;
        public TextView txt_subName;
        private RelativeLayout gst_Layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img_Action = itemView.findViewById(R.id.img_Action);
            txt_name = itemView.findViewById(R.id.txt_name);
            txt_subName = itemView.findViewById(R.id.txt_subName);
            gst_Layout = itemView.findViewById(R.id.gst_Layout);

        }
    }
}
