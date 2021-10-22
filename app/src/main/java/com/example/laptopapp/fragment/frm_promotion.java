package com.example.laptopapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.laptopapp.Activity.MainActivity;
import com.example.laptopapp.Data.loadEvent;
import com.example.laptopapp.Data.loadVoucher;
import com.example.laptopapp.R;

public class frm_promotion extends Fragment {
    ListView listView;
    MainActivity mainActivity;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_promotion, container, false);
        listView = root.findViewById(R.id.list_promotion);
        loadVoucher loadVoucher = new loadVoucher();
        mainActivity = (MainActivity) getActivity();
        loadVoucher.takeallvoucher(root.getContext(), listView,mainActivity);
        return root;
    }
}
