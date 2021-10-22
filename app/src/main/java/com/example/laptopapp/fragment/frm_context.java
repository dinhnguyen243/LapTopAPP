package com.example.laptopapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.laptopapp.Data.laptopData;
import com.example.laptopapp.R;

public class frm_context extends Fragment {
    TextView masp, tensp, brand, ram, rom, CPU, Core, mausac, manhinh, kichthuoc, VGA, os;
    ImageView imageView;
    String Product_id;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.product_context, container, false);
        masp = root.findViewById(R.id.tv_context_masp);
        tensp = root.findViewById(R.id.tv_context_tensp);
        brand = root.findViewById(R.id.tv_context_brand);
        ram = root.findViewById(R.id.tv_context_ram);
        rom = root.findViewById(R.id.tv_context_rom);
        CPU = root.findViewById(R.id.tv_context_CPU);
        Core = root.findViewById(R.id.tv_context_core);
        mausac = root.findViewById(R.id.tv_context_mausac);
        manhinh = root.findViewById(R.id.tv_context_manhinh);
        kichthuoc = root.findViewById(R.id.tv_context_kichthuoc);
        VGA = root.findViewById(R.id.tv_context_VGA);
        os = root.findViewById(R.id.tv_context_os);
        imageView = root.findViewById(R.id.imagecontext);
        Product_id = getArguments().getString("Product_ID");
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        laptopData laptopData = new laptopData();
        laptopData.takeinfodetails(Product_id,masp, tensp, brand, ram, rom, CPU, Core, mausac, manhinh, kichthuoc, VGA, os,imageView);
    }
}
