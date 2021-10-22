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
import com.example.laptopapp.Adapter.BillAdapter;
import com.example.laptopapp.Model.Bill;
import com.example.laptopapp.R;
import com.example.laptopapp.Session.SessionManagement;
import com.example.laptopapp.api.APIClient;
import com.example.laptopapp.api.ApiService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class frm_history extends Fragment {
    ArrayList<Bill> bills = new ArrayList<>();
    ListView listView;
    MainActivity mainActivity;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_history, container, false);
        listView = (ListView) root.findViewById(R.id.list_history);
        mainActivity = (MainActivity) getActivity();
        Retrofit retrofit = APIClient.getClient();
        ApiService apiService = retrofit.create(ApiService.class);
        SessionManagement sessionManagement = new SessionManagement(root.getContext());
        String id = sessionManagement.getSession();
        apiService.bill(id).enqueue(new Callback<ArrayList<Bill>>() {
            @Override
            public void onResponse(Call<ArrayList<Bill>> call, Response<ArrayList<Bill>> response) {
                bills = response.body();
                BillAdapter billAdapter = new BillAdapter(root.getContext(), bills);
                listView.setAdapter(billAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        mainActivity.showdetailsbill(bills.get(position).getBill_id());
                    }
                });
            }

            @Override
            public void onFailure(Call<ArrayList<Bill>> call, Throwable t) {

            }
        });

        return root;
    }
}
