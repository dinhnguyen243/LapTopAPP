package com.example.laptopapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.laptopapp.Activity.MainActivity;
import com.example.laptopapp.Data.loadEvent;
import com.example.laptopapp.R;

public class frm_notification extends Fragment {
    private  MainActivity mainActivity;
    ListView listView;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_notification, container, false);
       listView = root.findViewById(R.id.list_notification);
        loadEvent loadEvent = new loadEvent();
        loadEvent.takeallEvent(root.getContext(),listView);
        mainActivity = (MainActivity) getActivity();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    mainActivity.showFragment("promotion",-2);
            }
        });
        return root;
    }
}
