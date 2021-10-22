package com.example.laptopapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.laptopapp.Activity.ProductDetails;
import com.example.laptopapp.Adapter.BillDetaliAdapter;
import com.example.laptopapp.Model.BillDetail;
import com.example.laptopapp.R;
import com.example.laptopapp.api.APIClient;
import com.example.laptopapp.api.ApiService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class frm_billdetail extends Fragment {
    ListView listView;
    ArrayList<BillDetail> billDetails = new ArrayList<>();
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_billdetali, container, false);
        listView = root.findViewById(R.id.list_billdetali);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Retrofit retrofit = APIClient.getClient();
        ApiService apiService = retrofit.create(ApiService.class);
        String bill_id = getArguments().getString("bill_id");
       apiService.takebilldetali(bill_id).enqueue(new Callback<ArrayList<BillDetail>>() {
           @Override
           public void onResponse(Call<ArrayList<BillDetail>> call, Response<ArrayList<BillDetail>> response) {
               billDetails = response.body();
               BillDetaliAdapter billDetaliAdapter = new BillDetaliAdapter(root.getContext(), billDetails);
               listView.setAdapter(billDetaliAdapter);
               listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                   @Override
                   public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                       Intent intent = new Intent(root.getContext(), ProductDetails.class);
                       intent.putExtra("product_ID",billDetails.get(position).getProduct_id());
                       startActivity(intent);
                   }
               });
           }
           @Override
           public void onFailure(Call<ArrayList<BillDetail>> call, Throwable t) {
               System.out.println("loi " + t);
           }
       });
        return root;
    }
}
