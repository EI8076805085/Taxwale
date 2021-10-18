package com.ennovation.taxwale.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ennovation.taxwale.Model.GSTResponse;
import com.ennovation.taxwale.Model.ITRResponse;
import com.ennovation.taxwale.R;

public class ITRAdapter extends RecyclerView.Adapter<ITRAdapter.ViewHolder>{

    private ITRResponse[] listdata;

    public ITRAdapter(ITRResponse[] myListData) {
        this.listdata = myListData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.irt_filling_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ITRResponse myListData = listdata[position];
        holder.txt_name.setText(myListData.getName());
        holder.txt_subName.setText(myListData.getSubName());
        holder.img_Action.setImageResource(myListData.getImg());

    }

    @Override
    public int getItemCount() {
        return listdata.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView img_Action;
        public TextView txt_name;
        public TextView txt_subName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_Action = itemView.findViewById(R.id.img_Action);
            txt_name = itemView.findViewById(R.id.txt_name);
            txt_subName = itemView.findViewById(R.id.txt_subName);

        }
    }
}
