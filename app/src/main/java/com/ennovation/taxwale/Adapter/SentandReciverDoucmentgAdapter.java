package com.ennovation.taxwale.Adapter;

import static com.ennovation.taxwale.Utils.Constants.IMAGE_URL;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ennovation.taxwale.Model.SentandReciveDocumentModel;
import com.ennovation.taxwale.R;
import java.util.ArrayList;

public class SentandReciverDoucmentgAdapter extends RecyclerView.Adapter<SentandReciverDoucmentgAdapter.ViewHolder>{

ArrayList<SentandReciveDocumentModel> arrayList=new ArrayList<>();
Context context;

    public SentandReciverDoucmentgAdapter(ArrayList<SentandReciveDocumentModel> arrayList, Context context) {
        this.arrayList = arrayList;
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

        String []namevalue=arrayList.get(position).getDocument().split("/");
        holder.txt_fileName.setText(namevalue[1]);
        holder.date.setText(arrayList.get(position).getDate());
        if (arrayList.get(position).getCategory().equalsIgnoreCase("ITR")) {
            holder.symbol.setBackgroundTintList(context.getResources().getColorStateList(R.color.orenge));
        }

        Glide.with(context).load(IMAGE_URL+arrayList.get(position).getDocument()).apply(new RequestOptions().placeholder(R.drawable.file).error(R.drawable.file)).into(holder.img_document);


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView img_Action,img_document;
        public TextView txt_fileName;
        public TextView date;
        private RelativeLayout gst_Layout;
        private LinearLayout symbol;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_fileName = itemView.findViewById(R.id.txt_fileName);
            symbol = itemView.findViewById(R.id.symbol);
            date = itemView.findViewById(R.id.date);
            img_document = itemView.findViewById(R.id.img_document);


        }
    }
}
