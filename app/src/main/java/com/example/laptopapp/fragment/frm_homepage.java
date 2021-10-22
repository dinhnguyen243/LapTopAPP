package com.example.laptopapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.laptopapp.Activity.MainActivity;
import com.example.laptopapp.Adapter.homepagelogo_apdater;
import com.example.laptopapp.Data.loadData;
import com.example.laptopapp.Interface.Itemclick;
import com.example.laptopapp.Model.Laptop;
import com.example.laptopapp.Model.Logo;
import com.example.laptopapp.R;

import java.util.ArrayList;
import java.util.List;

public class frm_homepage extends Fragment implements Itemclick {
    ArrayList<Laptop> listdata;
    homepagelogo_apdater homepageAdapter;
    ArrayList<Logo> dtoLogos;
    ViewGroup root;
    private MainActivity mainActivity;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
         root = (ViewGroup) inflater.inflate(R.layout.fragment_homepage, container, false);
        RecyclerView myrv = (RecyclerView) root.findViewById(R.id.popular_recycler);
        RecyclerView logo = (RecyclerView) root.findViewById(R.id.logo_rcv);
        ImageSlider imageSlider = root.findViewById(R.id.slider);
        mainActivity = (MainActivity) getActivity();
        List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel("https://file.hstatic.net/1000262653/file/flash_sale_laptop_2f792c14d9ee44a48b38df29000e7f4d_master.jpg", null));
        slideModels.add(new SlideModel("https://cdn.tgdd.vn/Files/2019/05/29/1169729/dellpromotionq2t6-bannerwebsite_800x450-01_800x450.jpg", null));
        slideModels.add(new SlideModel("https://ben.com.vn/tin-tuc/wp-content/uploads/2020/07/Promotion-ODD-Laptop-785x466-1-1024x538.jpg", null));
        slideModels.add(new SlideModel("https://tanthanhdanh.vn/wp-content/uploads/2021/07/TTD_Promotion_202107_LGGramLamViecTaiNha_WebBannerV2.jpg", null));
        slideModels.add(new SlideModel("https://cdn2.sunwaypals.com.my/pr-000535e8-5a82-4c08-aaee-42dbdbbca611.jpg", null));
        imageSlider.setImageList(slideModels, true);
        imageSlider.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemSelected(int i) {
                mainActivity.onItemSelected(2);
            }
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(root.getContext(), RecyclerView.HORIZONTAL, false);
        logo.setLayoutManager(layoutManager);
        dtoLogos = new ArrayList<>();
        dtoLogos.add(new Logo(R.drawable.dell));
        dtoLogos.add(new Logo(R.drawable.acer));
        dtoLogos.add(new Logo(R.drawable.asus));
        dtoLogos.add(new Logo(R.drawable.lenovo));
        dtoLogos.add(new Logo(R.drawable.download));
        homepageAdapter = new homepagelogo_apdater(root.getContext(), dtoLogos, this);
        logo.setAdapter(homepageAdapter);
        loadData data = new loadData(root.getContext(),myrv,"Full",null,mainActivity);
        data.execute();
        return root;
    }

    @Override
    public void callback(int position) {
        switch (position) {
            case 0:
                mainActivity.showFragment("product",0);
                break;
            case 1:
                mainActivity.showFragment("product",1);
                break;
            case 2:
                mainActivity.showFragment("product",2);
                break;
            case 3:
                mainActivity.showFragment("product",3);
                break;
            case 4:
                mainActivity.showFragment("product",4);
                break;
        }
    }
}
