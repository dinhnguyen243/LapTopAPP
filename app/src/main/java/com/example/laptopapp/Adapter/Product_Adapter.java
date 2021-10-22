package com.example.laptopapp.Adapter;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.laptopapp.Data.laptopData;
import com.example.laptopapp.fragment.frm_context;
import com.example.laptopapp.fragment.frm_details;

public class Product_Adapter extends FragmentStatePagerAdapter {
    private Context context;
    int totaltabs;
    String productID;
    String status;
    public Product_Adapter(@NonNull FragmentManager fm, Context context, int totaltabs,String productID,String status) {
        super(fm);
        this.context = context;
        this.totaltabs = totaltabs;
        this.productID = productID;
        this.status = status;
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putString("Product_ID",productID);
        bundle.putString("status",status);
       switch (position){
           case 0:
               frm_details frm_details = new frm_details();
               frm_details.setArguments(bundle);
               return frm_details;
           case 1:
               frm_context frm_context = new frm_context();
               frm_context.setArguments(bundle);
               return frm_context;
           default:
               return null;
       }
    }

    @Override
    public int getCount() {
        return 2;
    }

}
