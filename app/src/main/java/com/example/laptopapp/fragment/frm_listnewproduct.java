package com.example.laptopapp.fragment;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.StrictMode;

import com.example.laptopapp.Activity.MainActivity;
import com.example.laptopapp.Adapter.RecyclerView_LoadProductAdapter;
import com.example.laptopapp.Model.Laptop;
import com.example.laptopapp.R;
import com.example.laptopapp.api.APIClient;
import com.example.laptopapp.api.ApiService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class frm_listnewproduct extends Fragment {
    MainActivity mainActivity;
    ArrayList<Laptop> listdata;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_newproduct, container, false);
        RecyclerView myrv = (RecyclerView) root.findViewById(R.id.recycler_newproduct);
        listdata = new ArrayList<>();
        mainActivity = (MainActivity) getActivity();
        Retrofit retrofit = APIClient.getClient();
        ApiService apiService = retrofit.create(ApiService.class);
        apiService.newlaptop().enqueue(new Callback<ArrayList<Laptop>>() {
            @Override
            public void onResponse(Call<ArrayList<Laptop>> call, Response<ArrayList<Laptop>> response) {
                listdata = response.body();
                RecyclerView_LoadProductAdapter myAdapter = new RecyclerView_LoadProductAdapter(root.getContext(),listdata,mainActivity);
                myrv.setLayoutManager(new GridLayoutManager(root.getContext(),2));
                myrv.setAdapter(myAdapter);
            }

            @Override
            public void onFailure(Call<ArrayList<Laptop>> call, Throwable t) {

            }
        });

        return root;
    }
}
