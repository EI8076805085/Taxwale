package com.ennovation.taxwale.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ennovation.taxwale.Adapter.ITRFileadapter;
import com.ennovation.taxwale.Model.ITRFileResponse;
import com.ennovation.taxwale.R;


public class ITRAllFileFragment extends Fragment {

    RecyclerView itrRecyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_i_t_r_all_file, container, false);
        itrRecyclerView = view.findViewById(R.id.itrRecyclerView);

        ITRFileResponse[] myListData = new ITRFileResponse[]{
                new ITRFileResponse("No file upload"),
               new ITRFileResponse("No file upload"),
               new ITRFileResponse("No file upload"),
               new ITRFileResponse("No file upload"),
               new ITRFileResponse("No file upload"),
               new ITRFileResponse("No file upload"),
               new ITRFileResponse("No file upload"),


        };

        ITRFileadapter adapter = new ITRFileadapter(myListData, getActivity());
        itrRecyclerView.setHasFixedSize(true);
        itrRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        itrRecyclerView.setAdapter(adapter);

        return view;

    }
}