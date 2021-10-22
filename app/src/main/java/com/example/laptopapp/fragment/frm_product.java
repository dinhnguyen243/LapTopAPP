package com.example.laptopapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.laptopapp.Activity.MainActivity;
import com.example.laptopapp.Adapter.RecyclerView_LoadProductAdapter;
import com.example.laptopapp.Data.loadData;
import com.example.laptopapp.Model.Laptop;
import com.example.laptopapp.R;

import java.util.ArrayList;

public class frm_product extends Fragment {
    ArrayList<Laptop> listdata;
    TextView tv_brand;
    int brand;
    String name;
MainActivity mainActivity;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_product, container, false);
        RecyclerView myrv = (RecyclerView) root.findViewById(R.id.recycler_product);
        tv_brand = root.findViewById(R.id.tv_brand);
        mainActivity = (MainActivity) getActivity();
        listdata = new ArrayList<>();
        try {
            brand = getArguments().getInt("brand", -1);
        } catch (Exception e) {

        }
        try{
            name = getArguments().getString("name");
        }catch (Exception e){

        }
        if (name == null){
            switch (brand) {
                case 0:
                    loadData data = new loadData(root.getContext(),myrv,"dell",null,mainActivity);
                    tv_brand.setText("Thương Hiệu : Dell");
                    data.execute();
                    break;
                case 1:
                    loadData data1 = new loadData(root.getContext(),myrv,"acer",null,mainActivity);
                    tv_brand.setText("Thương Hiệu : Acer");
                    data1.execute();
                    break;
                case 2:
                    loadData data2 = new loadData(root.getContext(),myrv,"asus",null,mainActivity);
                    tv_brand.setText("Thương Hiệu : Asus");
                    data2.execute();
                    break;
                case 3:
                    loadData data3 = new loadData(root.getContext(),myrv,"lenovo",null,mainActivity);
                    tv_brand.setText("Thương Hiệu : Lenovo");
                    data3.execute();
                    break;
                case 4:
                    loadData data4 = new loadData(root.getContext(),myrv,"msi",null,mainActivity);
                    tv_brand.setText("Thương Hiệu : MSI");
                    data4.execute();
                    break;
                case -1:
                    loadData data5 = new loadData(root.getContext(),myrv,"Full",null,mainActivity);
                    tv_brand.setText("Danh Sách");
                    data5.execute();
                    break;



            }
        }else{
            loadData data = new loadData(root.getContext(),myrv,"",name,mainActivity);
            data.execute();
            tv_brand.setText("Tìm Kiếm");
        }

        return root;
    }
}
