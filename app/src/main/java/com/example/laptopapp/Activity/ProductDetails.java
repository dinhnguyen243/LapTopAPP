package com.example.laptopapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.laptopapp.Adapter.Product_Adapter;
import com.example.laptopapp.Data.laptopData;
import com.example.laptopapp.Model.Laptop;
import com.example.laptopapp.R;

public class ProductDetails extends AppCompatActivity {
    ViewPager viewPager;
    ImageView back, icon_cart;
    Laptop laptop = new Laptop();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.productdetails);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        viewPager = findViewById(R.id.view_paper);
        icon_cart = findViewById(R.id.icon_cart);
        back = findViewById(R.id.icon_back_details);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        icon_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductDetails.this, CartActivity.class);
                startActivity(intent);
            }
        });
        Intent intent = getIntent();
        String Proudct_ID = intent.getStringExtra("product_ID");
        String status = intent.getStringExtra("status");
            Product_Adapter product_adapter = new Product_Adapter(getSupportFragmentManager(),this,2,Proudct_ID,status);
            viewPager.setAdapter(product_adapter);
    }
}
