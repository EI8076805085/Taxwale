package com.ennovation.taxwale.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ennovation.taxwale.Model.GSTResponse;
import com.ennovation.taxwale.Model.ServiceResponse;
import com.ennovation.taxwale.R;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ViewHolder>{
    private ServiceResponse[] listdata;

    public ServiceAdapter(ServiceResponse[] myListData) {
        this.listdata = myListData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.service_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ServiceResponse myListData = listdata[position];
        holder.txt_serviceName.setText(myListData.getName());
        holder.img_service.setImageResource(myListData.getImg());

    }

    @Override
    public int getItemCount() {
        return listdata.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView img_service;
        public TextView txt_serviceName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_service = itemView.findViewById(R.id.img_service);
            txt_serviceName = itemView.findViewById(R.id.txt_serviceName);

        }
    }
}
