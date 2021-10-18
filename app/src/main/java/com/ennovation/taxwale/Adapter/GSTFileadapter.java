package com.ennovation.taxwale.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ennovation.taxwale.Model.ITRFileResponse;
import com.ennovation.taxwale.R;

public class GSTFileadapter extends RecyclerView.Adapter<GSTFileadapter.ViewHolder>{
    private ITRFileResponse[] listdata;
    Context context;

    public GSTFileadapter(ITRFileResponse[] myListData, FragmentActivity activity) {
        this.listdata = myListData;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.itr_file_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ITRFileResponse myListData = listdata[position];
        holder.txt_fileName.setText(myListData.getName());
    }

    @Override
    public int getItemCount() {
        return listdata.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txt_fileName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_fileName = itemView.findViewById(R.id.txt_fileName);
        }
    }
}
