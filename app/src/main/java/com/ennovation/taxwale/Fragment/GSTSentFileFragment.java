package com.ennovation.taxwale.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ennovation.taxwale.Adapter.GSTFileadapter;
import com.ennovation.taxwale.Adapter.ITRFileadapter;
import com.ennovation.taxwale.Model.ITRFileResponse;
import com.ennovation.taxwale.R;


public class GSTSentFileFragment extends Fragment {

    RecyclerView gstRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_g_s_t_sent_file, container, false);

        gstRecyclerView = view.findViewById(R.id.gstRecyclerView);

        ITRFileResponse[] myListData = new ITRFileResponse[]{
                new ITRFileResponse("User file File-Name file"),
                new ITRFileResponse("User file File-Name file"),
                new ITRFileResponse("User file File-Name file"),
                new ITRFileResponse("User file File-Name file"),
                new ITRFileResponse("User file File-Name file"),
                new ITRFileResponse("User file File-Name file"),
                new ITRFileResponse("User file File-Name file"),

        };

        GSTFileadapter adapter = new GSTFileadapter(myListData, getActivity());
        gstRecyclerView.setHasFixedSize(true);
        gstRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        gstRecyclerView.setAdapter(adapter);
        return view;

    }
}