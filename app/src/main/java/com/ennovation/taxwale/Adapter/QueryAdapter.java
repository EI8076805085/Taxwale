package com.ennovation.taxwale.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ennovation.taxwale.Activity.ConsultantQueryActivity;
import com.ennovation.taxwale.Model.ITRResponse;
import com.ennovation.taxwale.Model.QueryListRisponse;
import com.ennovation.taxwale.R;

public class QueryAdapter extends RecyclerView.Adapter<QueryAdapter.ViewHolder> {
    QueryListRisponse[] listData;
    Context context;

    public QueryAdapter(QueryListRisponse[] myListData, ConsultantQueryActivity consultantQueryActivity) {
        this.listData = myListData;
        this.context = consultantQueryActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.query_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final QueryListRisponse myListData = listData[position];
        holder.txt_query.setText(myListData.getQuery());

    }

    @Override
    public int getItemCount() {
        return listData.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_query;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_query = itemView.findViewById(R.id.txt_query);

        }
    }
}
