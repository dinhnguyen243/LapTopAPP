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

import com.example.laptopapp.Data.loadVoucher;
import com.example.laptopapp.R;

public class frm_detailspromotion extends Fragment {
    TextView title,date,discount,code;
    ImageView imageView;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_promotion_details, container, false);
        String id = getArguments().getString("id_voucher");
        title = root.findViewById(R.id.title);
        date = root.findViewById(R.id.date);
        discount = root.findViewById(R.id.discount);
        code = root.findViewById(R.id.code);
        imageView = root.findViewById(R.id.image_promotion_details);
        loadVoucher loadVoucher = new loadVoucher();
        loadVoucher.takedetalisvoucher(imageView,title,date,discount,code,id);
        return root;
    }
}
