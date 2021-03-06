package com.bku.appbooking.main.fragment.history;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.bku.appbooking.main.fragment.history.HistoryListAdapter;
import com.bku.appbooking.R;

public class HistoryFragment extends Fragment {
    ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_history, container, false);
        listView = view.findViewById(R.id.history_listview);
        setupListView();
        return view;
    }

    private void setupListView(){
        Log.e("hiiiiiiiiiiiiii", "hiiiiiiiiiiiiii");
        listView.setAdapter(new HistoryListAdapter(getContext()));
    }
}
